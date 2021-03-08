package com.getjavajob.training.yarginy.socialnetwork.web.servlets.accountpage.actionservlets;

import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.service.AccountInfoService;
import com.getjavajob.training.yarginy.socialnetwork.service.infokeepers.AccountInfoKeeper;
import com.getjavajob.training.yarginy.socialnetwork.web.servlethelpers.AccountFieldsUpdater;
import com.getjavajob.training.yarginy.socialnetwork.web.servlets.AbstractGetPostServlet;
import com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Jsps;
import com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Pages;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;

import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Attributes.*;
import static java.util.Objects.isNull;

public class AccountUpdateServlet extends AbstractGetPostServlet {
    private AccountInfoService accountInfoService;

    @Autowired
    public void setAccountInfoService(AccountInfoService accountInfoService) {
        this.accountInfoService = accountInfoService;
    }

    @Override
    protected void safeDoGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        AccountFieldsUpdater updater = new AccountFieldsUpdater(req, req.getSession());
        long requestedUserId = (long) req.getAttribute(REQUESTED_ID);

        //select at first visit
        AccountInfoKeeper accountInfoKeeper = updater.getOrCreateAccountInfo(() -> accountInfoService.select(requestedUserId));
        //save original to session if wasn't
        if (isNull(req.getSession().getAttribute(ACCOUNT_INFO))) {
            req.getSession().setAttribute(ACCOUNT_INFO, accountInfoKeeper);
        }

        updater.initAccountAttributes(accountInfoKeeper);

        req.setAttribute(TARGET, Pages.UPDATE_ACCOUNT);

        req.getRequestDispatcher(Jsps.UPDATE_ACCOUNT).forward(req, resp);
    }

    @Override
    protected void safeDoPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        AccountFieldsUpdater updater = new AccountFieldsUpdater(req, req.getSession());

        if ("cancel".equals(req.getParameter("save"))) {
            updater.acceptActionOrRetry(true, null);
            return;
        }

        HttpSession session = req.getSession();
        AccountInfoKeeper accountInfoKeeper = new AccountInfoKeeper(new Account(), new ArrayList<>());
        AccountInfoKeeper storedAccountInfoKeeper = (AccountInfoKeeper) session.getAttribute(ACCOUNT_INFO);
        //set non updatable values
        Account account = accountInfoKeeper.getAccount();
        Account storedAccount = storedAccountInfoKeeper.getAccount();
        account.setEmail(storedAccount.getEmail());
        account.setRegistrationDate(storedAccount.getRegistrationDate());

//        updater.getValuesFromParams(accountInfoKeeper);

        if (isNull(session.getAttribute(PHOTO))) {
            session.setAttribute(PHOTO, storedAccount.getPhoto());
        }
        if (isNull(account.getPhoto())) {
            account.setPhoto((byte[]) session.getAttribute(PHOTO));
        } else {
            session.setAttribute(PHOTO, account.getPhoto());
        }

        req.setAttribute(ACCOUNT_INFO, accountInfoKeeper);
        boolean accepted = updater.isParamsAccepted();
        if (!accepted) {
            safeDoGet(req, resp);
        } else {
            update(updater, accountInfoKeeper, storedAccountInfoKeeper);
        }
    }

    private void update(AccountFieldsUpdater updater, AccountInfoKeeper accountInfoKeeper,
                        AccountInfoKeeper storedAccountInfoKeeper) throws ServletException, IOException {
        boolean updated;
//        try {
//            updated = accountInfoService.update(accountInfoKeeper, storedAccountInfoKeeper);
//        } catch (IncorrectDataException e) {
//            updater.handleInfoExceptions(e, this::safeDoGet);
//            return;
//        }
//        updater.acceptActionOrRetry(updated, this::safeDoGet);
    }
}
