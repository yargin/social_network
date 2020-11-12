package com.getjavajob.training.yarginy.socialnetwork.web.servlets.group.viewservlets;

import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.group.Group;
import com.getjavajob.training.yarginy.socialnetwork.service.GroupService;
import com.getjavajob.training.yarginy.socialnetwork.service.GroupServiceImpl;
import com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Attributes;
import com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Jsps;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

public class GroupMemberRequestsServlet extends HttpServlet {
    private final GroupService groupService = new GroupServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long requestedId = (long) req.getAttribute(Attributes.REQUESTED_ID);
        Collection<Account> requesters = groupService.getGroupRequests(requestedId);
        req.setAttribute("requesters", requesters);
        Group group = groupService.selectGroup(requestedId);
        req.setAttribute("group", group);
        req.setAttribute("tab", "requests");
        req.getRequestDispatcher(Jsps.GROUP_JSP).forward(req, resp);
    }
}
