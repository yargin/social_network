package com.getjavajob.training.yarginy.socialnetwork.web.servlets.accountpage.viewservlets;

import com.getjavajob.training.yarginy.socialnetwork.common.models.group.Group;
import com.getjavajob.training.yarginy.socialnetwork.service.GroupService;
import com.getjavajob.training.yarginy.socialnetwork.service.GroupServiceImpl;
import com.getjavajob.training.yarginy.socialnetwork.web.servlethelpers.AccountInfoHelper;
import com.getjavajob.training.yarginy.socialnetwork.web.servlets.AbstractGetServlet;
import com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Jsps;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;

import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Attributes.*;

public class GroupsListServlet extends AbstractGetServlet {
    public static final String ALL_GROUPS_LIST = "allgroups";
    private final GroupService groupService = new GroupServiceImpl();
    private final AccountInfoHelper infoHelper = new AccountInfoHelper();

    @Override
    protected void safeDoGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long accountId = (long) req.getAttribute(REQUESTED_ID);

        if ("true".equals(req.getParameter(ALL_GROUPS_LIST))) {
            Map<Group, Boolean> unjoinedGroups = groupService.getAllUnjoinedGroupsAreRequested(accountId);
            req.setAttribute(GROUPS, unjoinedGroups);
            req.setAttribute(ALL_GROUPS_LIST, "true");
        } else {
            Collection<Group> joinedGroups = groupService.getAccountGroups(accountId);
            req.setAttribute(GROUPS, joinedGroups);
            req.removeAttribute(ALL_GROUPS_LIST);
        }

        infoHelper.setAccountInfo(req, accountId);
        req.setAttribute(TAB, "groups");
        req.getRequestDispatcher(Jsps.GROUPS_LIST).forward(req, resp);
    }
}
