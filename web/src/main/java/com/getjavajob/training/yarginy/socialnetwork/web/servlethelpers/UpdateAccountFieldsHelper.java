package com.getjavajob.training.yarginy.socialnetwork.web.servlethelpers;

import com.getjavajob.training.yarginy.socialnetwork.common.exceptions.IncorrectData;
import com.getjavajob.training.yarginy.socialnetwork.common.exceptions.IncorrectDataException;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.additionaldata.Sex;
import com.getjavajob.training.yarginy.socialnetwork.common.models.password.Password;
import com.getjavajob.training.yarginy.socialnetwork.common.models.password.PasswordImpl;
import com.getjavajob.training.yarginy.socialnetwork.common.models.phone.Phone;
import com.getjavajob.training.yarginy.socialnetwork.common.models.phone.PhoneImpl;
import com.getjavajob.training.yarginy.socialnetwork.common.models.phone.additionaldata.PhoneType;
import com.getjavajob.training.yarginy.socialnetwork.service.datakeepers.AccountInfoKeeper;
import com.getjavajob.training.yarginy.socialnetwork.web.servlets.accountpage.additionaldata.PhoneExchanger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static com.getjavajob.training.yarginy.socialnetwork.common.models.NullEntitiesFactory.getNullPassword;
import static com.getjavajob.training.yarginy.socialnetwork.web.servlethelpers.RedirectHelper.redirect;
import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Attributes.*;
import static java.util.Objects.isNull;

public final class UpdateAccountFieldsHelper extends UpdateFieldsHelper {
    public UpdateAccountFieldsHelper(HttpServletRequest req, HttpServletResponse resp, String param, String successUrl) {
        super(req, resp, param, successUrl);
    }

    public AccountInfoKeeper getOrCreateAccountInfo(Supplier<AccountInfoKeeper> accountInfoCreator) {
        AccountInfoKeeper accountInfo = (AccountInfoKeeper) req.getAttribute(ACCOUNT_INFO);
        if (isNull(accountInfo)) {
            accountInfo = accountInfoCreator.get();
            req.setAttribute(ACCOUNT_INFO, accountInfoCreator.get());
        }
        return accountInfo;
    }

    public void initAccountAttributes(AccountInfoKeeper accountInfo) {
        initSex();

        Account account = accountInfo.getAccount();
        setAttribute("name", account::getName);
        setAttribute("surname", account::getSurname);
        setAttribute("patronymic", account::getPatronymic);
        setAttribute("sex", account::getSex);
        setAttribute("email", account::getEmail);
        setAttribute("additionalEmail", account::getAdditionalEmail);
        setAttribute("birthDate", account::getBirthDate);
        setAttribute("icq", account::getIcq);
        setAttribute("skype", account::getSkype);
        setAttribute("country", account::getCountry);
        setAttribute("city", account::getCity);
        setAttribute("photo", account::getHtmlPhoto);

        Collection<Phone> phones = accountInfo.getPhones();
        HttpSession session = req.getSession();
        if (isNull(session.getAttribute(PRIVATE_PHONES))) {
            Collection<PhoneExchanger> privatePhones = createPhoneExchangers(phones, "privatePhone", PhoneType.PRIVATE);
            session.setAttribute(PRIVATE_PHONES, privatePhones);
        }
        if (isNull(session.getAttribute(WORK_PHONES))) {
            Collection<PhoneExchanger> workPhones = createPhoneExchangers(phones, "workPhone", PhoneType.WORK);
            session.setAttribute(WORK_PHONES, workPhones);
        }
    }

    private Collection<PhoneExchanger> createPhoneExchangers(Collection<Phone> phones, String param,
                                                             PhoneType type) {
        AtomicInteger i = new AtomicInteger(0);
        Collection<PhoneExchanger> phoneExchangers = phones.stream().filter(phone -> type.equals(phone.getType())).
                map(phone -> {
                    i.getAndIncrement();
                    return new PhoneExchanger(param + i, phone.getNumber(), "");
                }).collect(Collectors.toList());
        phoneExchangers.add(new PhoneExchanger(param + i.incrementAndGet(), "", ""));
        return phoneExchangers;
    }

    public void initSex() {
        req.setAttribute("male", Sex.MALE.toString());
        req.setAttribute("female", Sex.FEMALE.toString());
    }

