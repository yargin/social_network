package com.getjavajob.training.yarginy.socialnetwork.web.helpers;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.additionaldata.Role;
import com.getjavajob.training.yarginy.socialnetwork.common.models.Phone;
import com.getjavajob.training.yarginy.socialnetwork.common.utils.DataHandler;
import com.getjavajob.training.yarginy.socialnetwork.service.AccountService;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.stream.Collectors;

import static com.getjavajob.training.yarginy.socialnetwork.common.models.additionaldata.PhoneType.PRIVATE;
import static com.getjavajob.training.yarginy.socialnetwork.common.models.additionaldata.PhoneType.WORK;
import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Attributes.PHOTO;
import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Attributes.USER;
import static java.util.Objects.isNull;

@Component
public class AccountInfoHelper {
    private final AccountService accountService;
    private final DataHandler dataHandler;

    public AccountInfoHelper(AccountService accountService, DataHandler dataHandler) {
        this.accountService = accountService;
        this.dataHandler = dataHandler;
    }

    public void setAccountInfo(ModelAndView modelAndView, long id) {
        Account account = accountService.get(id);
        modelAndView.addObject("user", account);
        modelAndView.addObject(PHOTO, dataHandler.getHtmlPhoto(account.getPhoto()));

        Collection<Phone> phones = accountService.getPhones(id);
        Collection<Phone> privatePhones = phones.stream().filter(phone -> PRIVATE.equals(phone.getType())).
                collect(Collectors.toList());
        modelAndView.addObject("privatePhones", privatePhones);
        Collection<Phone> workPhones = phones.stream().filter(phone -> WORK.equals(phone.getType())).
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
