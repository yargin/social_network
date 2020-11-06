package com.getjavajob.training.yarginy.socialnetwork.web.servlets.friendship;

import com.getjavajob.training.yarginy.socialnetwork.service.AccountService;
import com.getjavajob.training.yarginy.socialnetwork.service.AccountServiceImpl;
import com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Attributes;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.getjavajob.training.yarginy.socialnetwork.web.servlethelpers.RedirectHelper.redirectToReferer;

public class FriendshipRequestCreationServlet extends HttpServlet {
    private final AccountService accountService = new AccountServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //check first is from session
        //add
        long currentId = (long) req.getSession().getAttribute(Attributes.USER_ID);
        long requestedId = (long) req.getAttribute(Attributes.REQUESTED_ID);
        if (currentId != requestedId) {
            redirectToReferer(req, resp);
            return;
        }
        accountService.createFriendshipRequest(currentId, requestedId);
    }
}
