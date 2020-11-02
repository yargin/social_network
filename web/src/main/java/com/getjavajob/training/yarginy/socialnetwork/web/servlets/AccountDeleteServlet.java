package com.getjavajob.training.yarginy.socialnetwork.web.servlets;

import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.AccountImpl;
import com.getjavajob.training.yarginy.socialnetwork.service.AccountService;
import com.getjavajob.training.yarginy.socialnetwork.service.AccountServiceImpl;
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
        long userId = (long) req.getSession().getAttribute(Attributes.USER_ID);
        String userEmail = (String) req.getSession().getAttribute(Attributes.USER_EMAIL);
        Account accountToDelete = new AccountImpl();
        accountToDelete.setId(userId);
        accountToDelete.setEmail(userEmail);
        boolean deleted = accountService.deleteAccount(accountToDelete);
        if (deleted) {
            req.getSession().invalidate();
            redirect(req, resp, Pages.LOGOUT);
        } else {
            req.getRequestDispatcher(Jsps.ERROR).forward(req, resp);
        }

    }
}
