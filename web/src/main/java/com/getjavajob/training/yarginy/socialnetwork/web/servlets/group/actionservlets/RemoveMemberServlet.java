package com.getjavajob.training.yarginy.socialnetwork.web.servlets.group.actionservlets;

import com.getjavajob.training.yarginy.socialnetwork.service.GroupService;
import com.getjavajob.training.yarginy.socialnetwork.service.GroupServiceImpl;
import com.getjavajob.training.yarginy.socialnetwork.web.servlets.AbstractPostServlet;
import com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Attributes;
import com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Pages;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.getjavajob.training.yarginy.socialnetwork.web.servlethelpers.RedirectHelper.redirect;

public class RemoveMemberServlet extends AbstractPostServlet {
    private final GroupService groupService = new GroupServiceImpl();

    @Override
    protected void safeDoPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        long accountId = (long) req.getAttribute(Attributes.REQUESTER_ID);
        long groupId = (long) req.getAttribute(Attributes.RECEIVER_ID);
        groupService.leaveGroup(accountId, groupId);
        redirect(req, resp, Pages.GROUP_MEMBERS, Attributes.REQUESTED_ID, "" + groupId);
    }
}
