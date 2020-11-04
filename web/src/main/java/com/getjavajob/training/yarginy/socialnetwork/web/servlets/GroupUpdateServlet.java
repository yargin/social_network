package com.getjavajob.training.yarginy.socialnetwork.web.servlets;

import com.getjavajob.training.yarginy.socialnetwork.common.exceptions.IncorrectDataException;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.group.Group;
import com.getjavajob.training.yarginy.socialnetwork.common.models.group.GroupImpl;
import com.getjavajob.training.yarginy.socialnetwork.service.GroupService;
import com.getjavajob.training.yarginy.socialnetwork.service.GroupServiceImpl;
import com.getjavajob.training.yarginy.socialnetwork.web.servlethelpers.GroupManagementAccessHelper;
import com.getjavajob.training.yarginy.socialnetwork.web.servlethelpers.UpdateGroupFieldsHelper;
import com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Attributes;
import com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Jsps;
import com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Pages;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.getjavajob.training.yarginy.socialnetwork.web.servlethelpers.RedirectHelper.redirectToReferer;

public class GroupUpdateServlet extends HttpServlet {
    private final GroupService groupService = new GroupServiceImpl();
    private final GroupManagementAccessHelper accessHelper = new GroupManagementAccessHelper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UpdateGroupFieldsHelper updater = new UpdateGroupFieldsHelper(req, resp, Attributes.GROUP_ID, Pages.GROUP);
        long requestedId = updater.getRequestedUserId(Attributes.GROUP_ID);
        if (requestedId == 0) {
            return;
        }
        Group group = updater.getOrCreateGroupAttribute(() -> groupService.selectGroup(requestedId));
        req.setAttribute("group", group);
        req.setAttribute("owner", group.getOwner());

        Account account = accessHelper.getAccountFromSession(req);
        if (accessHelper.isAbleToManage(group, account)) {
            req.getRequestDispatcher(Jsps.GROUP_UPDATE).forward(req, resp);
        } else {
            redirectToReferer(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UpdateGroupFieldsHelper updater = new UpdateGroupFieldsHelper(req, resp, Attributes.GROUP_ID, Pages.GROUP);
        long requestedId = updater.getRequestedUserId(Attributes.GROUP_ID);
        if (requestedId == 0) {
            return;
        }
        Group storedGroup = groupService.selectGroup(requestedId);
        Group group = new GroupImpl();
        group.setPhoto(storedGroup.getPhoto());
        group.setId(requestedId);
        Account account = accessHelper.getAccountFromSession(req);
        if (!accessHelper.isAbleToManage(group, account)) {
            redirectToReferer(req, resp);
            return;
        }

        updater.getValuesFromParams(group);
        boolean accepted = updater.isParamsAccepted();
        req.setAttribute(Attributes.GROUP, group);
        if (!accepted) {
            req.setAttribute(Attributes.GROUP, group);
            doGet(req, resp);
        } else {
            updateGroup(updater, group, storedGroup);
        }
    }

    private void updateGroup(UpdateGroupFieldsHelper updater, Group group, Group storedGroup) throws ServletException, IOException {
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
