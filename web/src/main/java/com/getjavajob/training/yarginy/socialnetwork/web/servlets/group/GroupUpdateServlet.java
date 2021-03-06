package com.getjavajob.training.yarginy.socialnetwork.web.servlets.group;

import com.getjavajob.training.yarginy.socialnetwork.common.exceptions.IncorrectDataException;
import com.getjavajob.training.yarginy.socialnetwork.common.models.group.Group;
import com.getjavajob.training.yarginy.socialnetwork.service.GroupService;
import com.getjavajob.training.yarginy.socialnetwork.web.servlethelpers.UpdateGroupFieldsHelper;
import com.getjavajob.training.yarginy.socialnetwork.web.servlets.AbstractGetPostServlet;
import com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Attributes;
import com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Jsps;
import com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Pages;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Attributes.GROUP;
import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Attributes.REQUESTED_ID;
import static java.util.Objects.isNull;

public class GroupUpdateServlet extends AbstractGetPostServlet {
    @Autowired
    private GroupService groupService;

    @Override
    protected void safeDoGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UpdateGroupFieldsHelper updater = new UpdateGroupFieldsHelper(req, resp);
        long requestedId = (long) req.getAttribute(REQUESTED_ID);

        //select at first visit
        Group group = updater.getOrCreateGroup(() -> groupService.get(requestedId));

        //save to session if wasn't
        if (isNull(req.getSession().getAttribute(GROUP))) {
            req.getSession().setAttribute(GROUP, group);
        }
        updater.initGroupAttributes(group);

        req.getRequestDispatcher(Jsps.GROUP_UPDATE).forward(req, resp);
    }

    @Override
    protected void safeDoPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UpdateGroupFieldsHelper updater = new UpdateGroupFieldsHelper(req, resp);
        if ("cancel".equals(req.getParameter("save"))) {
            updater.acceptActionOrRetry(true, null);
            return;
        }
        long requestedId = (long) req.getAttribute(REQUESTED_ID);
        Group storedGroup = (Group) req.getSession().getAttribute(GROUP);
        Group group = new Group();

        //for further views
        group.setPhoto(storedGroup.getPhoto());
        group.setId(requestedId);

        updater.getValuesFromParams(group);
        boolean accepted = updater.isParamsAccepted();
        //for next view
        req.setAttribute(GROUP, group);
        if (!accepted) {
            safeDoGet(req, resp);
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
            updater.handleInfoExceptions(e, this::safeDoGet);
            return;
        }
        updater.setSuccessUrl(Pages.GROUP_WALL, Attributes.GROUP_ID, "" + group.getId());
        updater.acceptActionOrRetry(updated, this::safeDoGet);
    }
}
