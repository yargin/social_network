package com.getjavajob.training.yarginy.socialnetwork.web.servlets;

import com.getjavajob.training.yarginy.socialnetwork.common.exceptions.IncorrectData;
import com.getjavajob.training.yarginy.socialnetwork.common.exceptions.IncorrectDataException;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.AccountImpl;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.additionaldata.Sex;
import com.getjavajob.training.yarginy.socialnetwork.common.models.accountphoto.AccountPhoto;
import com.getjavajob.training.yarginy.socialnetwork.common.models.accountphoto.AccountPhotoImpl;
import com.getjavajob.training.yarginy.socialnetwork.common.models.password.Password;
import com.getjavajob.training.yarginy.socialnetwork.common.models.password.PasswordImpl;
import com.getjavajob.training.yarginy.socialnetwork.common.models.phone.Phone;
import com.getjavajob.training.yarginy.socialnetwork.common.models.phone.PhoneImpl;
import com.getjavajob.training.yarginy.socialnetwork.common.models.phone.additionaldata.PhoneType;
import com.getjavajob.training.yarginy.socialnetwork.service.AuthService;
import com.getjavajob.training.yarginy.socialnetwork.service.AuthServiceImpl;
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
    private static final String JSP = "/WEB-INF/jsps/registration.jsp";
    private static final String REG_SUCCESS_URL = "/mywall";
    private static final AuthService AUTH_SERVICE = new AuthServiceImpl();
    //todo each thread should have it's own variable
    private Account account = new AccountImpl();
    private AccountPhoto accountPhoto = new AccountPhotoImpl();
    private Collection<PhoneExchanger> privatePhones = new ArrayList<>();
    private Collection<PhoneExchanger> workPhones = new ArrayList<>();
    private boolean paramsAccepted;

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

        if (privatePhones.isEmpty()) {
            privatePhones.add(new PhoneExchanger("privatePhone1", "", ""));
            privatePhones.add(new PhoneExchanger("privatePhone2", "", ""));
        }
        req.setAttribute("privatePhones", privatePhones);

        if (workPhones.isEmpty()) {
            workPhones.add(new PhoneExchanger("workPhone1", "", ""));
            workPhones.add(new PhoneExchanger("workPhone2", "", ""));
        }
        req.setAttribute("workPhones", workPhones);

        req.getRequestDispatcher(JSP).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        paramsAccepted = true;

        setStringParam(account::setName, "name", req);
        setStringParam(account::setSurname, "surname", req);
        setStringParam(account::setPatronymic, "patronymic", req);
        setSexParam(account::setSex, "sex", req);
        setStringParam(account::setEmail, "email", req);
        setStringParam(account::setAdditionalEmail, "additionalEmail", req);

        Password password = getPassword(req);

        setDateParam(account::setBirthDate, "birthDate", req);

        setStringParam(account::setIcq, "icq", req);
        setStringParam(account::setSkype, "skype", req);
        setStringParam(account::setCountry, "country", req);
        setStringParam(account::setCity, "city", req);

        Collection<Phone> phones = getPhones(req, privatePhones, PhoneType.PRIVATE);
        phones.addAll(getPhones(req, workPhones, PhoneType.WORK));

        setStreamParam(accountPhoto::setPhoto, "photo", req);

        if (!paramsAccepted) {
            doGet(req, resp);
        } else {
            register(phones, password, req, resp);
        }
    }

    private void register(Collection<Phone> phones, Password password, HttpServletRequest req,
                          HttpServletResponse resp) throws IOException, ServletException {
        try {
            AUTH_SERVICE.register(account, phones, password, accountPhoto);
        } catch (IncorrectDataException e) {
            if (e.getType() == IncorrectData.EMAIL_DUPLICATE) {
                req.setAttribute(EMAIL_DUPLICATE, e.getType().getPropertyKey());
            } else if (e.getType() == IncorrectData.PHONE_DUPLICATE) {
                req.setAttribute(PHONE_DUPLICATE, e.getType().getPropertyKey());
            }
            doGet(req, resp);
        }
        resp.sendRedirect(req.getContextPath() + REG_SUCCESS_URL);
    }

    private Collection<Phone> getPhones(HttpServletRequest req, Collection<PhoneExchanger> phonesToGet, PhoneType type) {
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
                    paramsAccepted = false;
                }
                enteredPhone.setValue(phoneToAdd);
            }
        }
        return phones;
    }

    private Password getPassword(HttpServletRequest req) {
        Password password = new PasswordImpl();
        password.setAccount(account);
        setStringParam(password::setPassword, "password", req);
        Password confirmPassword = new PasswordImpl();
        confirmPassword.setAccount(account);
        setStringParam(confirmPassword::setPassword, "confirmPassword", req);
        if (!Objects.equals(password, confirmPassword)) {
            req.setAttribute("passNotMatch", "error.passwordNotMatch");
            paramsAccepted = false;
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
            paramsAccepted = false;
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
                paramsAccepted = false;
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
            paramsAccepted = false;
        }
    }

    private void setStreamParam(Consumer<InputStream> setter, String param, HttpServletRequest req) throws IOException,
            ServletException {
        Part imagePart = req.getPart(param);
        try {
            try (InputStream inputStream = imagePart.getInputStream()) {
                setter.accept(inputStream);
                String base64Image = Base64.getEncoder().encodeToString(accountPhoto.getPhoto());
                req.setAttribute(param, base64Image);
            } catch (IOException e) {
                throw new IncorrectDataException(IncorrectData.UPLOADING_ERROR);
            }
        } catch (IncorrectDataException e) {
            req.setAttribute("err" + param, e.getType().getPropertyKey());
            paramsAccepted = false;
        }
    }
}
