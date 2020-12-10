package com.getjavajob.training.yarginy.socialnetwork.web.servlets.messages;

import com.getjavajob.training.yarginy.socialnetwork.common.models.dialog.Dialog;
import com.getjavajob.training.yarginy.socialnetwork.service.DialogService;
import com.getjavajob.training.yarginy.socialnetwork.web.servlethelpers.RedirectHelper;
import com.getjavajob.training.yarginy.socialnetwork.web.servlets.AbstractPostServlet;
import com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Attributes;
import com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Jsps;
import com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Pages;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Attributes.REQUESTED_ID;

public class PrivateMessageServlet extends AbstractPostServlet {
    private DialogService dialogService;

    @Autowired
    public void setDialogService(DialogService dialogService) {
        this.dialogService = dialogService;
    }

    @Override
    protected void safeDoPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long receiverId = (long) req.getAttribute(Attributes.RECEIVER_ID);
        long requesterId = (long) req.getAttribute(Attributes.REQUESTER_ID);

        Dialog dialog = dialogService.getByTalkers(receiverId, requesterId);
        if (!Objects.equals(dialog, dialogService.getNullDialog())) {
            RedirectHelper.redirect(req, resp, Pages.DIALOG, REQUESTED_ID, "" + dialog.getId());
        } else {
            req.setAttribute(REQUESTED_ID, receiverId);
            req.getRequestDispatcher(Jsps.NEW_DIALOG).forward(req, resp);
        }
    }
}
