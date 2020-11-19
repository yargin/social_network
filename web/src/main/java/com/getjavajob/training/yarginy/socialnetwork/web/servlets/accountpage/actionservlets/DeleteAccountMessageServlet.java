package com.getjavajob.training.yarginy.socialnetwork.web.servlets.accountpage.actionservlets;

import com.getjavajob.training.yarginy.socialnetwork.common.models.message.Message;
import com.getjavajob.training.yarginy.socialnetwork.common.models.message.MessageImpl;
import com.getjavajob.training.yarginy.socialnetwork.service.AccountMessageService;
import com.getjavajob.training.yarginy.socialnetwork.service.AccountMessageServiceImpl;
import com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Attributes;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.getjavajob.training.yarginy.socialnetwork.web.servlethelpers.RedirectHelper.redirectToReferer;

public class DeleteAccountMessageServlet extends HttpServlet {
    private final AccountMessageService messageService = new AccountMessageServiceImpl();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long messageId = (long) req.getAttribute(Attributes.REQUESTED_ID);
        Message message = new MessageImpl();
        message.setId(messageId);
        messageService.deleteMessage(message);
        redirectToReferer(req, resp);
    }
}
