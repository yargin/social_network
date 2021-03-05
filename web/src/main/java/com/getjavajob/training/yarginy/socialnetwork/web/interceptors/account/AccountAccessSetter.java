package com.getjavajob.training.yarginy.socialnetwork.web.interceptors.account;

import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.additionaldata.Role;
import com.getjavajob.training.yarginy.socialnetwork.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Attributes.*;

@Component
public class AccountAccessSetter extends HandlerInterceptorAdapter {
    private final AccountService accountService;

    @Autowired
    public AccountAccessSetter(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse resp, Object handler) {
        HttpSession session = req.getSession();
        long sessionId = (long) session.getAttribute(USER_ID);

        String stringRequestedId = req.getParameter(REQUESTED_ID);
        long requestedId;
        try {
            requestedId = Long.parseLong(stringRequestedId);
        } catch (NumberFormatException e) {
            requestedId = sessionId;
        }

        if (sessionId == requestedId) {
            req.setAttribute("owner", true);
        } else {
            if (accountService.isFriend(sessionId, requestedId)) {
                req.setAttribute("friend", true);
            } else if (accountService.isRequester(sessionId, requestedId)) {
                req.setAttribute("requester", true);
            }
        }

        Account account = (Account) session.getAttribute(USER);
        if (Role.ADMIN.equals(account.getRole())) {
            req.setAttribute("admin", true);
        }

        return true;
    }
}
