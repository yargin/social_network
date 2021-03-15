package com.getjavajob.training.yarginy.socialnetwork.web.interceptors.group;

import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.additionaldata.Role;
import com.getjavajob.training.yarginy.socialnetwork.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Attributes.*;
import static java.util.Objects.isNull;

@Component
public class GroupAccessSetter extends HandlerInterceptorAdapter {
    private final GroupService groupService;

    @Autowired
    public GroupAccessSetter(GroupService groupService) {
        this.groupService = groupService;
    }

    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse resp, Object handler) throws Exception {
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
        return true;
    }
}
