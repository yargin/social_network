package com.getjavajob.training.yarginy.socialnetwork.web.servlets.group.actionservlets;

import com.getjavajob.training.yarginy.socialnetwork.service.GroupService;
import com.getjavajob.training.yarginy.socialnetwork.web.servlets.AbstractPostServlet;
import com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Attributes;
import com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Pages;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.getjavajob.training.yarginy.socialnetwork.web.servlethelpers.RedirectHelper.redirect;

public class AcceptRequestServlet extends AbstractPostServlet {
    private GroupService groupService;

    @Autowired
    public void setGroupService(GroupService groupService) {
        this.groupService = groupService;
    }

    @Override
    protected void safeDoPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        long accountId = (long) req.getAttribute(Attributes.REQUESTER_ID);
        long groupId = (long) req.getAttribute(Attributes.RECEIVER_ID);
        if ("true".equals(req.getParameter("accept"))) {
            groupService.acceptRequest(accountId, groupId);
        } else {
            groupService.declineRequest(accountId, groupId);
        }
        redirect(req, resp, Pages.GROUP_REQUESTS, Attributes.REQUESTED_ID, "" + groupId);
    }
}
