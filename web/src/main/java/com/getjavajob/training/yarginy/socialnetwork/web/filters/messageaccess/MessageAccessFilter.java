package com.getjavajob.training.yarginy.socialnetwork.web.filters.messageaccess;

import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.additionaldata.Role;
import com.getjavajob.training.yarginy.socialnetwork.service.DialogService;
import com.getjavajob.training.yarginy.socialnetwork.service.DialogServiceImpl;
import com.getjavajob.training.yarginy.socialnetwork.service.GroupService;
import com.getjavajob.training.yarginy.socialnetwork.service.GroupServiceImpl;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.getjavajob.training.yarginy.socialnetwork.web.servlethelpers.RedirectHelper.redirectToReferer;
import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Attributes.*;
import static java.util.Objects.isNull;

public class MessageAccessFilter extends HttpFilter {
    private final GroupService groupService = new GroupServiceImpl();
    private final DialogService dialogService = new DialogServiceImpl();

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException,
            ServletException {
        long authorId = (long) req.getAttribute(REQUESTER_ID);
        long receiverId = (long) req.getAttribute(RECEIVER_ID);
        long currentUserId = (long) req.getSession().getAttribute(USER_ID);

        Account account = (Account) req.getSession().getAttribute(USER);
        if ((!isNull(account.getRole()) && (Role.ADMIN.equals(account.getRole())))) {
            req.setAttribute("admin", true);
            chain.doFilter(req, res);
            return;
        }

        boolean isAuthor = authorId == currentUserId;
        boolean isReceiver = receiverId == currentUserId;

        boolean hasAccess = false;
        String type = req.getParameter("type");
        if ("accountWall".equals(type)) {
            hasAccess = isAuthor || isReceiver;
        } else if ("accountPrivate".equals(type)) {
            hasAccess = isAuthor || dialogService.isTalker(currentUserId, receiverId);
        } else if ("groupWall".equals(type)) {
            hasAccess = isAuthor || groupService.isModerator(currentUserId, receiverId) || groupService.
                    isOwner(currentUserId, receiverId);
        }
        if (hasAccess) {
            chain.doFilter(req, res);
        } else {
            redirectToReferer(req, res);
        }
    }
}
