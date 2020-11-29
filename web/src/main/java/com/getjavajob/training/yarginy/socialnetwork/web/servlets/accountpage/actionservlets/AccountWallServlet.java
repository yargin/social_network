package com.getjavajob.training.yarginy.socialnetwork.web.servlets.accountpage.actionservlets;

import com.getjavajob.training.yarginy.socialnetwork.common.models.message.Message;
import com.getjavajob.training.yarginy.socialnetwork.service.messages.AccountWallMessageServiceImpl;
import com.getjavajob.training.yarginy.socialnetwork.service.messages.MessageService;
import com.getjavajob.training.yarginy.socialnetwork.web.servlethelpers.AccountInfoHelper;
import com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Jsps;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Attributes.REQUESTED_ID;
import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Attributes.TAB;

public class AccountWallServlet extends HttpServlet {
    private final AccountInfoHelper infoHelper = new AccountInfoHelper();
    private final MessageService accountMessageService = new AccountWallMessageServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long requestedUserId = (long) req.getAttribute(REQUESTED_ID);

        infoHelper.setAccountInfo(req, requestedUserId);

        Collection<Message> messages = accountMessageService.selectMessages(requestedUserId);
        req.setAttribute("messages", messages);
        req.setAttribute("type", "accountWall");
        req.setAttribute("id", requestedUserId);
        req.setAttribute(TAB, "wall");
        req.getRequestDispatcher(Jsps.MY_WALL).forward(req, resp);
    }
}
