package com.getjavajob.training.yarginy.socialnetwork.web.servlets;

import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.AccountImpl;
import com.getjavajob.training.yarginy.socialnetwork.service.AccountService;
import com.getjavajob.training.yarginy.socialnetwork.service.AccountServiceImpl;
import com.getjavajob.training.yarginy.socialnetwork.web.servlethelpers.UpdateAccountFieldsHelper;
import com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Attributes;
import com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Jsps;
import com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Pages;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.getjavajob.training.yarginy.socialnetwork.web.servlethelpers.RedirectHelper.redirect;

public class AccountDeleteServlet extends HttpServlet {
    private final AccountService accountService = new AccountServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UpdateAccountFieldsHelper updater = new UpdateAccountFieldsHelper(req, resp, Attributes.USER_ID, Pages.MY_WALL);
        long requestedUserId = updater.getRequestedUserId(Attributes.USER_ID);
        if (requestedUserId == 0) {
            return;
        }
        updater.checkUpdatePermissions(requestedUserId);

        Account accountToDelete = new AccountImpl();
        accountToDelete.setId(requestedUserId);
        boolean deleted = accountService.deleteAccount(accountToDelete);
        if (deleted) {
            redirect(req, resp, Pages.MY_WALL);
        } else {
            req.getRequestDispatcher(Jsps.ERROR).forward(req, resp);
        }
    }
}
