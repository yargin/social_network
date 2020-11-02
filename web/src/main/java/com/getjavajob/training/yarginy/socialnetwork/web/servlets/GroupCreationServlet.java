package com.getjavajob.training.yarginy.socialnetwork.web.servlets;

import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.AccountImpl;
import com.getjavajob.training.yarginy.socialnetwork.common.models.group.Group;
import com.getjavajob.training.yarginy.socialnetwork.common.models.group.GroupImpl;
import com.getjavajob.training.yarginy.socialnetwork.service.AccountService;
import com.getjavajob.training.yarginy.socialnetwork.service.AccountServiceImpl;
import com.getjavajob.training.yarginy.socialnetwork.service.GroupService;
import com.getjavajob.training.yarginy.socialnetwork.service.GroupServiceImpl;
import com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Attributes;
import com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Jsps;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;

public class GroupCreationServlet extends HttpServlet {
    private final AccountService accountService = new AccountServiceImpl();
    private final GroupService groupService = new GroupServiceImpl();
    private final ThreadLocal<Boolean> paramsAccepted = new ThreadLocal<>();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(Jsps.GROUP_CREATION).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Group group = new GroupImpl();
        String name = req.getParameter("name");
        group.setName(name);
        String description = req.getParameter("description");
        group.setDescription(description);

        Part photo = req.getPart("photo");
//        group.setPhoto();

        long accountId = (long) req.getSession().getAttribute(Attributes.USER_ID);
        Account account = new AccountImpl();
        account.setId(accountId);
        group.setOwner(account);
        boolean creation = groupService.createGroup(group);
        if (creation) {
//            redirect to groups page
        } else {
            //set Error - name duplicate
        }
    }
}
