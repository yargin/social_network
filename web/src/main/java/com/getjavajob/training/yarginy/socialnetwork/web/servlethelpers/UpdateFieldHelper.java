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
import java.time.LocalDate;
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

    public static void setStringFromParam(Consumer<String> setter, Supplier<String> getter, String param,
                                          HttpServletRequest req, ThreadLocal<Boolean> paramsAccepted) {
        String value = req.getParameter(param);
        if (isNull(value)){
            value = getter.get();
        } else {
            try {
                setter.accept(value);
            } catch (IncorrectDataException e) {
                req.setAttribute(Attributes.ERR + param, e.getType().getPropertyKey());
                paramsAccepted.set(false);
            }
        }
        req.setAttribute(param, value);
    }

    public static void setDateFromParam(Consumer<LocalDate> setter, Supplier<LocalDate> getter, String param,
                                        HttpServletRequest req, ThreadLocal<Boolean> paramsAccepted) {
        String enteredDate = req.getParameter(param);
        LocalDate date = null;
        if (isNull(enteredDate)){
            date = getter.get();
        } else {
            if (!enteredDate.trim().isEmpty()) {
                date = LocalDate.parse(enteredDate);
                try {
                    setter.accept(date);
                } catch (IncorrectDataException e) {
                    req.setAttribute(Attributes.ERR + param, e.getType().getPropertyKey());
                    paramsAccepted.set(false);
                }
            }
        }
        if (!isNull(date)) {
            req.setAttribute(param, Date.valueOf(date));
        }
    }

