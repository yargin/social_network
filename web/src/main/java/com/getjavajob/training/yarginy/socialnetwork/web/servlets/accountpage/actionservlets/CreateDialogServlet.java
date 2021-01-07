package com.getjavajob.training.yarginy.socialnetwork.web.servlets.accountpage.actionservlets;

import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.AccountImpl;
import com.getjavajob.training.yarginy.socialnetwork.common.models.dialog.Dialog;
import com.getjavajob.training.yarginy.socialnetwork.common.models.dialog.DialogImpl;
import com.getjavajob.training.yarginy.socialnetwork.common.models.message.Message;
import com.getjavajob.training.yarginy.socialnetwork.service.DialogService;
import com.getjavajob.training.yarginy.socialnetwork.web.servlethelpers.MessageHelper;
import com.getjavajob.training.yarginy.socialnetwork.web.servlets.AbstractPostServlet;
import com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Attributes;
import com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Pages;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.getjavajob.training.yarginy.socialnetwork.web.servlethelpers.RedirectHelper.redirect;
import static com.getjavajob.training.yarginy.socialnetwork.web.servlethelpers.RedirectHelper.redirectToReferer;
import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Attributes.REQUESTED_ID;
import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Attributes.REQUESTER_ID;
import static java.util.Objects.isNull;

public class CreateDialogServlet extends AbstractPostServlet {
    private DialogService dialogService;
    private MessageHelper messageHelper;

    @Autowired
    public void setDialogService(DialogService dialogService) {
        this.dialogService = dialogService;
    }

    @Autowired
    public void setMessageHelper(MessageHelper messageHelper) {
        this.messageHelper = messageHelper;
    }

    @Override
    protected void safeDoPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long authorId = (long) req.getAttribute(REQUESTER_ID);
        long receiverId = (long) req.getAttribute(Attributes.RECEIVER_ID);
        Dialog dialog = new DialogImpl();
        Account author = new AccountImpl();
        author.setId(authorId);
        Account receiver = new AccountImpl();
        receiver.setId(receiverId);
        dialog.setFirstAccount(author);
        dialog.setSecondAccount(receiver);
        Message message = messageHelper.getMessageFromRequest(req);
        String text = message.getText();
        if ((isNull(text) || text.trim().isEmpty()) && isNull(message.getImage())) {
            redirectToReferer(req, resp);
            return;
        }
        dialogService.create(dialog, message);
        dialog = dialogService.getByTalkers(authorId, receiverId);
        redirect(req, resp, Pages.DIALOG + '?' + REQUESTED_ID + '=' + dialog.getId());
    }
}
