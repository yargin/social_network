package com.getjavajob.training.yarginy.socialnetwork.web.servlets;

import com.getjavajob.training.yarginy.socialnetwork.common.exceptions.IncorrectDataException;
import com.getjavajob.training.yarginy.socialnetwork.service.AccountInfoService;
import com.getjavajob.training.yarginy.socialnetwork.service.AccountInfoServiceImpl;
import com.getjavajob.training.yarginy.socialnetwork.service.dto.AccountInfoDTO;
import com.getjavajob.training.yarginy.socialnetwork.web.servlethelpers.UpdateAccountFieldsHelper;
import com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Jsps;
import com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Pages;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.getjavajob.training.yarginy.socialnetwork.web.servlethelpers.RedirectHelper.redirect;
import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Attributes.*;
import static java.util.Objects.isNull;

public class AccountUpdateServlet extends HttpServlet {
    private static final String UPDATE_SUCCESS_URL = Pages.MY_WALL;
    private final AccountInfoService accountInfoService = new AccountInfoServiceImpl();
    private final UpdateAccountFieldsHelper updater = new UpdateAccountFieldsHelper();
    private final ThreadLocal<Boolean> paramsAccepted = new ThreadLocal<>();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long requestedUserId = updater.getRequestedUserId(req, resp);
        if (requestedUserId == 0) {
            return;
        }
        updater.checkUpdatePermissions(req, requestedUserId);

        AccountInfoDTO accountInfoDTO = updater.accountInfoDTOInit(req, () -> accountInfoService.select(requestedUserId));
        updater.initAccountAttributes(req, accountInfoDTO);

        req.setAttribute(TARGET, Pages.UPDATE_ACCOUNT);

        req.getRequestDispatcher(Jsps.UPDATE_ACCOUNT).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long requestedId = updater.getRequestedUserId(req, resp);
        if (requestedId == 0) {
            return;
        }

        if ("cancel".equals(req.getParameter("save"))) {
            updater.acceptActionOrRetry(req, resp, true, UPDATE_SUCCESS_URL, null, USER_ID, "" + requestedId);
            return;
        }

        paramsAccepted.set(true);
        HttpSession session = req.getSession();

        AccountInfoDTO accountInfoDTO = (AccountInfoDTO) session.getAttribute(ACCOUNT_INFO);
        if (isNull(accountInfoDTO)) {
            redirect(req, resp, Pages.LOGOUT);
            return;
        }

        updater.getValuesFromParams(req, accountInfoDTO, paramsAccepted);

        boolean accepted = paramsAccepted.get();
        paramsAccepted.remove();
        if (!accepted) {
            doGet(req, resp);
        } else {
            AccountInfoDTO storedAccountInfoDTO = accountInfoService.select(requestedId);
            update(req, resp, accountInfoDTO, storedAccountInfoDTO);
        }
    }

    private void update(HttpServletRequest req, HttpServletResponse resp, AccountInfoDTO accountInfoDTO,
                        AccountInfoDTO storedAccountInfoDTO) throws ServletException, IOException {
        boolean updated;
        try {
            updated = accountInfoService.update(accountInfoDTO, storedAccountInfoDTO);
        } catch (IncorrectDataException e) {
            updater.handleInfoExceptions(req, resp, e, this::doGet);
            return;
        }
        String requestedId = req.getParameter(USER_ID);
        updater.acceptActionOrRetry(req, resp, updated, UPDATE_SUCCESS_URL, this::doGet, USER_ID, requestedId);
    }
}
