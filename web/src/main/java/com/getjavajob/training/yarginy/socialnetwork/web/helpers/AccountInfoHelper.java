package com.getjavajob.training.yarginy.socialnetwork.web.helpers;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.Phone;
import com.getjavajob.training.yarginy.socialnetwork.common.models.additionaldata.Role;
import com.getjavajob.training.yarginy.socialnetwork.common.utils.DataHandler;
import com.getjavajob.training.yarginy.socialnetwork.service.AccountService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collection;
import java.util.stream.Collectors;

import static com.getjavajob.training.yarginy.socialnetwork.common.models.additionaldata.PhoneType.PRIVATE;
import static com.getjavajob.training.yarginy.socialnetwork.common.models.additionaldata.PhoneType.WORK;
import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Attributes.PHOTO;

@Component
public class AccountInfoHelper {
    private final AccountService accountService;
    private final DataHandler dataHandler;

    public AccountInfoHelper(AccountService accountService, DataHandler dataHandler) {
        this.accountService = accountService;
        this.dataHandler = dataHandler;
    }

    public void setAccountInfo(ModelAndView modelAndView, Account account) {
        modelAndView.addObject("user", account);
        modelAndView.addObject("id", account.getId());
        modelAndView.addObject(PHOTO, dataHandler.getHtmlPhoto(account.getPhoto()));

        Collection<Phone> phones = accountService.getPhones(account.getId());
        Collection<Phone> privatePhones = phones.stream().filter(phone -> PRIVATE.equals(phone.getType())).
                collect(Collectors.toList());
        modelAndView.addObject("privatePhones", privatePhones);
        Collection<Phone> workPhones = phones.stream().filter(phone -> WORK.equals(phone.getType())).
                collect(Collectors.toList());
        modelAndView.addObject("workPhones", workPhones);
    }

    public boolean isAdmin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        GrantedAuthority authority = authentication.getAuthorities().iterator().next();
        return Role.ADMIN.toString().equals(authority.getAuthority());
    }
}