//    public static <E> void setDateFromParamTrans(Consumer<E> setter, Supplier<E> getter, String param,
//                                                 HttpServletRequest req,  ThreadLocal<Boolean> paramsAccepted,
//                                                 Function<String, E> fromParamToValue) {
//        String enteredDate = req.getParameter(param);
//        E value = null;
//        if (isNull(enteredDate)){
//            value = getter.get();
//        } else {
//            if (!enteredDate.trim().isEmpty()) {
//                value = fromParamToValue.apply(enteredDate);
//                try {
//                    setter.accept(value);
//                } catch (IncorrectDataException e) {
//                    req.setAttribute(Attributes.ERR + param, e.getType().getPropertyKey());
//                    paramsAccepted.set(false);
//                }
//            }
//        }
//        if (!isNull(value)) {
//            req.setAttribute(param, value);
//        }
//    }

    public static void setSexFromParam(Consumer<Sex> setter, Supplier<Sex> getter, String param, HttpServletRequest req,
                                         ThreadLocal<Boolean> paramsAccepted) {
        try {
            String selectedSex = req.getParameter(param);
            Sex sex;
            if (!isNull(selectedSex)) {
                sex = Sex.valueOf(selectedSex);
                setter.accept(sex);
            } else {
                sex = getter.get();
            }
            req.setAttribute(param, sex);
        } catch (IncorrectDataException e) {
            req.setAttribute(Attributes.ERR + param, e.getType().getPropertyKey());
            paramsAccepted.set(false);
        }
    }

    public static void setPhotoFromParam(HttpServletRequest req, Consumer<byte[]> setter,  String param,
                                         int maxSize, ThreadLocal<Boolean> paramsAccepted)
            throws IOException, ServletException {
        Part imagePart = req.getPart(param);
        HttpSession session = req.getSession();
        String previousImage = (String) session.getAttribute(param);
        if (!isNull(imagePart)) {
            try {
                try (InputStream inputStream = imagePart.getInputStream()) {
                    int size = inputStream.available();
                    if (size > maxSize) {
                        throw new IncorrectDataException(IncorrectData.FILE_TOO_LARGE);
                    }
                    byte[] photo = new byte[inputStream.available()];
                    inputStream.read(photo);
                    setter.accept(photo);
                    String specifiedPhoto = Base64.getEncoder().encodeToString(photo);
                    if (!isNull(specifiedPhoto) && !specifiedPhoto.isEmpty()) {
                        previousImage = specifiedPhoto;
                    }
                } catch (IOException e) {
                    throw new IncorrectDataException(IncorrectData.UPLOADING_ERROR);
                }
            } catch (IncorrectDataException e) {
                req.setAttribute(Attributes.ERR + param, e.getType().getPropertyKey());
                paramsAccepted.set(false);
            }
        }
        if (!isNull(previousImage) && !previousImage.isEmpty()) {
            byte[] photo = Base64.getDecoder().decode(previousImage);
            setter.accept(photo);
            session.setAttribute(param, previousImage);
        }
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
        Collection<PhoneExchanger> privatePhones = (Collection<PhoneExchanger>) req.getSession().getAttribute(
                Attributes.PRIVATE_PHONES_ATTRIBUTE);
        if (!isNull(privatePhones)) {
            phones.addAll(getTypedPhonesFromParam(req, privatePhones, PhoneType.PRIVATE, account,
                    paramsAccepted));
        }
        Collection<PhoneExchanger> workPhones = (Collection<PhoneExchanger>) req.getSession().getAttribute(
                Attributes.WORK_PHONES_ATTRIBUTE);
        if (!isNull(workPhones)) {
            phones.addAll(getTypedPhonesFromParam(req, workPhones, PhoneType.WORK, account, paramsAccepted));
        }
        return phones;
    }

    public static Password getPassword(HttpServletRequest req, Account account, ThreadLocal<Boolean> paramsAccepted) {
        Password password = new PasswordImpl();
        password.setAccount(account);
        setStringFromParam(password::setPassword, password::getPassword, "password", req, paramsAccepted);
        Password confirmPassword = new PasswordImpl();
        confirmPassword.setAccount(account);
        setStringFromParam(confirmPassword::setPassword, password::getPassword, "confirmPassword", req, paramsAccepted);
        if (!Objects.equals(password, confirmPassword)) {
            req.setAttribute("passNotMatch", "error.passwordNotMatch");
            paramsAccepted.set(false);
            return getNullPassword();
        } else {
            return password;
        }
    }

    /**
     * initialises depended on configuration parameters such as sex-value & phone-fields
     * @param req {@link HttpServletRequest} to set attributes into
     * @param accountInfo {@link AccountInfoDTO} stores account's data such as {@link Collection} of phones that should
     *                                          be set in params
     */
    public static void initFields(HttpServletRequest req, AccountInfoDTO accountInfo) {
        HttpSession session = req.getSession();

        req.setAttribute("male", Sex.MALE.toString());
        req.setAttribute("female", Sex.FEMALE.toString());

        initPhonesFields(accountInfo, session, Attributes.PRIVATE_PHONES_ATTRIBUTE, PhoneType.PRIVATE);
        initPhonesFields(accountInfo, session, Attributes.WORK_PHONES_ATTRIBUTE, PhoneType.WORK);
    }

    private static void initPhonesFields(AccountInfoDTO accountInfo, HttpSession session, String param, PhoneType type) {
        Collection<PhoneExchanger> privatePhones;
        String paramName = param.substring(0, param.length() - 1);
        if (!isNull(accountInfo)) {
            privatePhones = accountInfo.getPhones().stream().filter(phone -> type.equals(phone.getType())).
                    map(phone -> new PhoneExchanger("", phone.getNumber(), "")).collect(Collectors.toList());
            while (privatePhones.size() < PHONES_SIZE) {
                privatePhones.add(new PhoneExchanger(paramName + privatePhones.size(), "", ""));
            }
        } else if (isNull(session.getAttribute(param))) {
            privatePhones = new ArrayList<>();
            while (privatePhones.size() < PHONES_SIZE) {
                privatePhones.add(new PhoneExchanger(paramName + privatePhones.size(), "", ""));
            }
        } else {
            privatePhones = (Collection<PhoneExchanger>) session.getAttribute(param);
        }
        session.setAttribute(param, privatePhones);
    }

        public static void getValuesFromParams(HttpServletRequest req, AccountInfoDTO accountInfoDTO, ThreadLocal<Boolean>
            paramsAccepted) throws IOException, ServletException {
        Account account = accountInfoDTO.getAccount();
        setStringFromParam(account::setName, account::getName, "name", req, paramsAccepted);
        setStringFromParam(account::setSurname, account::getSurname, "surname", req, paramsAccepted);
        setStringFromParam(account::setPatronymic, account::getPatronymic, "patronymic", req, paramsAccepted);
        setSexFromParam(account::setSex, account::getSex, "sex", req, paramsAccepted);
        setStringFromParam(account::setEmail, account::getEmail,  "email", req, paramsAccepted);
        setStringFromParam(account::setAdditionalEmail, account::getAdditionalEmail,  "additionalEmail", req, paramsAccepted);

        setDateFromParam(account::setBirthDate, account::getBirthDate,  "birthDate", req, paramsAccepted);

        setStringFromParam(account::setIcq, account::getIcq,  "icq", req, paramsAccepted);
        setStringFromParam(account::setSkype, account::getSkype,  "skype", req, paramsAccepted);
        setStringFromParam(account::setCountry, account::getCountry,  "country", req, paramsAccepted);
        setStringFromParam(account::setCity, account::getCity,  "city", req, paramsAccepted);

        Collection<Phone> phones = getPhonesFromParams(req, account, paramsAccepted);
        accountInfoDTO.getPhones().addAll(phones);

        AccountPhoto accountPhoto = accountInfoDTO.getAccountPhoto();
        setPhotoFromParam(req, accountPhoto::setPhoto, Attributes.PHOTO_ATTRIBUTE, accountPhoto.getMaxSize(), paramsAccepted);
    }

    public static void acceptActionOrRetry(HttpServletRequest req, HttpServletResponse resp, boolean updated,
                                           String successUrl, DoGetWrapper doGet) throws IOException,
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
    public interface DoGetWrapper {
        void accept(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException;
    }
}
