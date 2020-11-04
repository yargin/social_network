package com.getjavajob.training.yarginy.socialnetwork.web.servlets;

import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.group.Group;
import com.getjavajob.training.yarginy.socialnetwork.service.GroupService;
import com.getjavajob.training.yarginy.socialnetwork.service.GroupServiceImpl;
import com.getjavajob.training.yarginy.socialnetwork.web.servlethelpers.GroupManagementAccessHelper;
import com.getjavajob.training.yarginy.socialnetwork.web.servlethelpers.UpdateFieldsHelper;
import com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Attributes;
import com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Jsps;
import com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Pages;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GroupWallServlet extends HttpServlet {
    private final GroupService groupService = new GroupServiceImpl();
    private final GroupManagementAccessHelper accessHelper = new GroupManagementAccessHelper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UpdateFieldsHelper updater = new UpdateFieldsHelper(req, resp, Attributes.GROUP_ID, Pages.GROUP);
        long requestedId = updater.getRequestedUserId(Attributes.GROUP_ID);
        if (requestedId == 0) {
            return;
        }
        Group group = groupService.selectGroup(requestedId);
        req.setAttribute("group", group);
        req.setAttribute("owner", group.getOwner());

        Account account = accessHelper.getAccountFromSession(req);
        if (accessHelper.isAbleToManage(group, account)) {
            //set update buttons
        }
        req.getRequestDispatcher(Jsps.GROUP_INFO).forward(req, resp);
    }
}
