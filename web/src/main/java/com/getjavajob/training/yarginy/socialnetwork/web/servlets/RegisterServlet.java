package com.getjavajob.training.yarginy.socialnetwork.web.servlets;

import com.getjavajob.training.yarginy.socialnetwork.common.exceptions.IncorrectData;
import com.getjavajob.training.yarginy.socialnetwork.common.exceptions.IncorrectDataException;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.additionaldata.Sex;
import com.getjavajob.training.yarginy.socialnetwork.common.models.accountphoto.AccountPhoto;
import com.getjavajob.training.yarginy.socialnetwork.common.models.password.Password;
import com.getjavajob.training.yarginy.socialnetwork.common.models.password.PasswordImpl;
import com.getjavajob.training.yarginy.socialnetwork.common.models.phone.Phone;
import com.getjavajob.training.yarginy.socialnetwork.common.models.phone.PhoneImpl;
import com.getjavajob.training.yarginy.socialnetwork.common.models.phone.additionaldata.PhoneType;
import com.getjavajob.training.yarginy.socialnetwork.service.AuthService;
import com.getjavajob.training.yarginy.socialnetwork.service.AuthServiceImpl;
import com.getjavajob.training.yarginy.socialnetwork.service.dto.AccountInfoDTO;
import com.getjavajob.training.yarginy.socialnetwork.web.servlets.additionaldata.PhoneExchanger;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collection;
import java.util.Objects;
import java.util.function.Consumer;

import static com.getjavajob.training.yarginy.socialnetwork.common.models.NullEntitiesFactory.getNullPassword;
import static java.util.Objects.isNull;

public class RegisterServlet extends HttpServlet {
    private static final String EMAIL_DUPLICATE = "emailDuplicate";
    private static final String PHONE_DUPLICATE = "phoneDuplicate";
    private static final String PHOTO_ATTRIBUTE = "photo";
    private static final String PRIVATE_PHONES_ATTRIBUTE = "privatePhones";
    private static final String WORK_PHONES_ATTRIBUTE = "workPhones";
    private static final String JSP = "/WEB-INF/jsps/registration.jsp";
    private static final String REG_SUCCESS_URL = "/mywall";
    private static final AuthService AUTH_SERVICE = new AuthServiceImpl();
    private final ThreadLocal<Boolean> paramsAccepted = new ThreadLocal<>();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(true);

        String userName = (String) session.getAttribute("userName");
        if (!isNull(userName)) {
            resp.sendRedirect(req.getContextPath() + REG_SUCCESS_URL);
            return;
        }

        req.setAttribute("male", Sex.MALE.toString());
        req.setAttribute("female", Sex.FEMALE.toString());

        Collection<PhoneExchanger> privatePhones;
        if (isNull(session.getAttribute(PRIVATE_PHONES_ATTRIBUTE))) {
            privatePhones = new ArrayList<>();
            privatePhones.add(new PhoneExchanger("privatePhone1", "", ""));
            privatePhones.add(new PhoneExchanger("privatePhone2", "", ""));
        } else {
            privatePhones = (Collection<PhoneExchanger>) session.getAttribute(PRIVATE_PHONES_ATTRIBUTE);
        }
        session.setAttribute(PRIVATE_PHONES_ATTRIBUTE, privatePhones);

        Collection<PhoneExchanger> workPhones;
        if (isNull(session.getAttribute(WORK_PHONES_ATTRIBUTE))) {
            workPhones = new ArrayList<>();
            workPhones.add(new PhoneExchanger("workPhone1", "", ""));
            workPhones.add(new PhoneExchanger("workPhone2", "", ""));
            session.setAttribute(WORK_PHONES_ATTRIBUTE, workPhones);
        } else {
            workPhones = (Collection<PhoneExchanger>) session.getAttribute(WORK_PHONES_ATTRIBUTE);
        }
        session.setAttribute(WORK_PHONES_ATTRIBUTE, workPhones);

        req.getRequestDispatcher(JSP).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        paramsAccepted.set(true);
        AccountInfoDTO accountInfoDTO = new AccountInfoDTO();
        Account account = accountInfoDTO.getAccount();

        setStringParam(account::setName, "name", req);
        setStringParam(account::setSurname, "surname", req);
        setStringParam(account::setPatronymic, "patronymic", req);
        setSexParam(account::setSex, "sex", req);
        setStringParam(account::setEmail, "email", req);
        setStringParam(account::setAdditionalEmail, "additionalEmail", req);

        Password password = getPassword(req, account);

        setDateParam(account::setBirthDate, "birthDate", req);

        setStringParam(account::setIcq, "icq", req);
        setStringParam(account::setSkype, "skype", req);
        setStringParam(account::setCountry, "country", req);
        setStringParam(account::setCity, "city", req);

        Collection<PhoneExchanger> privatePhones = (Collection<PhoneExchanger>) req.getSession().getAttribute(
                PRIVATE_PHONES_ATTRIBUTE);
        Collection<Phone> phones = getPhones(req, privatePhones, PhoneType.PRIVATE, account);
        Collection<PhoneExchanger> workPhones = (Collection<PhoneExchanger>) req.getSession().getAttribute(
                WORK_PHONES_ATTRIBUTE);
        phones.addAll(getPhones(req, workPhones, PhoneType.WORK, account));

        AccountPhoto accountPhoto = accountInfoDTO.getAccountPhoto();
        setPhotoParam(req, accountPhoto);

