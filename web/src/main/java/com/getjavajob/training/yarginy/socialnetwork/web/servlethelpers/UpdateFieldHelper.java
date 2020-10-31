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
import com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Attributes;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static com.getjavajob.training.yarginy.socialnetwork.common.models.NullEntitiesFactory.getNullPassword;
import static com.getjavajob.training.yarginy.socialnetwork.web.servlethelpers.RedirectHelper.redirect;
import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Attributes.*;
import static java.util.Objects.isNull;

public final class UpdateFieldHelper {
    private UpdateFieldHelper() {
    }

    /**
     * gets class E object from request parameter & sets it into model object
     *
     * @param setter           puts received value into model-object
     * @param param            request parameter's name to receive value
     * @param req              request with parameter
     * @param paramsAccepted   flag that value was successfully set
     * @param fromParamToValue transforms string parameter into applicable object
     * @param <E>              value's type
     */
    public static <E> void setObjectFromParam(Consumer<E> setter, String param,
                                              HttpServletRequest req, ThreadLocal<Boolean> paramsAccepted,
                                              Function<String, E> fromParamToValue) {
        String enteredValue = req.getParameter(param);
        if (!isNull(enteredValue)) {
            E value = null;
            if (!isNull(fromParamToValue)) {
                value = fromParamToValue.apply(enteredValue);
            }
            setFromParam(setter, param, req, paramsAccepted, value);
        }
    }

    public static void setPhotoFromParam(HttpServletRequest req, AccountPhoto accountPhoto, String param,
                                         ThreadLocal<Boolean> paramsAccepted) throws IOException, ServletException {
        Part imagePart = req.getPart(param);
        if (!isNull(imagePart)) {
            try (InputStream inputStream = imagePart.getInputStream()) {
                if (inputStream.available() > 0) {
                    accountPhoto.setPhoto(inputStream);
                }
            } catch (IOException e) {
                throw new IncorrectDataException(IncorrectData.UPLOADING_ERROR);
            } catch (IncorrectDataException e) {
                paramsAccepted.set(false);
                req.setAttribute(ERR + param, e.getType().getPropertyKey());
            }
        }
    }

    private static void setStringFromParam(Consumer<String> setter, String param, HttpServletRequest req,
                                           ThreadLocal<Boolean> paramsAccepted) {
        String enteredValue = req.getParameter(param);
        if (!isNull(enteredValue)) {
            setFromParam(setter, param, req, paramsAccepted, enteredValue);
        }
    }

    private static <E> void setFromParam(Consumer<E> setter, String param, HttpServletRequest req,
                                         ThreadLocal<Boolean> paramsAccepted, E value) {
        try {
            setter.accept(value);
        } catch (IncorrectDataException e) {
            req.setAttribute(ERR + param, e.getType().getPropertyKey());
            paramsAccepted.set(false);
            req.setAttribute(param, value);
        }
    }

    public static AccountInfoDTO accountInfoDTOInit(HttpServletRequest req, Supplier<AccountInfoDTO> accountInfoCreator) {
        HttpSession session = req.getSession();
        AccountInfoDTO accountInfo = (AccountInfoDTO) session.getAttribute(ACCOUNT_INFO);
        if (isNull(accountInfo)) {
            accountInfo = accountInfoCreator.get();
            session.setAttribute(ACCOUNT_INFO, accountInfoCreator.get());
            session.setAttribute(STORED_ACCOUNT_INFO, accountInfoCreator.get());
        }
        return accountInfo;
    }

    public static void initAccountAttributes(HttpServletRequest req, AccountInfoDTO accountInfo) {
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

    private static Collection<PhoneExchanger> createPhoneExchangers(Collection<Phone> phones, String param,
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

    private static <E> void setAttribute(HttpServletRequest req, String param, Supplier<E> getter) {
        if (isNull(req.getAttribute(param)) && !isNull(getter.get())) {
            req.setAttribute(param, getter.get());
        }
    }

    /**
     * initialises depended on configuration parameters such as sex-value & phone-fields
     *
     * @param req {@link HttpServletRequest} to set attributes into
     */
    public static void initSex(HttpServletRequest req) {
        req.setAttribute("male", Sex.MALE.toString());
        req.setAttribute("female", Sex.FEMALE.toString());
    }

    private static Collection<Phone> getPhonesFromParams(HttpServletRequest req, String attribute, PhoneType type,
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

    public static Password getPassword(HttpServletRequest req, Account account, ThreadLocal<Boolean> paramsAccepted) {
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

    public static void getValuesFromParams(HttpServletRequest req, AccountInfoDTO accountInfoDTO, ThreadLocal<Boolean>
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
        setPhotoFromParam(req, accountPhoto, PHOTO_ATTRIBUTE, paramsAccepted);

        if (Boolean.TRUE.equals(paramsAccepted.get())) {
            Collection<Phone> phones = accountInfoDTO.getPhones();
            phones.clear();
            phones.addAll(privatePhones);
            phones.addAll(workPhones);
        }
    }

    public static void acceptActionOrRetry(HttpServletRequest req, HttpServletResponse resp, boolean updated,
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

    @FunctionalInterface
    public interface DoGetWrapper {
        void accept(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException;
    }

    public static void handleInfoExceptions(HttpServletRequest req, HttpServletResponse resp, IncorrectDataException e,
                                            DoGetWrapper doGet) throws ServletException, IOException {
        if (e.getType() == IncorrectData.EMAIL_DUPLICATE) {
            req.setAttribute(EMAIL_DUPLICATE, e.getType().getPropertyKey());
        }
        if (e.getType() == IncorrectData.PHONE_DUPLICATE) {
            req.setAttribute(PHONE_DUPLICATE, e.getType().getPropertyKey());
        }
        if (e.getType() == IncorrectData.UPLOADING_ERROR) {
            req.setAttribute(Attributes.PHONE_DUPLICATE, e.getType().getPropertyKey());
        }
        doGet.accept(req, resp);
    }
}
