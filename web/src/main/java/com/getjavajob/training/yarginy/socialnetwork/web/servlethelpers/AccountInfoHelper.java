package com.getjavajob.training.yarginy.socialnetwork.web.servlethelpers;

import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.additionaldata.Role;
import com.getjavajob.training.yarginy.socialnetwork.common.models.phone.Phone;
import com.getjavajob.training.yarginy.socialnetwork.common.models.phone.additionaldata.PhoneType;
import com.getjavajob.training.yarginy.socialnetwork.service.AccountService;
import com.getjavajob.training.yarginy.socialnetwork.service.AccountServiceImpl;
import com.getjavajob.training.yarginy.socialnetwork.service.dto.AccountInfoDTO;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.stream.Collectors;

import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Attributes.USER;
import static java.util.Objects.isNull;

public class AccountInfoHelper {
    private static final AccountService ACCOUNT_SERVICE = new AccountServiceImpl();

    public void setAccountInfo(HttpServletRequest req, long requestedUserId) {
        AccountInfoDTO accountInfoDTO = ACCOUNT_SERVICE.getAccountInfo(requestedUserId);

        Account account = accountInfoDTO.getAccount();
        req.setAttribute("user", account);

        Collection<Phone> phones = accountInfoDTO.getPhones();
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