        if (!paramsAccepted.get()) {
            doGet(req, resp);
        } else {
            register(req, resp, accountInfoDTO, password);
        }
    }

    private void register(HttpServletRequest req, HttpServletResponse resp, AccountInfoDTO accountInfoDTO, Password
            password) throws IOException, ServletException {
        boolean registered = false;
        try {
            registered = AUTH_SERVICE.register(accountInfoDTO, password);
        } catch (IncorrectDataException e) {
            if (e.getType() == IncorrectData.EMAIL_DUPLICATE) {
                req.setAttribute(EMAIL_DUPLICATE, e.getType().getPropertyKey());
            } else if (e.getType() == IncorrectData.PHONE_DUPLICATE) {
                req.setAttribute(PHONE_DUPLICATE, e.getType().getPropertyKey());
            }
            doGet(req, resp);
        }
        if (registered) {
            HttpSession session = req.getSession();
            session.removeAttribute(PHOTO_ATTRIBUTE);
            session.removeAttribute(PRIVATE_PHONES_ATTRIBUTE);
            session.removeAttribute(WORK_PHONES_ATTRIBUTE);
            resp.sendRedirect(req.getContextPath() + REG_SUCCESS_URL);
        } else {
            doGet(req, resp);
        }
    }

    private Collection<Phone> getPhones(HttpServletRequest req, Collection<PhoneExchanger> phonesToGet, PhoneType type,
                                        Account account) {
        Collection<Phone> phones = new ArrayList<>();
        for (PhoneExchanger enteredPhone : phonesToGet) {
            String phoneToAdd = req.getParameter(enteredPhone.getName());
            if (!isNull(phoneToAdd) && !phoneToAdd.trim().isEmpty()) {
                try {
                    Phone phone = new PhoneImpl(phoneToAdd, account);
                    phone.setType(type);
                    phones.add(phone);
                } catch (IncorrectDataException e) {
                    enteredPhone.setError(e.getType().getPropertyKey());
                    paramsAccepted.set(false);
                }
                enteredPhone.setValue(phoneToAdd);
            }
        }
        return phones;
    }

    private Password getPassword(HttpServletRequest req, Account account) {
        Password password = new PasswordImpl();
        password.setAccount(account);
        setStringParam(password::setPassword, "password", req);
        Password confirmPassword = new PasswordImpl();
        confirmPassword.setAccount(account);
        setStringParam(confirmPassword::setPassword, "confirmPassword", req);
        if (!Objects.equals(password, confirmPassword)) {
            req.setAttribute("passNotMatch", "error.passwordNotMatch");
            paramsAccepted.set(false);
            return getNullPassword();
        } else {
            return password;
        }
    }

    private void setStringParam(Consumer<String> setter, String param, HttpServletRequest req) {
        String value = req.getParameter(param);
        try {
            setter.accept(value);
        } catch (IncorrectDataException e) {
            req.setAttribute("err" + param, e.getType().getPropertyKey());
            paramsAccepted.set(false);
        }
        req.setAttribute(param, value);
    }

    private void setDateParam(Consumer<LocalDate> setter, String param, HttpServletRequest req) {
        String enteredDate = req.getParameter(param);
        if (!isNull(enteredDate) & !enteredDate.trim().isEmpty()) {
            LocalDate date = LocalDate.parse(enteredDate);
            try {
                setter.accept(date);
            } catch (IncorrectDataException e) {
                req.setAttribute("err" + param, e.getType().getPropertyKey());
                paramsAccepted.set(false);
            }
            req.setAttribute(param, Date.valueOf(date));
        }
    }

    private void setSexParam(Consumer<Sex> setter, String param, HttpServletRequest req) {
        try {
            String selectedSex = req.getParameter(param);
            if (!isNull(selectedSex)) {
                Sex sex = Sex.valueOf(selectedSex);
                setter.accept(sex);
                req.setAttribute(param, sex);
            }
        } catch (IncorrectDataException e) {
            req.setAttribute("err" + param, e.getType().getPropertyKey());
            paramsAccepted.set(false);
        }
    }

    private void setPhotoParam(HttpServletRequest req, AccountPhoto accountPhoto) throws IOException, ServletException {
        Part imagePart = req.getPart(PHOTO_ATTRIBUTE);
        HttpSession session = req.getSession();
        String previousImage = (String) session.getAttribute(PHOTO_ATTRIBUTE);
        if (!isNull(imagePart)) {
            try {
                try (InputStream inputStream = imagePart.getInputStream()) {
                    accountPhoto.setPhoto(inputStream);
                    String enteredPhoto = Base64.getEncoder().encodeToString(accountPhoto.getPhoto());
                    if (!isNull(enteredPhoto) && !enteredPhoto.isEmpty()) {
                        previousImage = Base64.getEncoder().encodeToString(accountPhoto.getPhoto());
                    }
                } catch (IOException e) {
                    throw new IncorrectDataException(IncorrectData.UPLOADING_ERROR);
                }
            } catch (IncorrectDataException e) {
                req.setAttribute("err" + PHOTO_ATTRIBUTE, e.getType().getPropertyKey());
                paramsAccepted.set(false);
            }
        }
        if (!isNull(previousImage) && !previousImage.isEmpty()) {
            byte[] photo = Base64.getDecoder().decode(previousImage);
            accountPhoto.setPhoto(photo);
            session.setAttribute(PHOTO_ATTRIBUTE, previousImage);
        }
    }
}
