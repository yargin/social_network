package com.getjavajob.training.yarginy.socialnetwork.web.servlethelpers;

import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.additionaldata.Role;
import com.getjavajob.training.yarginy.socialnetwork.common.models.phone.Phone;
import com.getjavajob.training.yarginy.socialnetwork.common.models.phone.additionaldata.PhoneType;
import com.getjavajob.training.yarginy.socialnetwork.common.utils.DataHandleHelper;
import com.getjavajob.training.yarginy.socialnetwork.service.AccountService;
import com.getjavajob.training.yarginy.socialnetwork.service.aaa.AccountInfoKeeper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.stream.Collectors;

import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Attributes.USER;
import static java.util.Objects.isNull;

@Component
public class AccountInfoHelper {
    private AccountService accountService;
    private ApplicationContext context;

    @Autowired
    public void setAccountService(ApplicationContext context, AccountService accountService) {
        this.accountService = accountService;
        this.context = context;
    }

    public void setAccountInfo(HttpServletRequest req, long requestedUserId) {
        AccountInfoKeeper accountInfoKeeper = accountService.getAccountInfo(requestedUserId);

        Account account = accountInfoKeeper.getAccount();
        req.setAttribute("user", account);
        req.setAttribute("photo", context.getBean(DataHandleHelper.class).getHtmlPhoto(account.getPhoto()));

        Collection<Phone> phones = accountInfoKeeper.getPhones();
        Collection<Phone> privatePhones = phones.stream().filter(phone -> phone.getType() == PhoneType.PRIVATE).
                collect(Collectors.toList());
        req.setAttribute("privatePhones", privatePhones);
        Collection<Phone> workPhones = phones.stream().filter(phone -> phone.getType() == PhoneType.WORK).
                collect(Collectors.toList());
        req.setAttribute("workPhones", workPhones);
    }

    public static boolean isAdmin(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException,
            ServletException {
        Account account = (Account) req.getSession().getAttribute(USER);
        if ((!isNull(account.getRole()) && (Role.ADMIN.equals(account.getRole())))) {
            req.setAttribute("admin", true);
            chain.doFilter(req, res);
            return true;
        }
        return false;
    }
}
