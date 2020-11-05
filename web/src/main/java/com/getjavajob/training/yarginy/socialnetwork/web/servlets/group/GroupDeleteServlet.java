package com.getjavajob.training.yarginy.socialnetwork.web.servlets.group;

import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.group.Group;
import com.getjavajob.training.yarginy.socialnetwork.common.models.group.GroupImpl;
import com.getjavajob.training.yarginy.socialnetwork.service.GroupService;
import com.getjavajob.training.yarginy.socialnetwork.service.GroupServiceImpl;
import com.getjavajob.training.yarginy.socialnetwork.web.servlethelpers.GroupManagementAccessHelper;
import com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Attributes;
import com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Jsps;
import com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Pages;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.getjavajob.training.yarginy.socialnetwork.web.servlethelpers.RedirectHelper.redirect;
import static com.getjavajob.training.yarginy.socialnetwork.web.servlethelpers.RedirectHelper.redirectToReferer;

public class GroupDeleteServlet extends HttpServlet {
    private final GroupService groupService = new GroupServiceImpl();
    private final GroupManagementAccessHelper accessHelper = new GroupManagementAccessHelper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long requestedId = accessHelper.getRequestedId(req, resp, Attributes.GROUP_ID);
        if (requestedId == 0) {
            redirectToReferer(req, resp);
            return;
        }
        Group group = new GroupImpl();
        group.setId(requestedId);
        Account account = accessHelper.getAccountFromSession(req);
        if (accessHelper.isModerator(group, account)) {
            if (groupService.removeGroup(group)) {
                redirect(req, resp, Pages.GROUPS);
            } else {
                req.getRequestDispatcher(Jsps.ERROR).forward(req, resp);
            }
        } else {
            redirectToReferer(req, resp);
        }
    }
}
