package com.getjavajob.training.yarginy.socialnetwork.web.servlets.accountpage.actionservlets;

import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.AccountImpl;
import com.getjavajob.training.yarginy.socialnetwork.common.models.message.Message;
import com.getjavajob.training.yarginy.socialnetwork.common.models.message.MessageImpl;
import com.getjavajob.training.yarginy.socialnetwork.service.AccountMessageService;
import com.getjavajob.training.yarginy.socialnetwork.service.AccountMessageServiceImpl;
import com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Attributes;
import com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Pages;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;

import static com.getjavajob.training.yarginy.socialnetwork.web.servlethelpers.RedirectHelper.redirect;
import static java.util.Objects.isNull;

public class AddAccountMessageServlet extends HttpServlet {
    private final AccountMessageService messageService = new AccountMessageServiceImpl();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Message message = new MessageImpl();
        long senderId = (long) req.getAttribute(Attributes.REQUESTER_ID);
        long receiverId = (long) req.getAttribute(Attributes.RECEIVER_ID);
        String text = req.getParameter("text");
        Part part = req.getPart("image");
        if (part.getSize() < 1 && text.trim().isEmpty()) {
            redirect(req, resp, Pages.MY_WALL, Attributes.REQUESTED_ID, "" + receiverId);
            return;
        }

        message.setText(text);
        Account author = new AccountImpl();
        author.setId(senderId);
        if (!isNull(part)) {
            try (InputStream inputStream = part.getInputStream()) {
                message.setImage(inputStream);
            }
        }
        message.setAuthor(author);
        message.setReceiverId(receiverId);
        if ("accountWall".equals(req.getParameter("type"))) {
            messageService.addAccountWallMessage(message);
        } else {

        }
        redirect(req, resp, Pages.MY_WALL, Attributes.REQUESTED_ID, "" + receiverId);
    }
}
