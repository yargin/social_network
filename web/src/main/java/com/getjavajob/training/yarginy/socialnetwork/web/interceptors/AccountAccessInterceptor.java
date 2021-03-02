package com.getjavajob.training.yarginy.socialnetwork.web.interceptors;

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
import static java.util.Objects.isNull;

@Component
public class AccountAccessInterceptor extends HandlerInterceptorAdapter {
    private final AccountService accountService;

    @Autowired
    public AccountAccessInterceptor(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse resp, Object handler) throws Exception {
        HttpSession session = req.getSession();
        long requestedUserId;
        Object objectId = req.getAttribute(REQUESTED_ID);
        if (isNull(objectId)) {
            requestedUserId = (long) req.getAttribute(RECEIVER_ID);
        } else {
            requestedUserId = (long) objectId;
        }

        long requesterId = (long) session.getAttribute(USER_ID);
        if (requesterId == requestedUserId) {
            req.setAttribute("owner", true);
        }

        if (accountService.isFriend(requesterId, requestedUserId)) {
            req.setAttribute("friend", true);
        } else if (accountService.isRequester(requesterId, requestedUserId)) {
            req.setAttribute("requester", true);
        }

        Account account = (Account) session.getAttribute(USER);
        if (Role.ADMIN.equals(account.getRole())) {
            req.setAttribute("admin", true);
        }

        return true;
    }
}
