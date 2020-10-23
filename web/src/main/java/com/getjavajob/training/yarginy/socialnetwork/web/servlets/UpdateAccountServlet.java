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

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Date;
import java.util.Base64;

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
        long id = (long) req.getSession().getAttribute(Attributes.USER_ID);

        AccountInfoDTO accountInfo = (AccountInfoDTO) req.getAttribute("accountInfo");

        if (isNull(accountInfo)) {
            accountInfo = accountInfoService.select(id);
        }
        initFields(req, accountInfo);

        Account account = accountInfo.getAccount();

        req.setAttribute("name", account.getName());
        req.setAttribute("surname", account.getSurname());
        req.setAttribute("patronymic", account.getPatronymic());
        req.setAttribute("sex", account.getSex());
        req.setAttribute("email", account.getEmail());
        req.setAttribute("additionalEmail", account.getAdditionalEmail());
        req.setAttribute("birthDate", Date.valueOf(account.getBirthDate()));
        req.setAttribute("icq", account.getId());
        req.setAttribute("skype", account.getSkype());
        req.setAttribute("country", account.getCountry());
        req.setAttribute("city", account.getCity());

        AccountPhoto accountPhoto = accountInfo.getAccountPhoto();
        if (!isNull(accountPhoto)) {
            String photo = Base64.getEncoder().encodeToString(accountPhoto.getPhoto());
            req.setAttribute(PHOTO_ATTRIBUTE, photo);
        }

        req.setAttribute(Attributes.TARGET, Pages.UPDATE_ACCOUNT);

        req.getRequestDispatcher(Jsps.UPDATE_ACCOUNT).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        paramsAccepted.set(true);
        AccountInfoDTO accountInfoDTO = new AccountInfoDTO();

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

    //todo move to UpdateHelper
    private void acceptActionOrRetry(HttpServletRequest req, HttpServletResponse resp, boolean updated, String successUrl,
                                     DoGetWrapper doGet) throws IOException,
            ServletException {
        if (updated) {
            HttpSession session = req.getSession();
            session.removeAttribute(Attributes.PHOTO_ATTRIBUTE);
            session.removeAttribute(Attributes.PRIVATE_PHONES_ATTRIBUTE);
            session.removeAttribute(Attributes.WORK_PHONES_ATTRIBUTE);
            redirect(req, resp, successUrl);
        } else {
            doGet.accept(req, resp);
        }
    }

    @FunctionalInterface
    interface DoGetWrapper {
        void accept(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException;
    }
}
