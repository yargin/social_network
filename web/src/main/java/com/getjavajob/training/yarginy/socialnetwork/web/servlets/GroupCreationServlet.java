package com.getjavajob.training.yarginy.socialnetwork.web.servlets;

import com.getjavajob.training.yarginy.socialnetwork.common.exceptions.IncorrectData;
import com.getjavajob.training.yarginy.socialnetwork.common.exceptions.IncorrectDataException;
import com.getjavajob.training.yarginy.socialnetwork.common.models.group.Group;
import com.getjavajob.training.yarginy.socialnetwork.common.models.group.GroupImpl;
import com.getjavajob.training.yarginy.socialnetwork.service.GroupService;
import com.getjavajob.training.yarginy.socialnetwork.service.GroupServiceImpl;
import com.getjavajob.training.yarginy.socialnetwork.web.servlethelpers.UpdateGroupFieldsHelper;
import com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Attributes;
import com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Jsps;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Attributes.NAME_DUPLICATE;

public class GroupCreationServlet extends HttpServlet {
    private final String REG_SUCCESS_URL = "/group";
    private final GroupService groupService = new GroupServiceImpl();
    private final UpdateGroupFieldsHelper updater = new UpdateGroupFieldsHelper();
    private final ThreadLocal<Boolean> paramsAccepted = new ThreadLocal<>();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Group group = updater.getOrCreateGroupAttribute(req, GroupImpl::new);
        updater.initGroupAttributes(req, group);
        req.getRequestDispatcher(Jsps.GROUP_CREATION).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Group group = new GroupImpl();
        paramsAccepted.set(true);
        updater.getValuesFromParams(req, group, paramsAccepted);

        boolean accepted = paramsAccepted.get();
        paramsAccepted.remove();
        if (accepted) {
            group.setOwner(updater.getAccountFromSession(req));
            createGroup(req, resp, group);
        } else {
            req.setAttribute(Attributes.GROUP, group);
            doGet(req, resp);
        }
    }

    private void createGroup(HttpServletRequest req, HttpServletResponse resp, Group group) throws IOException,
            ServletException {
        boolean created;
        try {
            created = groupService.createGroup(group);
        } catch (IncorrectDataException e) {
            if (e.getType() == IncorrectData.GROUP_DUPLICATE) {
                req.setAttribute(NAME_DUPLICATE, e.getType().getPropertyKey());
            }
            doGet(req, resp);
            return;
        }
        Group createdGroup = groupService.selectGroup(group);
        updater.acceptActionOrRetry(req, resp, created, REG_SUCCESS_URL, this::doGet, "groupId", "" +
                createdGroup.getId());
    }
}
