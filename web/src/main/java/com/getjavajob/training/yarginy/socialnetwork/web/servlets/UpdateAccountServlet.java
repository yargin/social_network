package com.getjavajob.training.yarginy.socialnetwork.web.servlets;

import com.getjavajob.training.yarginy.socialnetwork.common.exceptions.IncorrectDataException;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.service.AccountService;
import com.getjavajob.training.yarginy.socialnetwork.service.AccountServiceImpl;
import com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Attributes;
import com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Jsps;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class UpdateAccountServlet extends HttpServlet {
    private final AccountService accountService = new AccountServiceImpl();
    private final ThreadLocal<Boolean> paramsAccepted = new ThreadLocal<>();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long id = (long) req.getSession().getAttribute(Attributes.USER_ID);
        Account account = accountService.getAccount(id);

        req.getRequestDispatcher(Jsps.UPDATE_ACCOUNT).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}
