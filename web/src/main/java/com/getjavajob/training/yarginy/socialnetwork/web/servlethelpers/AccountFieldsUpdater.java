package com.getjavajob.training.yarginy.socialnetwork.web.servlethelpers;

import com.getjavajob.training.yarginy.socialnetwork.common.exceptions.IncorrectData;
import com.getjavajob.training.yarginy.socialnetwork.common.exceptions.IncorrectDataException;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.additionaldata.Sex;
import com.getjavajob.training.yarginy.socialnetwork.common.models.password.Password;
import com.getjavajob.training.yarginy.socialnetwork.common.models.phone.Phone;
import com.getjavajob.training.yarginy.socialnetwork.common.models.phone.additionaldata.PhoneType;
import com.getjavajob.training.yarginy.socialnetwork.common.utils.DataHandler;
import com.getjavajob.training.yarginy.socialnetwork.service.infokeepers.AccountInfoKeeper;
import com.getjavajob.training.yarginy.socialnetwork.web.servlets.accountpage.additionaldata.PhoneView;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static com.getjavajob.training.yarginy.socialnetwork.common.models.NullEntitiesFactory.getNullPassword;
import static com.getjavajob.training.yarginy.socialnetwork.common.models.account.additionaldata.Sex.FEMALE;
import static com.getjavajob.training.yarginy.socialnetwork.common.models.account.additionaldata.Sex.MALE;
import static com.getjavajob.training.yarginy.socialnetwork.common.models.phone.additionaldata.PhoneType.PRIVATE;
import static com.getjavajob.training.yarginy.socialnetwork.common.models.phone.additionaldata.PhoneType.WORK;
import static com.getjavajob.training.yarginy.socialnetwork.web.servlethelpers.RedirectHelper.redirect;
import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Attributes.*;
import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Pages.ACCOUNT_WALL;
import static java.util.Objects.isNull;

public final class AccountFieldsUpdater extends AbstractFieldsUpdater {
    private final DataHandler dataHandler = new DataHandler();
    //todo to delete
    HttpServletResponse resp;
    HttpSession session = new HttpSession() {
        @Override
        public long getCreationTime() {
            return 0;
        }

        @Override
        public String getId() {
            return null;
        }

        @Override
        public long getLastAccessedTime() {
            return 0;
        }

        @Override
        public ServletContext getServletContext() {
            return null;
        }

        @Override
        public int getMaxInactiveInterval() {
            return 0;
        }

        @Override
        public void setMaxInactiveInterval(int interval) {

        }

        @Override
        public HttpSessionContext getSessionContext() {
            return null;
        }

        @Override
        public Object getAttribute(String name) {
            return null;
        }

        @Override
        public Object getValue(String name) {
            return null;
        }

        @Override
        public Enumeration<String> getAttributeNames() {
            return null;
        }

        @Override
        public String[] getValueNames() {
            return new String[0];
        }

        @Override
        public void setAttribute(String name, Object value) {

        }

        @Override
        public void putValue(String name, Object value) {

        }

        @Override
        public void removeAttribute(String name) {

        }

        @Override
        public void removeValue(String name) {

        }

        @Override
        public void invalidate() {

        }

        @Override
        public boolean isNew() {
            return false;
        }
    };

    public AccountFieldsUpdater(HttpServletRequest req, HttpServletResponse resp) {
        super(req, USER_ID, ACCOUNT_WALL);
        this.resp = resp;
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
        setAttribute("photo", () -> dataHandler.getHtmlPhoto(account.getPhoto()));

        Collection<Phone> phones = accountInfo.getPhones();
//        HttpSession session = req.getSession();
        if (isNull(session.getAttribute(PRIVATE_PHONES))) {
            Collection<PhoneView> privatePhones = createPhoneViews(phones, "privatePhone", PRIVATE);
            session.setAttribute(PRIVATE_PHONES, privatePhones);
        }
        if (isNull(session.getAttribute(WORK_PHONES))) {
            Collection<PhoneView> workPhones = createPhoneViews(phones, "workPhone", WORK);
            session.setAttribute(WORK_PHONES, workPhones);
        }
    }

    private Collection<PhoneView> createPhoneViews(Collection<Phone> phones, String param, PhoneType type) {
        AtomicInteger i = new AtomicInteger(0);
        return phones.stream().filter(phone -> type.equals(phone.getType())).map(phone -> {
            i.getAndIncrement();
            return new PhoneView(param + i, phone.getNumber(), "");
        }).collect(Collectors.toList());
    }

    public void initSex() {
        req.setAttribute("male", MALE.toString());
        req.setAttribute("female", FEMALE.toString());
    }

    private Collection<Phone> getPhonesFromParams(String attribute, PhoneType type) {
        Collection<PhoneView> phoneViews = (Collection<PhoneView>) session.getAttribute(attribute);
        phoneViews.clear();
        Collection<Phone> phones = new ArrayList<>();
        AccountInfoKeeper accountInfo = (AccountInfoKeeper) session.getAttribute(ACCOUNT_INFO);
        Account account = accountInfo.getAccount();
        String prefix = type.equals(PRIVATE) ? "privatePhone" : "workPhone";
        int i = 0;
        String number = req.getParameter(prefix + i);
        while (!isNull(number)) {
            PhoneView phoneView = new PhoneView(prefix + i, number, "");
            phoneViews.add(phoneView);
            try {
                Phone phone = new Phone(number, account);
                phone.setType(type);
                phones.add(phone);
            } catch (IncorrectDataException e) {
                phoneView.setError(e.getType().getPropertyKey());
                paramsAccepted = false;
            }
            i++;
            number = req.getParameter(prefix + i);
        }
        return phones;
    }

    public Password getPassword(Account account) {
        Password password = new Password();
        password.setAccount(account);
        setStringFromParam(password::setPassword, "password");
        Password confirmPassword = new Password();
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
        setPhotoFromParam(account::setPhoto, dataHandler, "photo");

        Collection<Phone> privatePhones = getPhonesFromParams(PRIVATE_PHONES, PRIVATE);
        Collection<Phone> workPhones = getPhonesFromParams(WORK_PHONES, WORK);

        Collection<Phone> phones = accountInfoKeeper.getPhones();
        phones.clear();
        phones.addAll(privatePhones);
        phones.addAll(workPhones);
    }

    public void acceptActionOrRetry(boolean updated, DoGetWrapper doGet) throws IOException, ServletException {
        if (updated) {
            session.removeAttribute(ACCOUNT_INFO);
            session.removeAttribute(PRIVATE_PHONES);
            session.removeAttribute(WORK_PHONES);
            session.removeAttribute(PHOTO);
            redirect((HttpServletRequest) req, resp, updateSuccessUrl);
        } else {
            doGet.accept((HttpServletRequest) req, resp);
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
        doGet.accept((HttpServletRequest) req, resp);
    }
}
