package com.getjavajob.training.yarginy.socialnetwork.web.servlets.group;

import com.getjavajob.training.yarginy.socialnetwork.common.exceptions.IncorrectDataException;
import com.getjavajob.training.yarginy.socialnetwork.common.models.group.Group;
import com.getjavajob.training.yarginy.socialnetwork.common.models.group.GroupImpl;
import com.getjavajob.training.yarginy.socialnetwork.service.GroupService;
import com.getjavajob.training.yarginy.socialnetwork.service.GroupServiceImpl;
import com.getjavajob.training.yarginy.socialnetwork.web.servlethelpers.UpdateGroupFieldsHelper;
import com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Attributes;
import com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Jsps;
import com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Pages;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static java.util.Objects.isNull;

public class GroupUpdateServlet extends HttpServlet {
    private final GroupService groupService = new GroupServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UpdateGroupFieldsHelper updater = new UpdateGroupFieldsHelper(req, resp, Attributes.GROUP_ID, Pages.GROUP);
        long requestedId = (long) req.getAttribute(Attributes.REQUESTED_ID);

        //select at first visit
        Group group = updater.getOrCreateGroup(() -> groupService.get(requestedId));

        //save to session if wasn't
        if (isNull(req.getSession().getAttribute(Attributes.GROUP))) {
            req.getSession().setAttribute(Attributes.GROUP, group);
        }

        req.getRequestDispatcher(Jsps.GROUP_UPDATE).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UpdateGroupFieldsHelper updater = new UpdateGroupFieldsHelper(req, resp, Attributes.GROUP_ID, Pages.GROUP);
        if ("cancel".equals(req.getParameter("save"))) {
            updater.acceptActionOrRetry(true, null);
            return;
        }
        long requestedId = (long) req.getAttribute(Attributes.REQUESTED_ID);
        Group storedGroup = (Group) req.getSession().getAttribute(Attributes.GROUP);
        Group group = new GroupImpl();

        //for further views
        group.setPhoto(storedGroup.getPhoto());
        group.setId(requestedId);

        updater.getValuesFromParams(group);
        boolean accepted = updater.isParamsAccepted();
        //for next view
        req.setAttribute(Attributes.GROUP, group);
        if (!accepted) {
            doGet(req, resp);
        } else {
            updateGroup(updater, group, storedGroup);
        }
    }

    private void updateGroup(UpdateGroupFieldsHelper updater, Group group, Group storedGroup) throws ServletException,
            IOException {
        boolean updated;
        try {
            updated = groupService.updateGroup(group, storedGroup);
        } catch (IncorrectDataException e) {
            updater.handleInfoExceptions(e, this::doGet);
            return;
        }
        updater.setSuccessUrl(Pages.GROUP, Attributes.GROUP_ID, "" + group.getId());
        updater.acceptActionOrRetry(updated, this::doGet);
    }
}
