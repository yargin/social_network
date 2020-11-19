package com.getjavajob.training.yarginy.socialnetwork.web.servlets.accountpage.actionservlets;

import com.getjavajob.training.yarginy.socialnetwork.common.models.message.Message;
import com.getjavajob.training.yarginy.socialnetwork.service.AccountMessageService;
import com.getjavajob.training.yarginy.socialnetwork.service.AccountMessageServiceImpl;
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
    private final AccountMessageService accountMessageService = new AccountMessageServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long requestedUserId = (long) req.getAttribute(REQUESTED_ID);

        infoHelper.setAccountInfo(req, requestedUserId);

        Collection<Message> messages = accountMessageService.selectAccountWallMessages(requestedUserId);
        req.setAttribute("messages", messages);
        req.setAttribute(TAB, "wall");
        req.getRequestDispatcher(Jsps.MY_WALL).forward(req, resp);
    }
}
