package com.getjavajob.training.yarginy.socialnetwork.web.filters.messageaccess;

import com.getjavajob.training.yarginy.socialnetwork.service.DialogService;
import com.getjavajob.training.yarginy.socialnetwork.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.getjavajob.training.yarginy.socialnetwork.web.servlethelpers.AccountInfoHelper.isAdmin;
import static com.getjavajob.training.yarginy.socialnetwork.web.servlethelpers.RedirectHelper.redirectToReferer;
import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Attributes.*;

public class MessageAccessFilter extends HttpFilter {
    private GroupService groupService;
    private DialogService dialogService;

    @Autowired
    public void setGroupService(GroupService groupService) {
        this.groupService = groupService;
    }

    @Autowired
    public void setDialogService(DialogService dialogService) {
        this.dialogService = dialogService;
    }

    @Override
    public void init() {
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, getServletContext());
    }

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException,
            ServletException {
        long authorId = (long) req.getAttribute(REQUESTER_ID);
        long receiverId = (long) req.getAttribute(RECEIVER_ID);
        long currentUserId = (long) req.getSession().getAttribute(USER_ID);

        if (isAdmin(req, res, chain)) {
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
