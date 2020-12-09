package com.getjavajob.training.yarginy.socialnetwork.web.servlets.accountpage.actionservlets;

import com.getjavajob.training.yarginy.socialnetwork.service.AccountService;
import com.getjavajob.training.yarginy.socialnetwork.service.AccountServiceImpl;
import com.getjavajob.training.yarginy.socialnetwork.web.servlets.AbstractPostServlet;
import com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Attributes;
import com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Pages;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.getjavajob.training.yarginy.socialnetwork.web.servlethelpers.RedirectHelper.redirect;
import static java.util.Objects.isNull;

public class FriendshipRequestAcceptServlet extends AbstractPostServlet {
    private final AccountService accountService = new AccountServiceImpl();

    @Override
    protected void safeDoPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String accept = req.getParameter("accept");
        if (isNull(accept)) {
            redirect(req, resp, Pages.FRIENDSHIP_REQUESTS);
            return;
        }
        long requesterId = (long) req.getAttribute(Attributes.REQUESTER_ID);
        long receiverId = (long) req.getAttribute(Attributes.RECEIVER_ID);
        if ("true".equals(accept)) {
            accountService.addFriend(requesterId, receiverId);
        } else {
            accountService.deleteFriendshipRequest(requesterId, receiverId);
        }
        redirect(req, resp, Pages.FRIENDSHIP_REQUESTS, Attributes.REQUESTED_ID, "" + receiverId);
    }
}
