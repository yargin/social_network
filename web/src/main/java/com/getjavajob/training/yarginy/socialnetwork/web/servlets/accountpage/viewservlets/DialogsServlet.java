package com.getjavajob.training.yarginy.socialnetwork.web.servlets.accountpage.viewservlets;

import com.getjavajob.training.yarginy.socialnetwork.common.models.dialog.Dialog;
import com.getjavajob.training.yarginy.socialnetwork.service.AccountService;
import com.getjavajob.training.yarginy.socialnetwork.service.AccountServiceImpl;
import com.getjavajob.training.yarginy.socialnetwork.web.servlethelpers.AccountInfoHelper;
import com.getjavajob.training.yarginy.socialnetwork.web.servlets.AbstractGetServlet;
import com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Attributes;
import com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Jsps;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Attributes.TAB;

public class DialogsServlet extends AbstractGetServlet {
    private final AccountService accountService = new AccountServiceImpl();
    private final AccountInfoHelper infoHelper = new AccountInfoHelper();

    @Override
    protected void safeDoGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long requestedId = (long) req.getAttribute(Attributes.REQUESTED_ID);

        Collection<Dialog> dialogs = accountService.getDialogs(requestedId);
        req.setAttribute(Attributes.DIALOGS, dialogs);
        infoHelper.setAccountInfo(req, requestedId);
        req.setAttribute(TAB, "dialogs");
        req.getRequestDispatcher(Jsps.DIALOGS).forward(req, resp);
    }
}
