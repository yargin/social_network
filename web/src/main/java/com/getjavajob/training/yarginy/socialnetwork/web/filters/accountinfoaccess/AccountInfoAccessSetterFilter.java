package com.getjavajob.training.yarginy.socialnetwork.web.filters.accountinfoaccess;

import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.additionaldata.Role;
import com.getjavajob.training.yarginy.socialnetwork.service.AccountService;
import com.getjavajob.training.yarginy.socialnetwork.service.AccountServiceImpl;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Attributes.*;
import static java.util.Objects.isNull;

public class AccountInfoAccessSetterFilter extends HttpFilter {
    private final AccountService accountService = new AccountServiceImpl();

    @Override
    public void doFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain filterChain) throws IOException,
            ServletException {
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

        filterChain.doFilter(req, resp);
    }
}
