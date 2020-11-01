package com.getjavajob.training.yarginy.socialnetwork.web.servlets;

import com.getjavajob.training.yarginy.socialnetwork.common.exceptions.IncorrectDataException;
import com.getjavajob.training.yarginy.socialnetwork.service.AccountInfoService;
import com.getjavajob.training.yarginy.socialnetwork.service.AccountInfoServiceImpl;
import com.getjavajob.training.yarginy.socialnetwork.service.dto.AccountInfoDTO;
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
import static com.getjavajob.training.yarginy.socialnetwork.web.servlethelpers.UpdateFieldHelper.*;
import static java.util.Objects.isNull;

public class UpdateAccountServlet extends HttpServlet {
    private static final String UPDATE_SUCCESS_URL = Pages.MY_WALL;
    private final AccountInfoService accountInfoService = new AccountInfoServiceImpl();
    private final ThreadLocal<Boolean> paramsAccepted = new ThreadLocal<>();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        Long id = (Long) session.getAttribute(Attributes.USER_ID);
        if (isNull(id)) {
            resp.sendRedirect(req.getContextPath() + UPDATE_SUCCESS_URL);
            return;
        }
        AccountInfoDTO accountInfoDTO = accountInfoDTOInit(req, () -> accountInfoService.select(id));
        initAccountAttributes(req, accountInfoDTO);

        req.setAttribute(Attributes.TARGET, Pages.UPDATE_ACCOUNT);

        req.getRequestDispatcher(Jsps.UPDATE_ACCOUNT).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if ("cancel".equals(req.getParameter("save"))) {
            acceptActionOrRetry(req, resp, true, UPDATE_SUCCESS_URL, null);
            return;
        }

        paramsAccepted.set(true);
        HttpSession session = req.getSession();

        AccountInfoDTO accountInfoDTO = (AccountInfoDTO) session.getAttribute(Attributes.ACCOUNT_INFO);
        AccountInfoDTO storedAccountInfoDTO = (AccountInfoDTO) session.getAttribute(Attributes.STORED_ACCOUNT_INFO);
        if (isNull(accountInfoDTO) || isNull(storedAccountInfoDTO)) {
            redirect(req, resp, Pages.LOGOUT);
            return;
        }

        getValuesFromParams(req, accountInfoDTO, paramsAccepted);

        boolean accepted = paramsAccepted.get();
        if (!accepted) {
            doGet(req, resp);
        } else {
            update(req, resp, accountInfoDTO, storedAccountInfoDTO);
            paramsAccepted.remove();
        }
    }

    private void update(HttpServletRequest req, HttpServletResponse resp, AccountInfoDTO accountInfoDTO,
                        AccountInfoDTO storedAccountInfoDTO) throws
            ServletException, IOException {
        boolean updated;
        try {
            updated = accountInfoService.update(accountInfoDTO, storedAccountInfoDTO);
        } catch (IncorrectDataException e) {
            handleInfoExceptions(req, resp, e, this::doGet);
            return;
        }
        acceptActionOrRetry(req, resp, updated, UPDATE_SUCCESS_URL, this::doGet);
    }
}
