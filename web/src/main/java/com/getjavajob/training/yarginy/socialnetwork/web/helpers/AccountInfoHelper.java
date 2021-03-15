package com.getjavajob.training.yarginy.socialnetwork.web.helpers;

import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.additionaldata.Role;
import com.getjavajob.training.yarginy.socialnetwork.common.models.phone.Phone;
import com.getjavajob.training.yarginy.socialnetwork.common.models.phone.additionaldata.PhoneType;
import com.getjavajob.training.yarginy.socialnetwork.common.utils.DataHandler;
import com.getjavajob.training.yarginy.socialnetwork.service.AccountService;
import com.getjavajob.training.yarginy.socialnetwork.service.infokeepers.AccountInfoKeeper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.stream.Collectors;

import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Attributes.PHOTO;
import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Attributes.USER;
import static java.util.Objects.isNull;

@Component
public class AccountInfoHelper {
    private AccountService accountService;

    @Autowired
    public void setAccountService(AccountService accountService) {
        this.accountService = accountService;
    }

    public void setAccountInfo(ModelAndView modelAndView, long id) {
        AccountInfoKeeper accountInfoKeeper = accountService.getAccountInfo(id);

        Account account = accountInfoKeeper.getAccount();
        modelAndView.addObject("user", account);
        modelAndView.addObject(PHOTO, new DataHandler().getHtmlPhoto(account.getPhoto()));

        Collection<Phone> phones = accountInfoKeeper.getPhones();
        Collection<Phone> privatePhones = phones.stream().filter(phone -> phone.getType() == PhoneType.PRIVATE).
                collect(Collectors.toList());
        modelAndView.addObject("privatePhones", privatePhones);
        Collection<Phone> workPhones = phones.stream().filter(phone -> phone.getType() == PhoneType.WORK).
                collect(Collectors.toList());
        modelAndView.addObject("workPhones", workPhones);
    }

    public boolean isAdmin(HttpServletRequest req) {
        Account account = (Account) req.getSession().getAttribute(USER);
        if ((!isNull(account.getRole()) && (Role.ADMIN.equals(account.getRole())))) {
            req.setAttribute("admin", true);
            return true;
        }
        return false;
    }
}
