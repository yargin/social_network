package com.getjavajob.training.yarginy.socialnetwork.web.servlets;

import com.getjavajob.training.yarginy.socialnetwork.common.models.group.Group;
import com.getjavajob.training.yarginy.socialnetwork.common.models.group.GroupImpl;
import com.getjavajob.training.yarginy.socialnetwork.service.AccountService;
import com.getjavajob.training.yarginy.socialnetwork.service.AccountServiceImpl;
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

public class GroupCreationServlet extends HttpServlet {
    private final AccountService accountService = new AccountServiceImpl();
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
            //register
        } else {
            req.setAttribute(Attributes.GROUP, group);
            doGet(req, resp);
        }
    }
}
