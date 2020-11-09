package com.getjavajob.training.yarginy.socialnetwork.web.filters.groupinfoaccess;

import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.additionaldata.Role;
import com.getjavajob.training.yarginy.socialnetwork.service.GroupService;
import com.getjavajob.training.yarginy.socialnetwork.service.GroupServiceImpl;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Attributes.*;
import static java.util.Objects.isNull;

public class GroupInfoAccessSetterFilter extends HttpFilter {
    private final GroupService groupService = new GroupServiceImpl();

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain chain) throws IOException,
            ServletException {
        HttpSession session = req.getSession();
        long requestedGroupId;
        Object objectId = req.getAttribute(REQUESTED_ID);
        if (isNull(objectId)) {
            requestedGroupId = (long) req.getAttribute(RECEIVER_ID);
        } else {
            requestedGroupId = (long) objectId;
        }

        Account account = (Account) session.getAttribute(USER);
        long requesterId = account.getId();

        if ((!isNull(account.getRole()) && (Role.ADMIN.equals(account.getRole())))) {
            req.setAttribute("admin", true);
        }
        if (groupService.isOwner(requesterId, requestedGroupId)) {
            req.setAttribute("owner", true);
        }
        if (groupService.isModerator(requesterId, requestedGroupId)) {
            req.setAttribute("moderator", true);
        }
        if (groupService.isMember(requesterId, requestedGroupId)) {
            req.setAttribute("member", true);
        }

        chain.doFilter(req, resp);
    }
}
