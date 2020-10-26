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
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collection;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static com.getjavajob.training.yarginy.socialnetwork.common.models.NullEntitiesFactory.getNullPassword;
import static com.getjavajob.training.yarginy.socialnetwork.web.servlethelpers.RedirectHelper.redirect;
import static java.util.Objects.isNull;

public final class UpdateFieldHelper {
    private static final int PHONES_SIZE = 2;

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
                                         ThreadLocal<Boolean> paramsAccepted)
            throws IOException, ServletException {
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
                req.setAttribute(Attributes.ERR + param, e.getType().getPropertyKey());
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
            req.setAttribute(Attributes.ERR + param, e.getType().getPropertyKey());
            paramsAccepted.set(false);
            req.setAttribute(param, value);
        }
    }

    public static AccountInfoDTO accountInfoDTOInit(HttpServletRequest req, Supplier<AccountInfoDTO> accountInfoCreator) {
        HttpSession session = req.getSession();
        AccountInfoDTO accountInfo = (AccountInfoDTO) session.getAttribute(Attributes.ACCOUNT_INFO);
        if (isNull(accountInfo)) {
            accountInfo = accountInfoCreator.get();
            session.setAttribute(Attributes.ACCOUNT_INFO, accountInfo);
        }
        return accountInfo;
    }

    public static void initAccountAttributes(HttpServletRequest req, AccountInfoDTO accountInfo) {
        initFields(req, accountInfo);

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
        session.setAttribute(Attributes.PRIVATE_PHONES, initPhones(phones, "privatePhone", PhoneType.PRIVATE));
        session.setAttribute(Attributes.WORK_PHONES , initPhones(phones, "workPhone", PhoneType.WORK));
    }

    private static Collection<PhoneExchanger> initPhones(Collection<Phone> phones, String param, PhoneType type) {
        Collection<PhoneExchanger> phoneExchangers = phones.stream().filter(phone -> type.equals(phone.getType())).
                map(phone -> new PhoneExchanger("", phone.getNumber(), "")).collect(Collectors.toList());
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
     * @param req         {@link HttpServletRequest} to set attributes into
     * @param accountInfo {@link AccountInfoDTO} stores account's data such as {@link Collection} of phones that should
     *                    be set in params
     */
    public static void initFields(HttpServletRequest req, AccountInfoDTO accountInfo) {
        HttpSession session = req.getSession();

        req.setAttribute("male", Sex.MALE.toString());
        req.setAttribute("female", Sex.FEMALE.toString());

//        initPhonesFields(accountInfo, session, Attributes.PRIVATE_PHONES, PhoneType.PRIVATE);
//        initPhonesFields(accountInfo, session, Attributes.WORK_PHONES, PhoneType.WORK);
    }

    private static void initPhonesFields(AccountInfoDTO accountInfo, HttpSession session, String param, PhoneType type) {
        Collection<PhoneExchanger> phones;
        String paramName = param.substring(0, param.length() - 1);
        if (!isNull(accountInfo)) {
            phones = accountInfo.getPhones().stream().filter(phone -> type.equals(phone.getType())).
                    map(phone -> new PhoneExchanger("", phone.getNumber(), "")).collect(Collectors.toList());
            while (phones.size() < PHONES_SIZE) {
                phones.add(new PhoneExchanger(paramName + phones.size(), "", ""));
            }
        } else if (isNull(session.getAttribute(param))) {
            phones = new ArrayList<>();
            while (phones.size() < PHONES_SIZE) {
                phones.add(new PhoneExchanger(paramName + phones.size(), "", ""));
            }
        } else {
            phones = (Collection<PhoneExchanger>) session.getAttribute(param);
        }
        session.setAttribute(param, phones);
    }

    private static Collection<Phone> getTypedPhonesFromParam(HttpServletRequest req,
                                                             Collection<PhoneExchanger> phonesToGet, PhoneType type,
                                                             Account account, ThreadLocal<Boolean> paramsAccepted) {
        Collection<Phone> phones = new ArrayList<>();
        for (PhoneExchanger enteredPhone : phonesToGet) {
            enteredPhone.setError(null);
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

    public static Collection<Phone> getPhonesFromParams(HttpServletRequest req, Account account, ThreadLocal<Boolean>
            paramsAccepted) {
        Collection<Phone> phones = new ArrayList<>();
        Collection<PhoneExchanger> privatePhones = (Collection) req.getSession().getAttribute(
                Attributes.PRIVATE_PHONES);
        if (!isNull(privatePhones)) {
            phones.addAll(getTypedPhonesFromParam(req, privatePhones, PhoneType.PRIVATE, account,
                    paramsAccepted));
        }
        Collection<PhoneExchanger> workPhones = (Collection) req.getSession().getAttribute(
                Attributes.WORK_PHONES);
        if (!isNull(workPhones)) {
            phones.addAll(getTypedPhonesFromParam(req, workPhones, PhoneType.WORK, account, paramsAccepted));
        }
        return phones;
    }

    private static void getPhones(HttpServletRequest req, AccountInfoDTO accountInfo, PhoneType type, String attribute,
                                  ThreadLocal<Boolean> paramsAccepted) {
        Collection<Phone> phones = accountInfo.getPhones();
        Account account = accountInfo.getAccount();
        Collection<PhoneExchanger> exchangers = (Collection) req.getSession().getAttribute(attribute);
        for (PhoneExchanger exchanger : exchangers) {
            String number = req.getParameter(exchanger.getName());
            if (!isNull(number) && number.isEmpty()) {
                exchanger.setValue(number);
                try {
                    Phone phone = new PhoneImpl(number, account);
                    phone.setType(type);
                    phones.add(phone);
                } catch (IncorrectDataException e) {
                    paramsAccepted.set(false);
                    exchanger.setError(e.getType().getPropertyKey());
                }
            }
        }
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

        accountInfoDTO.getPhones().clear();
        getPhones(req, accountInfoDTO, PhoneType.PRIVATE, Attributes.PRIVATE_PHONES, paramsAccepted);
        getPhones(req, accountInfoDTO, PhoneType.WORK, Attributes.WORK_PHONES, paramsAccepted);

        AccountPhoto accountPhoto = accountInfoDTO.getAccountPhoto();
        setPhotoFromParam(req, accountPhoto, Attributes.PHOTO_ATTRIBUTE, paramsAccepted);
    }

    public static void acceptActionOrRetry(HttpServletRequest req, HttpServletResponse resp, boolean updated,
                                           String successUrl, DoGetWrapper doGet) throws IOException, ServletException {
        if (updated) {
            HttpSession session = req.getSession();
            session.removeAttribute(Attributes.ACCOUNT_INFO);
            session.removeAttribute(Attributes.PRIVATE_PHONES);
            session.removeAttribute(Attributes.WORK_PHONES);
            redirect(req, resp, successUrl);
        } else {
            doGet.accept(req, resp);
        }
    }

    @FunctionalInterface
    public interface DoGetWrapper {
        void accept(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException;
    }
}
