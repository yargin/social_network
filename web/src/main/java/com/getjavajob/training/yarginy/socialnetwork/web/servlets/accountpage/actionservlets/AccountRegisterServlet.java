package com.getjavajob.training.yarginy.socialnetwork.web.servlets.accountpage.actionservlets;

import com.getjavajob.training.yarginy.socialnetwork.common.exceptions.IncorrectDataException;
import com.getjavajob.training.yarginy.socialnetwork.common.models.password.Password;
import com.getjavajob.training.yarginy.socialnetwork.service.AuthService;
import com.getjavajob.training.yarginy.socialnetwork.service.aaa.AccountInfoKeeper;
import com.getjavajob.training.yarginy.socialnetwork.web.servlethelpers.UpdateAccountFieldsHelper;
import com.getjavajob.training.yarginy.socialnetwork.web.servlets.AbstractGetPostServlet;
import com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Attributes;
import com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Jsps;
import com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Pages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.getjavajob.training.yarginy.socialnetwork.web.servlethelpers.RedirectHelper.redirect;
import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Attributes.ACCOUNT_INFO;
import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Attributes.USER_ID;
import static java.util.Objects.isNull;

@Component
public class AccountRegisterServlet extends AbstractGetPostServlet {
    private AuthService authService;

    @Autowired
    public void setAuthService(AuthService authService) {
        this.authService = authService;
    }

    @Override
    protected void safeDoGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UpdateAccountFieldsHelper updater = new UpdateAccountFieldsHelper(req, resp, USER_ID, Pages.WALL);

        AccountInfoKeeper accountInfoKeeper = updater.getOrCreateAccountInfo(AccountInfoKeeper::new);
        if (isNull(req.getSession().getAttribute(ACCOUNT_INFO))) {
            req.getSession().setAttribute(ACCOUNT_INFO, accountInfoKeeper);
        }

        updater.initAccountAttributes(accountInfoKeeper);

        req.setAttribute(Attributes.TARGET, Pages.REGISTER);

        req.getRequestDispatcher(Jsps.REGISTER).forward(req, resp);
    }

    @Override
    protected void safeDoPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        UpdateAccountFieldsHelper updater = new UpdateAccountFieldsHelper(req, resp, USER_ID, Pages.WALL);

        AccountInfoKeeper accountInfoKeeper = (AccountInfoKeeper) req.getSession().getAttribute(Attributes.ACCOUNT_INFO);
        if (isNull(accountInfoKeeper)) {
            redirect(req, resp, Pages.LOGOUT);
            return;
        }

        updater.getValuesFromParams(accountInfoKeeper);

        Password password = updater.getPassword(accountInfoKeeper.getAccount());

        boolean accepted = updater.isParamsAccepted();
        req.setAttribute(ACCOUNT_INFO, accountInfoKeeper);
        if (!accepted) {
            safeDoGet(req, resp);
        } else {
            register(updater, accountInfoKeeper, password);
        }
    }

    private void register(UpdateAccountFieldsHelper updater, AccountInfoKeeper accountInfoKeeper, Password password) throws
            IOException, ServletException {
        boolean registered;
        try {
            registered = authService.register(accountInfoKeeper, password);
        } catch (IncorrectDataException e) {
            updater.handleInfoExceptions(e, this::safeDoGet);
            return;
        }
        updater.acceptActionOrRetry(registered, this::safeDoGet);
    }
}
