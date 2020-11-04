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
    private final AccountInfoService accountInfoService = new AccountInfoServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UpdateAccountFieldsHelper updater = new UpdateAccountFieldsHelper(req, resp, USER_ID, Pages.MY_WALL);
        long requestedUserId = updater.getRequestedUserId(USER_ID);
        if (requestedUserId == 0) {
            return;
        }
        updater.checkUpdatePermissions(requestedUserId);

        AccountInfoDTO accountInfoDTO = updater.accountInfoDTOInit(() -> accountInfoService.select(requestedUserId));
        if (isNull(accountInfoDTO)) {
            return;
        }
        updater.initAccountAttributes(accountInfoDTO);

        req.setAttribute(TARGET, Pages.UPDATE_ACCOUNT);

        req.getRequestDispatcher(Jsps.UPDATE_ACCOUNT).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UpdateAccountFieldsHelper updater = new UpdateAccountFieldsHelper(req, resp, USER_ID, Pages.MY_WALL);
        long requestedId = updater.getRequestedUserId(USER_ID);
        if (requestedId == 0) {
            return;
        }

        if ("cancel".equals(req.getParameter("save"))) {
            updater.acceptActionOrRetry(true, null);
            return;
        }

        HttpSession session = req.getSession();

        AccountInfoDTO accountInfoDTO = (AccountInfoDTO) session.getAttribute(ACCOUNT_INFO);
        if (isNull(accountInfoDTO)) {
            redirect(req, resp, Pages.LOGOUT);
            return;
        }

        updater.getValuesFromParams(accountInfoDTO);

        boolean accepted = updater.isParamsAccepted();
        if (!accepted) {
            doGet(req, resp);
        } else {
            AccountInfoDTO storedAccountInfoDTO = accountInfoService.select(requestedId);
            update(updater, accountInfoDTO, storedAccountInfoDTO);
        }
    }

    private void update(UpdateAccountFieldsHelper updater, AccountInfoDTO accountInfoDTO,
                        AccountInfoDTO storedAccountInfoDTO) throws ServletException, IOException {
        boolean updated;
        try {
            updated = accountInfoService.update(accountInfoDTO, storedAccountInfoDTO);
        } catch (IncorrectDataException e) {
            updater.handleInfoExceptions(e, this::doGet);
            return;
        }
        updater.acceptActionOrRetry(updated, this::doGet);
    }
}
