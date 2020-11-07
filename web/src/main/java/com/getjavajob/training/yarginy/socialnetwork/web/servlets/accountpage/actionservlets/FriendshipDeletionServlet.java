package com.getjavajob.training.yarginy.socialnetwork.web.servlets.accountpage.actionservlets;

import com.getjavajob.training.yarginy.socialnetwork.service.AccountService;
import com.getjavajob.training.yarginy.socialnetwork.service.AccountServiceImpl;
import com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Attributes;
import com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Pages;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.getjavajob.training.yarginy.socialnetwork.web.servlethelpers.RedirectHelper.redirect;

public class FriendshipDeletionServlet extends HttpServlet {
    private final AccountService accountService = new AccountServiceImpl();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long requesterId = (long) req.getAttribute(Attributes.REQUESTER_ID);
        long receiverId = (long) req.getAttribute(Attributes.RECEIVER_ID);
        accountService.removeFriend(requesterId, receiverId);
        redirect(req, resp, Pages.FRIENDS, Attributes.REQUESTED_ID, "" + receiverId);
    }
}
