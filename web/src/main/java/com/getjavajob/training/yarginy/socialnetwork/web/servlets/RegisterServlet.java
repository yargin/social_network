package com.getjavajob.training.yarginy.socialnetwork.web.servlets;

import com.getjavajob.training.yarginy.socialnetwork.common.exceptions.IncorrectDataException;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.AccountImpl;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.additionaldata.Sex;
import com.getjavajob.training.yarginy.socialnetwork.common.models.password.Password;
import com.getjavajob.training.yarginy.socialnetwork.common.models.password.PasswordImpl;
import com.getjavajob.training.yarginy.socialnetwork.common.models.phone.Phone;
import com.getjavajob.training.yarginy.socialnetwork.common.models.phone.PhoneImpl;
import com.getjavajob.training.yarginy.socialnetwork.common.models.phone.additionaldata.PhoneType;
import com.getjavajob.training.yarginy.socialnetwork.service.AuthService;
import com.getjavajob.training.yarginy.socialnetwork.service.AuthServiceImpl;
import com.getjavajob.training.yarginy.socialnetwork.web.servlets.additionaldata.PhoneExchanger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.function.Consumer;

import static com.getjavajob.training.yarginy.socialnetwork.common.models.NullEntitiesFactory.getNullPassword;
import static java.util.Objects.isNull;

public class RegisterServlet extends HttpServlet {
    private static final String JSP = "/WEB-INF/jsps/registration.jsp";
    private static final String REG_SUCCESS_URL = "/mywall";
    private static final AuthService AUTH_SERVICE = new AuthServiceImpl();
    private final Account account = new AccountImpl();
    private final Collection<PhoneExchanger> privatePhones = new ArrayList<>();
    private final Collection<PhoneExchanger> workPhones = new ArrayList<>();
    private boolean paramsAccepted;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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

        if (!paramsAccepted) {
            doGet(req, resp);
        } else {
            AUTH_SERVICE.register(account, phones, password);
            resp.sendRedirect(req.getContextPath() + REG_SUCCESS_URL);
        }
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

    private void setStringParam(Consumer<String> consumer, String param, HttpServletRequest req) {
        try {
            String value = req.getParameter(param);
            consumer.accept(value);
            req.setAttribute(param, value);
        } catch (IncorrectDataException e) {
            req.setAttribute("err" + param, e.getType().getPropertyKey());
            paramsAccepted = false;
        }
    }

    private void setDateParam(Consumer<LocalDate> consumer, String param, HttpServletRequest req) {
        try {
            String enteredDate = req.getParameter(param);
            if (!enteredDate.trim().isEmpty()) {
                LocalDate date = LocalDate.parse(req.getParameter(param));
                consumer.accept(date);
                req.setAttribute(param, date);
            }
        } catch (IncorrectDataException e) {
            req.setAttribute("err" + param, e.getType().getPropertyKey());
            paramsAccepted = false;
        }
    }

    private void setSexParam(Consumer<Sex> consumer, String param, HttpServletRequest req) {
        try {
            String selectedSex = req.getParameter(param);
            if (!isNull(selectedSex)) {
                Sex sex = Sex.valueOf(selectedSex);
                consumer.accept(sex);
                req.setAttribute(param, sex);
            }
        } catch (IncorrectDataException e) {
            req.setAttribute("err" + param, e.getType().getPropertyKey());
            paramsAccepted = false;
        }
    }
}
