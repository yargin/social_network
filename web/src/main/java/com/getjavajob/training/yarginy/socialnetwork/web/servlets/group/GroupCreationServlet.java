package com.getjavajob.training.yarginy.socialnetwork.web.servlets.group;

import com.getjavajob.training.yarginy.socialnetwork.common.exceptions.IncorrectDataException;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.group.Group;
import com.getjavajob.training.yarginy.socialnetwork.common.models.group.GroupImpl;
import com.getjavajob.training.yarginy.socialnetwork.service.GroupService;
import com.getjavajob.training.yarginy.socialnetwork.service.GroupServiceImpl;
import com.getjavajob.training.yarginy.socialnetwork.web.servlethelpers.UpdateGroupFieldsHelper;
import com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Jsps;
import com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Pages;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Attributes.*;
import static java.util.Objects.isNull;

public class GroupCreationServlet extends HttpServlet {
    private final GroupService groupService = new GroupServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UpdateGroupFieldsHelper updater = new UpdateGroupFieldsHelper(req, resp, GROUP_ID, Pages.GROUP);
        Group group = updater.getOrCreateGroup(GroupImpl::new);
        updater.initGroupAttributes(group);
        req.getRequestDispatcher(Jsps.GROUP_CREATION).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UpdateGroupFieldsHelper updater = new UpdateGroupFieldsHelper(req, resp, GROUP_ID, Pages.GROUP);
        Group group = new GroupImpl();
        updater.getValuesFromParams(group);
        if (!isNull(group.getPhoto())) {
            req.getSession().setAttribute(PHOTO, group.getPhoto());
        } else if (!isNull(req.getSession().getAttribute(PHOTO))) {
            group.setPhoto((byte[]) req.getSession().getAttribute(PHOTO));
        }

        boolean accepted = updater.isParamsAccepted();
        req.setAttribute(GROUP, group);
        if (accepted) {
            group.setOwner((Account) req.getSession().getAttribute(USER));
            createGroup(updater, group);
        } else {
            req.setAttribute(GROUP, group);
            doGet(req, resp);
        }
    }

    private void createGroup(UpdateGroupFieldsHelper updater, Group group) throws IOException, ServletException {
        boolean created;
        try {
            created = groupService.createGroup(group);
        } catch (IncorrectDataException e) {
            updater.handleInfoExceptions(e, this::doGet);
            return;
        }
        Group createdGroup = groupService.selectGroup(group);
        updater.setSuccessUrl(Pages.GROUP, GROUP_ID, "" + createdGroup.getId());
        updater.acceptActionOrRetry(created, this::doGet);
    }
}
