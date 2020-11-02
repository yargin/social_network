package com.getjavajob.training.yarginy.socialnetwork.web.servlethelpers;

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
import com.getjavajob.training.yarginy.socialnetwork.service.dto.AccountInfoDTO;
import com.getjavajob.training.yarginy.socialnetwork.web.servlets.additionaldata.PhoneExchanger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Date;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static com.getjavajob.training.yarginy.socialnetwork.common.models.NullEntitiesFactory.getNullPassword;
import static com.getjavajob.training.yarginy.socialnetwork.web.servlethelpers.RedirectHelper.redirect;
import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Attributes.*;
import static java.util.Objects.isNull;

public final class UpdateAccountFieldsHelper extends UpdateFieldsHelper {
    public AccountInfoDTO accountInfoDTOInit(HttpServletRequest req, Supplier<AccountInfoDTO> accountInfoCreator,
                                             boolean initStored) {
        HttpSession session = req.getSession();
        AccountInfoDTO accountInfo = (AccountInfoDTO) session.getAttribute(ACCOUNT_INFO);
        if (isNull(accountInfo)) {
            accountInfo = accountInfoCreator.get();
            session.setAttribute(ACCOUNT_INFO, accountInfoCreator.get());
            if (initStored) {
                session.setAttribute(STORED_ACCOUNT_INFO, accountInfoCreator.get());
            }
        }
        return accountInfo;
    }

    public void initAccountAttributes(HttpServletRequest req, AccountInfoDTO accountInfo) {
        initSex(req);

        Account account = accountInfo.getAccount();
        setAttribute(req, "name", account::getName);
        setAttribute(req, "surname", account::getSurname);
        setAttribute(req, "patronymic", account::getPatronymic);
        setAttribute(req, "sex", account::getSex);
        setAttribute(req, "email", account::getEmail);
        setAttribute(req, "additionalEmail", account::getAdditionalEmail);
        setAttribute(req, "birthDate", account::getBirthDate);
        setAttribute(req, "icq", account::getIcq);
        setAttribute(req, "skype", account::getSkype);
        setAttribute(req, "country", account::getCountry);
        setAttribute(req, "city", account::getCity);

        AccountPhoto accountPhoto = accountInfo.getAccountPhoto();
        if (!isNull(accountPhoto.getPhoto())) {
            String photo = Base64.getEncoder().encodeToString(accountPhoto.getPhoto());
            req.setAttribute("photo", photo);
        }

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
        phoneExchangers.add(new PhoneExchanger(param + phones.size(), "", ""));
        return phoneExchangers;
    }

    /**
     * initialises depended on configuration parameters such as sex-value & phone-fields
     *
     * @param req {@link HttpServletRequest} to set attributes into
     */
    public void initSex(HttpServletRequest req) {
        req.setAttribute("male", Sex.MALE.toString());
        req.setAttribute("female", Sex.FEMALE.toString());
    }

    private Collection<Phone> getPhonesFromParams(HttpServletRequest req, String attribute, PhoneType type,
                                                  ThreadLocal<Boolean> paramsAccepted) {
        AccountInfoDTO accountInfo = (AccountInfoDTO) req.getSession().getAttribute(ACCOUNT_INFO);
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
                    paramsAccepted.set(false);
                }
            }
        }
        return phones;
    }

    public Password getPassword(HttpServletRequest req, Account account, ThreadLocal<Boolean> paramsAccepted) {
        Password password = new PasswordImpl();
        password.setAccount(account);
        setStringFromParam(password::setPassword, "password", req, paramsAccepted);
        Password confirmPassword = new PasswordImpl();
        confirmPassword.setAccount(account);
        setStringFromParam(confirmPassword::setPassword, "confirmPassword", req, paramsAccepted);
        if (!Objects.equals(password, confirmPassword)) {
            req.setAttribute("passNotMatch", "error.passwordNotMatch");
            paramsAccepted.set(false);
            return getNullPassword();
        } else {
            return password;
        }
    }

    public void getValuesFromParams(HttpServletRequest req, AccountInfoDTO accountInfoDTO, ThreadLocal<Boolean>
            paramsAccepted) throws IOException, ServletException {
        Account account = accountInfoDTO.getAccount();
        setStringFromParam(account::setName, "name", req, paramsAccepted);
        setStringFromParam(account::setSurname, "surname", req, paramsAccepted);
        setStringFromParam(account::setPatronymic, "patronymic", req, paramsAccepted);
        setObjectFromParam(account::setSex, "sex", req, paramsAccepted, Sex::valueOf);
        setStringFromParam(account::setEmail, "email", req, paramsAccepted);
        setStringFromParam(account::setAdditionalEmail, "additionalEmail", req, paramsAccepted);
        setObjectFromParam(account::setBirthDate, "birthDate", req, paramsAccepted, Date::valueOf);
        setStringFromParam(account::setIcq, "icq", req, paramsAccepted);
        setStringFromParam(account::setSkype, "skype", req, paramsAccepted);
        setStringFromParam(account::setCountry, "country", req, paramsAccepted);
        setStringFromParam(account::setCity, "city", req, paramsAccepted);

        Collection<Phone> privatePhones = getPhonesFromParams(req, PRIVATE_PHONES, PhoneType.PRIVATE, paramsAccepted);
        Collection<Phone> workPhones = getPhonesFromParams(req, WORK_PHONES, PhoneType.WORK, paramsAccepted);

        AccountPhoto accountPhoto = accountInfoDTO.getAccountPhoto();
        setPhotoFromParam(accountPhoto::setPhoto, PHOTO_ATTRIBUTE, req, paramsAccepted);

        if (Boolean.TRUE.equals(paramsAccepted.get())) {
            Collection<Phone> phones = accountInfoDTO.getPhones();
            phones.clear();
            phones.addAll(privatePhones);
            phones.addAll(workPhones);
        }
    }

    public void acceptActionOrRetry(HttpServletRequest req, HttpServletResponse resp, boolean updated,
                                    String successUrl, DoGetWrapper doGet) throws IOException, ServletException {
        if (updated) {
            HttpSession session = req.getSession();
            session.removeAttribute(ACCOUNT_INFO);
            session.removeAttribute(PRIVATE_PHONES);
            session.removeAttribute(WORK_PHONES);
            redirect(req, resp, successUrl);
        } else {
            doGet.accept(req, resp);
        }
    }

    public void handleInfoExceptions(HttpServletRequest req, HttpServletResponse resp, IncorrectDataException e,
                                     DoGetWrapper doGet) throws ServletException, IOException {
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
