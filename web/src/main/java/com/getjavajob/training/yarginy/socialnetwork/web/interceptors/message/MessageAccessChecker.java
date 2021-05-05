package com.getjavajob.training.yarginy.socialnetwork.web.interceptors.message;

import com.getjavajob.training.yarginy.socialnetwork.service.DialogService;
import com.getjavajob.training.yarginy.socialnetwork.service.GroupService;
import com.getjavajob.training.yarginy.socialnetwork.web.helpers.AccountInfoHelper;
import com.getjavajob.training.yarginy.socialnetwork.web.helpers.Redirector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Attributes.RECEIVER_ID;
import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Attributes.REQUESTER_ID;
import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Attributes.USER_ID;
import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Messages.CHECK_FAILED;
import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Messages.CHECK_PASSED;


@Component
public class MessageAccessChecker extends HandlerInterceptorAdapter {
    private static final Logger logger = LoggerFactory.getLogger(MessageAccessChecker.class);
    private final GroupService groupService;
    private final DialogService dialogService;
    private final AccountInfoHelper infoHelper;
    private final Redirector redirector;

    public MessageAccessChecker(GroupService groupService, DialogService dialogService, AccountInfoHelper infoHelper,
                                Redirector redirector) {
        this.groupService = groupService;
        this.dialogService = dialogService;
        this.infoHelper = infoHelper;
        this.redirector = redirector;
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
        if ("account".equals(type)) {
            hasAccess = isAuthor || isReceiver;
        } else if ("dialog".equals(type)) {
            hasAccess = isAuthor || dialogService.isTalker(currentUserId, receiverId);
        } else if ("group".equals(type)) {
            hasAccess = isAuthor || groupService.isModerator(currentUserId, receiverId) || groupService.
                    isOwner(currentUserId, receiverId);
        }
        if (!hasAccess) {
            logger.info(CHECK_FAILED);
            redirector.redirectToReferer(req, resp);
        }
        logger.info(CHECK_PASSED);
        return hasAccess;
    }
}
