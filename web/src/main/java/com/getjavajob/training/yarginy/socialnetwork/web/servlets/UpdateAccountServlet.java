package com.getjavajob.training.yarginy.socialnetwork.web.servlets;

import com.getjavajob.training.yarginy.socialnetwork.common.exceptions.IncorrectData;
import com.getjavajob.training.yarginy.socialnetwork.common.exceptions.IncorrectDataException;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.accountphoto.AccountPhoto;
import com.getjavajob.training.yarginy.socialnetwork.service.AccountInfoService;
import com.getjavajob.training.yarginy.socialnetwork.service.AccountInfoServiceImpl;
import com.getjavajob.training.yarginy.socialnetwork.service.dto.AccountInfoDTO;
import com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Attributes;
import com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Jsps;
import com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Pages;
import com.sun.deploy.net.HttpRequest;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Base64;
import java.util.function.Supplier;

import static com.getjavajob.training.yarginy.socialnetwork.web.servlethelpers.RedirectHelper.redirect;
import static com.getjavajob.training.yarginy.socialnetwork.web.servlethelpers.UpdateFieldHelper.*;
import static java.util.Objects.isNull;

public class UpdateAccountServlet extends HttpServlet {
    private static final String PHOTO_ATTRIBUTE = "photo";
    private static final String PHONE_DUPLICATE = "phoneDuplicate";
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
        paramsAccepted.set(true);

        AccountInfoDTO accountInfoDTO = (AccountInfoDTO) req.getSession().getAttribute(Attributes.ACCOUNT_INFO);
        if (isNull(accountInfoDTO)) {
            redirect(req, resp, Pages.LOGOUT);
            return;
        }

        getValuesFromParams(req, accountInfoDTO, paramsAccepted);

        if (!paramsAccepted.get()) {
            doGet(req, resp);
        } else {
            update(req, resp, accountInfoDTO);
        }
    }

    private void update(HttpServletRequest req, HttpServletResponse resp, AccountInfoDTO accountInfoDTO) throws
            ServletException, IOException {
        boolean updated;
        try {
            updated = accountInfoService.update(accountInfoDTO);
        } catch (IncorrectDataException e) {
             if (e.getType() == IncorrectData.PHONE_DUPLICATE) {
                req.setAttribute(PHONE_DUPLICATE, e.getType().getPropertyKey());
            }
            doGet(req, resp);
            return;
        }
        acceptActionOrRetry(req, resp, updated, UPDATE_SUCCESS_URL, (req1, resp1) -> doGet(req1, resp1));
    }
}
