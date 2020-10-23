package com.getjavajob.training.yarginy.socialnetwork.web.servlets;

import com.getjavajob.training.yarginy.socialnetwork.common.exceptions.IncorrectData;
import com.getjavajob.training.yarginy.socialnetwork.common.exceptions.IncorrectDataException;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.accountphoto.AccountPhoto;
import com.getjavajob.training.yarginy.socialnetwork.common.models.password.Password;
import com.getjavajob.training.yarginy.socialnetwork.common.models.phone.Phone;
import com.getjavajob.training.yarginy.socialnetwork.common.models.phone.additionaldata.PhoneType;
import com.getjavajob.training.yarginy.socialnetwork.service.AuthService;
import com.getjavajob.training.yarginy.socialnetwork.service.AuthServiceImpl;
import com.getjavajob.training.yarginy.socialnetwork.service.dto.AccountInfoDTO;
import com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Attributes;
import com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Jsps;
import com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Pages;
import com.getjavajob.training.yarginy.socialnetwork.web.servlets.additionaldata.PhoneExchanger;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.Collection;

import static com.getjavajob.training.yarginy.socialnetwork.web.servlethelpers.RedirectHelper.redirect;
import static com.getjavajob.training.yarginy.socialnetwork.web.servlethelpers.UpdateFieldHelper.*;
import static java.util.Objects.isNull;

public class RegisterServlet extends HttpServlet {
    private static final String EMAIL_DUPLICATE = "emailDuplicate";
    private static final String PHONE_DUPLICATE = "phoneDuplicate";
    private static final String REG_SUCCESS_URL = Pages.MY_WALL;
    private static final AuthService AUTH_SERVICE = new AuthServiceImpl();
    private final ThreadLocal<Boolean> paramsAccepted = new ThreadLocal<>();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        String userName = (String) session.getAttribute(Attributes.USER_NAME);
        if (!isNull(userName)) {
            resp.sendRedirect(req.getContextPath() + REG_SUCCESS_URL);
            return;
        }

        initFields(req, null);

        req.setAttribute(Attributes.TARGET, Pages.REGISTER);

        req.getRequestDispatcher(Jsps.REGISTER).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        paramsAccepted.set(true);
        AccountInfoDTO accountInfoDTO = new AccountInfoDTO();
//        Account account = accountInfoDTO.getAccount();
//
//        setStringFromParam(account::setName, "name", req, paramsAccepted);
//        setStringFromParam(account::setSurname, "surname", req, paramsAccepted);
//        setStringFromParam(account::setPatronymic, "patronymic", req, paramsAccepted);
//        setSexFromParam(account::setSex, "sex", req, paramsAccepted);
//        setStringFromParam(account::setEmail, "email", req, paramsAccepted);
//        setStringFromParam(account::setAdditionalEmail, "additionalEmail", req, paramsAccepted);
//
//        setDateFromParam(account::setBirthDate, "birthDate", req, paramsAccepted);
//
//        setStringFromParam(account::setIcq, "icq", req, paramsAccepted);
//        setStringFromParam(account::setSkype, "skype", req, paramsAccepted);
//        setStringFromParam(account::setCountry, "country", req, paramsAccepted);
//        setStringFromParam(account::setCity, "city", req, paramsAccepted);
//
//        Collection<Phone> phones = getPhonesFromParams(req, account, paramsAccepted);
//        accountInfoDTO.getPhones().addAll(phones);
//
//        AccountPhoto accountPhoto = accountInfoDTO.getAccountPhoto();
//        setPhotoFromParam(req, accountPhoto::setPhoto, Attributes.PHOTO_ATTRIBUTE, accountPhoto.getMaxSize(), paramsAccepted);

        getValuesFromParams(req, accountInfoDTO, paramsAccepted);

        Password password = getPassword(req, accountInfoDTO.getAccount(), paramsAccepted);

        if (!paramsAccepted.get()) {
            doGet(req, resp);
        } else {
            register(req, resp, accountInfoDTO, password);
        }
    }

    private void register(HttpServletRequest req, HttpServletResponse resp, AccountInfoDTO accountInfoDTO, Password
            password) throws IOException, ServletException {
        boolean registered;
        try {
            registered = AUTH_SERVICE.register(accountInfoDTO, password);
        } catch (IncorrectDataException e) {
            if (e.getType() == IncorrectData.EMAIL_DUPLICATE) {
                req.setAttribute(EMAIL_DUPLICATE, e.getType().getPropertyKey());
            } else if (e.getType() == IncorrectData.PHONE_DUPLICATE) {
                req.setAttribute(PHONE_DUPLICATE, e.getType().getPropertyKey());
            }
            doGet(req, resp);
            return;
        }
        if (registered) {
            HttpSession session = req.getSession();
            session.removeAttribute(Attributes.PHOTO_ATTRIBUTE);
            session.removeAttribute(Attributes.PRIVATE_PHONES_ATTRIBUTE);
            session.removeAttribute(Attributes.WORK_PHONES_ATTRIBUTE);
            redirect(req, resp, REG_SUCCESS_URL);
        } else {
            doGet(req, resp);
        }
    }
}
