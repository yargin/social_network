package com.getjavajob.training.yarginy.socialnetwork.web.interceptors.message;

import com.getjavajob.training.yarginy.socialnetwork.service.DialogService;
import com.getjavajob.training.yarginy.socialnetwork.service.GroupService;
import com.getjavajob.training.yarginy.socialnetwork.web.helpers.AccountInfoHelper;
import com.getjavajob.training.yarginy.socialnetwork.web.helpers.RedirectHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Attributes.*;

@Component
public class MessageAccessChecker extends HandlerInterceptorAdapter {
    private final GroupService groupService;
    private final DialogService dialogService;
    private final AccountInfoHelper infoHelper;
    private final RedirectHelper redirectHelper;

    @Autowired
    public MessageAccessChecker(GroupService groupService, DialogService dialogService, AccountInfoHelper infoHelper,
                                RedirectHelper redirectHelper) {
        this.groupService = groupService;
        this.dialogService = dialogService;
        this.infoHelper = infoHelper;
        this.redirectHelper = redirectHelper;
    }

    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse resp, Object handler) throws Exception {
        long authorId = (long) req.getAttribute(REQUESTER_ID);
        long receiverId = (long) req.getAttribute(RECEIVER_ID);
        long currentUserId = (long) req.getSession().getAttribute(USER_ID);

        if (infoHelper.isAdmin(req)) {
            return true;
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
        if (!hasAccess) {
            redirectHelper.redirectToReferer(req, resp);
        }
        return hasAccess;
    }
}
