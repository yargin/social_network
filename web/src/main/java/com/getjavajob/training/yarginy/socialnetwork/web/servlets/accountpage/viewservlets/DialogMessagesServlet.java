package com.getjavajob.training.yarginy.socialnetwork.web.servlets.accountpage.viewservlets;

import com.getjavajob.training.yarginy.socialnetwork.common.models.dialog.Dialog;
import com.getjavajob.training.yarginy.socialnetwork.common.models.message.Message;
import com.getjavajob.training.yarginy.socialnetwork.service.DialogService;
import com.getjavajob.training.yarginy.socialnetwork.service.DialogServiceImpl;
import com.getjavajob.training.yarginy.socialnetwork.service.messages.DialogMessageServiceImpl;
import com.getjavajob.training.yarginy.socialnetwork.service.messages.MessageService;
import com.getjavajob.training.yarginy.socialnetwork.web.servlethelpers.AccountInfoHelper;
import com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Attributes;
import com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Jsps;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.Objects;

import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Attributes.TAB;

public class DialogMessagesServlet extends HttpServlet {
    private final DialogService dialogService = new DialogServiceImpl();
    private final MessageService messageService = new DialogMessageServiceImpl();
    private final AccountInfoHelper infoHelper = new AccountInfoHelper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long firstAccount = (long) req.getAttribute(Attributes.RECEIVER_ID);
        long secondAccount = (long) req.getAttribute(Attributes.REQUESTER_ID);

        Dialog dialog = dialogService.selectByAccounts(firstAccount, secondAccount);

        if (!Objects.equals(dialog, dialogService.getNullDialog())) {
            req.setAttribute("dialog", dialog);
            req.setAttribute("id", dialog.getId());
            Collection<Message> messages = messageService.selectMessages(dialog.getId());
            req.setAttribute("messages", messages);
            req.setAttribute("type", "accountPrivate");
            req.setAttribute(TAB, "dialog");
        }
        infoHelper.setAccountInfo(req, firstAccount);
        req.getRequestDispatcher(Jsps.PRIVATE_MESSAGE).forward(req, resp);
    }
}