    private Collection<Phone> getPhonesFromParams(String attribute, PhoneType type) {
        AccountInfoKeeper accountInfo = (AccountInfoKeeper) req.getSession().getAttribute(ACCOUNT_INFO);
        Account account = accountInfo.getAccount();
        Collection<PhoneExchanger> phoneExchangers = (Collection<PhoneExchanger>) req.getSession().getAttribute(attribute);
        Collection<Phone> phones = new ArrayList<>();
        Iterator<PhoneExchanger> iterator = phoneExchangers.iterator();
        while (iterator.hasNext()) {
            PhoneExchanger phoneExchanger = iterator.next();
            String phoneValue = req.getParameter(phoneExchanger.getParamName());
            if (isNull(phoneValue) || phoneValue.isEmpty()) {
                if (iterator.hasNext()) {
                    iterator.remove();
                }
            } else {
                phoneExchanger.setValue(phoneValue);
                try {
                    Phone phone = new PhoneImpl(phoneValue, account);
                    phone.setType(type);
                    phones.add(phone);
                } catch (IncorrectDataException e) {
                    phoneExchanger.setError(e.getType().getPropertyKey());
                    paramsAccepted = false;
                }
            }
        }
        return phones;
    }

    public Password getPassword(Account account) {
        Password password = new PasswordImpl();
        password.setAccount(account);
        setStringFromParam(password::setPassword, "password");
        Password confirmPassword = new PasswordImpl();
        confirmPassword.setAccount(account);
        setStringFromParam(confirmPassword::setPassword, "confirmPassword");
        if (!Objects.equals(password, confirmPassword)) {
            req.setAttribute("passNotMatch", "error.passwordNotMatch");
            paramsAccepted = false;
            return getNullPassword();
        } else {
            return password;
        }
    }

    public void getValuesFromParams(AccountInfoKeeper accountInfoKeeper) throws IOException, ServletException {
        Account account = accountInfoKeeper.getAccount();
        setStringFromParam(account::setName, "name");
        setStringFromParam(account::setSurname, "surname");
        setStringFromParam(account::setPatronymic, "patronymic");
        setObjectFromParam(account::setSex, "sex", Sex::valueOf);
        setStringFromParam(account::setEmail, "email");
        setStringFromParam(account::setAdditionalEmail, "additionalEmail");
        setObjectFromParam(account::setBirthDate, "birthDate", Date::valueOf);
        setStringFromParam(account::setIcq, "icq");
        setStringFromParam(account::setSkype, "skype");
        setStringFromParam(account::setCountry, "country");
        setStringFromParam(account::setCity, "city");
        setPhotoFromParam(account::setPhoto, "photo");

        Collection<Phone> privatePhones = getPhonesFromParams(PRIVATE_PHONES, PhoneType.PRIVATE);
        Collection<Phone> workPhones = getPhonesFromParams(WORK_PHONES, PhoneType.WORK);

        Collection<Phone> phones = accountInfoKeeper.getPhones();
        phones.clear();
        phones.addAll(privatePhones);
        phones.addAll(workPhones);
    }

    public void acceptActionOrRetry(boolean updated, DoGetWrapper doGet) throws IOException, ServletException {
        if (updated) {
            HttpSession session = req.getSession();
            session.removeAttribute(ACCOUNT_INFO);
            session.removeAttribute(PRIVATE_PHONES);
            session.removeAttribute(WORK_PHONES);
            session.removeAttribute(PHOTO);
            redirect(req, resp, updateSuccessUrl);
        } else {
            doGet.accept(req, resp);
        }
    }

    public void handleInfoExceptions(IncorrectDataException e, DoGetWrapper doGet) throws ServletException, IOException {
        if (e.getType() == IncorrectData.EMAIL_DUPLICATE) {
            req.setAttribute(EMAIL_DUPLICATE, e.getType().getPropertyKey());
        }
        if (e.getType() == IncorrectData.PHONE_DUPLICATE) {
            req.setAttribute(PHONE_DUPLICATE, e.getType().getPropertyKey());
        }
        if (e.getType() == IncorrectData.UPLOADING_ERROR) {
            req.setAttribute(UPLOAD_ERROR, e.getType().getPropertyKey());
        }
        doGet.accept(req, resp);
    }
}
