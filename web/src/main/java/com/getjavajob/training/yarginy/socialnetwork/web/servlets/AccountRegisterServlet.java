package com.getjavajob.training.yarginy.socialnetwork.web.servlets;

import com.getjavajob.training.yarginy.socialnetwork.common.exceptions.IncorrectDataException;
import com.getjavajob.training.yarginy.socialnetwork.common.models.password.Password;
import com.getjavajob.training.yarginy.socialnetwork.service.AuthService;
import com.getjavajob.training.yarginy.socialnetwork.service.AuthServiceImpl;
import com.getjavajob.training.yarginy.socialnetwork.service.dto.AccountInfoDTO;
import com.getjavajob.training.yarginy.socialnetwork.web.servlethelpers.UpdateAccountFieldsHelper;
import com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Attributes;
import com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Jsps;
import com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Pages;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.getjavajob.training.yarginy.socialnetwork.web.servlethelpers.RedirectHelper.redirect;
import static java.util.Objects.isNull;

public class AccountRegisterServlet extends HttpServlet {
    private static final String REG_SUCCESS_URL = Pages.MY_WALL;
    private static final AuthService AUTH_SERVICE = new AuthServiceImpl();
    private final UpdateAccountFieldsHelper updater = new UpdateAccountFieldsHelper();
    private final ThreadLocal<Boolean> paramsAccepted = new ThreadLocal<>();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        String userName = (String) session.getAttribute(Attributes.USER_NAME);
        if (!isNull(userName)) {
            resp.sendRedirect(req.getContextPath() + REG_SUCCESS_URL);
            return;
        }

        AccountInfoDTO accountInfoDTO = updater.accountInfoDTOInit(req, AccountInfoDTO::new);
        updater.initAccountAttributes(req, accountInfoDTO);

        req.setAttribute(Attributes.TARGET, Pages.REGISTER);

        req.getRequestDispatcher(Jsps.REGISTER).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        paramsAccepted.set(true);

        AccountInfoDTO accountInfoDTO = (AccountInfoDTO) req.getSession().getAttribute(Attributes.ACCOUNT_INFO);
        if (isNull(accountInfoDTO)) {
            redirect(req, resp, Pages.LOGOUT);
            return;
        }

        updater.getValuesFromParams(req, accountInfoDTO, paramsAccepted);

        Password password = updater.getPassword(req, accountInfoDTO.getAccount(), paramsAccepted);

        if (!paramsAccepted.get()) {
            doGet(req, resp);
        } else {
            register(req, resp, accountInfoDTO, password);
            paramsAccepted.remove();
        }
    }

    private void register(HttpServletRequest req, HttpServletResponse resp, AccountInfoDTO accountInfoDTO, Password
            password) throws IOException, ServletException {
        boolean registered;
        try {
            registered = AUTH_SERVICE.register(accountInfoDTO, password);
        } catch (IncorrectDataException e) {
            updater.handleInfoExceptions(req, resp, e, this::doGet);
            return;
        }
        updater.acceptActionOrRetry(req, resp, registered, REG_SUCCESS_URL, this::doGet);
    }
}
