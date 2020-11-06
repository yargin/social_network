package com.getjavajob.training.yarginy.socialnetwork.web.servlets.group;

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

public class GroupsListServlet extends HttpServlet {
    public static final String GROUPS = "groups";
    public static final String ALL_GROUPS_LIST = "allgroups";
    private final GroupService groupService = new GroupServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long accountId = (long) req.getAttribute(Attributes.USER_ID);

        String jsp = Jsps.GROUPS_LIST;
        if ("true".equals(req.getParameter(ALL_GROUPS_LIST))) {
            Collection<Group> unjoinedGroups = groupService.getNonJoinedGroups(accountId);
            req.setAttribute(GROUPS, unjoinedGroups);
            req.setAttribute(ALL_GROUPS_LIST, "true");
        } else {
            Collection<Group> joinedGroups = groupService.getAccountGroups(accountId);
            req.setAttribute(GROUPS, joinedGroups);
            req.removeAttribute(ALL_GROUPS_LIST);
        }
        req.getRequestDispatcher(jsp).forward(req, resp);
    }
}
