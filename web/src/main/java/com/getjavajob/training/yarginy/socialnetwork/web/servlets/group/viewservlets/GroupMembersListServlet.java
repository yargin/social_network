package com.getjavajob.training.yarginy.socialnetwork.web.servlets.group.viewservlets;

import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.group.Group;
import com.getjavajob.training.yarginy.socialnetwork.service.GroupService;
import com.getjavajob.training.yarginy.socialnetwork.web.servlets.AbstractGetServlet;
import com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Attributes;
import com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Jsps;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public class GroupMembersListServlet extends AbstractGetServlet {
    private GroupService groupService;

    @Autowired
    public void setGroupService(GroupService groupService) {
        this.groupService = groupService;
    }

    @Override
    protected void safeDoGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long requestedId = (long) req.getAttribute(Attributes.REQUESTED_ID);
        Map<Account, Boolean> members = groupService.getGroupMembersModerators(requestedId);
        req.setAttribute("members", members);
        Group group = groupService.get(requestedId);
        req.setAttribute("group", group);
        req.setAttribute("tab", "members");
        req.getRequestDispatcher(Jsps.GROUP_JSP).forward(req, resp);
    }
}
