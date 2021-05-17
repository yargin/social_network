package com.getjavajob.training.yarginy.socialnetwork.web.interceptors.group;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.additionaldata.Role;
import com.getjavajob.training.yarginy.socialnetwork.service.GroupService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Attributes.RECEIVER_ID;
import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Attributes.REQUESTED_ID;
import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Attributes.REQUESTER_ID;
import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Attributes.USER;
import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Messages.CHECK_PASSED;
import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Messages.TWO_STRINGS_WITH_SPACE;
import static java.util.Objects.isNull;

@Component
public class GroupAccessSetter extends HandlerInterceptorAdapter {
    private static final Logger logger = LoggerFactory.getLogger(GroupAccessSetter.class);
    private final GroupService groupService;

    public GroupAccessSetter(GroupService groupService) {
        this.groupService = groupService;
    }

    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse resp, Object handler) {
        HttpSession session = req.getSession();
        long requestedGroupId;
        Object objectId = req.getAttribute(REQUESTED_ID);
        if (isNull(objectId)) {
            requestedGroupId = (long) req.getAttribute(RECEIVER_ID);
        } else {
            requestedGroupId = (long) objectId;
        }

        Account account = (Account) session.getAttribute(USER);
        if ((!isNull(account.getRole()) && (Role.ADMIN.equals(account.getRole())))) {
            req.setAttribute("admin", true);
        }

        long requesterId = account.getId();

        Object requesterObject = req.getAttribute(REQUESTER_ID);
        if (!isNull(requesterObject) && requesterId == (long) requesterObject) {
            req.setAttribute("requestOwner", true);
        }

        if (groupService.isOwner(requesterId, requestedGroupId)) {
            req.setAttribute("owner", true);
        }
        if (groupService.isMembershipRequester(requesterId, requestedGroupId)) {
            req.setAttribute("requester", true);
        }
        if (groupService.isMember(requesterId, requestedGroupId)) {
            req.setAttribute("member", true);
        }
        if (groupService.isModerator(requesterId, requestedGroupId)) {
            req.setAttribute("moderator", true);
        }
        logger.info(TWO_STRINGS_WITH_SPACE, req.getRequestURI(), CHECK_PASSED);
        return true;
    }
}
