package com.getjavajob.training.yarginy.socialnetwork.web.filters.messageaccess;

import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.additionaldata.Role;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Attributes.*;

public class MessageAccessSetterFilter extends HttpFilter {
    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain chain) throws IOException, ServletException {
        long authorId = (long) req.getAttribute(REQUESTER_ID);
        long wallOwnerId = (long) req.getAttribute(RECEIVER_ID);
        long currentUserId = (long) req.getSession().getAttribute(USER_ID);

        if (wallOwnerId == currentUserId) {
            req.setAttribute(REDACTOR, true);
            chain.doFilter(req, resp);
            return;
        }

        if (authorId == currentUserId) {
            req.setAttribute(REDACTOR, true);
            chain.doFilter(req, resp);
            return;
        }

        Account account = (Account) req.getSession().getAttribute(USER);
        if (Role.ADMIN.equals(account.getRole())) {
            req.setAttribute(REDACTOR, true);
            chain.doFilter(req, resp);
            return;
        }
        chain.doFilter(req, resp);
    }
}
