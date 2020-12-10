package com.getjavajob.training.yarginy.socialnetwork.web.servlets.group;

import com.getjavajob.training.yarginy.socialnetwork.common.models.group.Group;
import com.getjavajob.training.yarginy.socialnetwork.common.models.group.GroupImpl;
import com.getjavajob.training.yarginy.socialnetwork.service.GroupService;
import com.getjavajob.training.yarginy.socialnetwork.web.servlets.AbstractGetServlet;
import com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Attributes;
import com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Jsps;
import com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Pages;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.getjavajob.training.yarginy.socialnetwork.web.servlethelpers.RedirectHelper.redirect;

public class GroupDeleteServlet extends AbstractGetServlet {
    private GroupService groupService;

    @Autowired
    public void setGroupService(GroupService groupService) {
        this.groupService = groupService;
    }

    @Override
    protected void safeDoGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long requestedId = (long) req.getAttribute(Attributes.REQUESTED_ID);
        Group group = new GroupImpl();
        group.setId(requestedId);
        if (groupService.removeGroup(group)) {
            redirect(req, resp, Pages.GROUPS);
        } else {
            req.getRequestDispatcher(Jsps.ERROR).forward(req, resp);
        }
    }
}
