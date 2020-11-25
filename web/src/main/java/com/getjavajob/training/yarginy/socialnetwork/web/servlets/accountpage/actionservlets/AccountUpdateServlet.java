package com.getjavajob.training.yarginy.socialnetwork.web.servlets.accountpage.actionservlets;

import com.getjavajob.training.yarginy.socialnetwork.common.exceptions.IncorrectDataException;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.AccountImpl;
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
import java.util.ArrayList;

import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Attributes.*;
import static java.util.Objects.isNull;

public class AccountUpdateServlet extends HttpServlet {
    private final AccountInfoService accountInfoService = new AccountInfoServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UpdateAccountFieldsHelper updater = new UpdateAccountFieldsHelper(req, resp, USER_ID, Pages.MY_WALL);
        long requestedUserId = (long) req.getAttribute(REQUESTED_ID);

        //select at first visit
        AccountInfoDTO accountInfoDTO = updater.getOrCreateAccountInfo(() -> accountInfoService.select(requestedUserId));
        //save original to session if wasn't
        if (isNull(req.getSession().getAttribute(ACCOUNT_INFO))) {
            req.getSession().setAttribute(ACCOUNT_INFO, accountInfoDTO);
        }

        updater.initAccountAttributes(accountInfoDTO);

        req.setAttribute(TARGET, Pages.UPDATE_ACCOUNT);

        req.getRequestDispatcher(Jsps.UPDATE_ACCOUNT).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UpdateAccountFieldsHelper updater = new UpdateAccountFieldsHelper(req, resp, REQUESTED_ID, Pages.MY_WALL);

        if ("cancel".equals(req.getParameter("save"))) {
            updater.acceptActionOrRetry(true, null);
            return;
        }

        HttpSession session = req.getSession();
        AccountInfoDTO accountInfoDTO = new AccountInfoDTO(new AccountImpl(), new ArrayList<>());
        AccountInfoDTO storedAccountInfoDTO = (AccountInfoDTO) session.getAttribute(ACCOUNT_INFO);
        //set non updatable values
        Account account = accountInfoDTO.getAccount();
        Account storedAccount = storedAccountInfoDTO.getAccount();
        account.setEmail(storedAccount.getEmail());
        account.setRegistrationDate(storedAccount.getRegistrationDate());

        updater.getValuesFromParams(accountInfoDTO);

        if (isNull(session.getAttribute(PHOTO))) {
            session.setAttribute(PHOTO, storedAccount.getPhoto());
        }
        if (isNull(account.getPhoto())) {
            account.setPhoto((byte[]) session.getAttribute(PHOTO));
        } else {
            session.setAttribute(PHOTO, account.getPhoto());
        }

        req.setAttribute(ACCOUNT_INFO, accountInfoDTO);
        boolean accepted = updater.isParamsAccepted();
        if (!accepted) {
            doGet(req, resp);
        } else {
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
