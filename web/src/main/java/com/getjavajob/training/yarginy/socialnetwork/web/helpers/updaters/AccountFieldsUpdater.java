package com.getjavajob.training.yarginy.socialnetwork.web.helpers.updaters;

import com.getjavajob.training.yarginy.socialnetwork.common.exceptions.IncorrectData;
import com.getjavajob.training.yarginy.socialnetwork.common.exceptions.IncorrectDataException;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.password.Password;
import com.getjavajob.training.yarginy.socialnetwork.common.models.phone.Phone;
import com.getjavajob.training.yarginy.socialnetwork.common.models.phone.additionaldata.PhoneType;
import com.getjavajob.training.yarginy.socialnetwork.common.utils.DataHandler;
import com.getjavajob.training.yarginy.socialnetwork.service.infokeepers.AccountInfoKeeper;
import com.getjavajob.training.yarginy.socialnetwork.web.controllers.datakeepers.PhoneView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static com.getjavajob.training.yarginy.socialnetwork.common.models.NullEntitiesFactory.getNullPassword;
import static com.getjavajob.training.yarginy.socialnetwork.common.models.account.additionaldata.Sex.FEMALE;
import static com.getjavajob.training.yarginy.socialnetwork.common.models.account.additionaldata.Sex.MALE;
import static com.getjavajob.training.yarginy.socialnetwork.common.models.phone.additionaldata.PhoneType.PRIVATE;
import static com.getjavajob.training.yarginy.socialnetwork.common.models.phone.additionaldata.PhoneType.WORK;
import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Attributes.*;
import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Pages.ACCOUNT_WALL;
import static java.util.Objects.isNull;

public final class AccountFieldsUpdater {
    private static final String PRIVATE_PHONES_ATTR = "privatePhones";
    private static final String WORK_PHONES_ATTR = "workPhones";
    private final DataHandler dataHandler = new DataHandler();
    private final HttpSession session;
    private final HttpServletRequest req;
    private final String updateFailView;
    private boolean paramsAccepted = true;
    private String updateSuccessUrl;

    public AccountFieldsUpdater(HttpServletRequest req, HttpSession session, String updateFailView) {
        this.session = session;
        this.req = req;
        this.updateFailView = updateFailView;
        Object requestedId = req.getAttribute(REQUESTED_ID);
        if (!isNull(requestedId)) {
            setSuccessUrl(ACCOUNT_WALL, REQUESTED_ID, (long) requestedId);
        } else {
            updateSuccessUrl = ACCOUNT_WALL;
        }
    }

    public AccountInfoKeeper getOrCreateAccountInfo(Supplier<AccountInfoKeeper> accountInfoCreator) {
        AccountInfoKeeper accountInfo = (AccountInfoKeeper) req.getAttribute(ACCOUNT_INFO);
        if (isNull(accountInfo)) {
            accountInfo = accountInfoCreator.get();
            req.setAttribute(ACCOUNT_INFO, accountInfoCreator.get());
        }
        return accountInfo;
    }

    public String getView(AccountInfoKeeper accountInfo, String view) {
        initSex();

        Account account = accountInfo.getAccount();
        byte[] photoBytes = account.getPhoto();
        if (!isNull(photoBytes)) {
            String photo = dataHandler.getHtmlPhoto(photoBytes);
            req.setAttribute(PHOTO, photo);
        }
        req.setAttribute("account", account);

        Collection<Phone> phones = accountInfo.getPhones();

        if (isNull(session.getAttribute(PRIVATE_PHONES_ATTR))) {
            Collection<PhoneView> privatePhones = createPhoneViews(phones, PRIVATE);
            session.setAttribute(PRIVATE_PHONES_ATTR, privatePhones);
        }

        if (isNull(session.getAttribute(WORK_PHONES_ATTR))) {
            Collection<PhoneView> workPhones = createPhoneViews(phones, WORK);
            session.setAttribute(WORK_PHONES_ATTR, workPhones);
        }
        return view;
    }

    private Collection<PhoneView> createPhoneViews(Collection<Phone> phones, PhoneType type) {
        return phones.stream().filter(phone -> type.equals(phone.getType())).
                map(phone -> new PhoneView(phone.getNumber(), "")).collect(Collectors.toList());
    }

    public void initSex() {
        req.setAttribute("male", MALE.toString());
        req.setAttribute("female", FEMALE.toString());
    }

    private Collection<Phone> getPhonesFromParams(Collection<String> enteredPhones, String attribute,
                                                  PhoneType type, AccountInfoKeeper accountInfo) {
        Collection<PhoneView> phoneViews = (Collection<PhoneView>) session.getAttribute(attribute);
        phoneViews.clear();
        Collection<Phone> phones = new ArrayList<>();
        Account account = accountInfo.getAccount();

        if (!isNull(enteredPhones)) {
            for (String enteredPhone : enteredPhones) {
                PhoneView phoneView = new PhoneView(enteredPhone, "");
                phoneViews.add(phoneView);
                try {
                    Phone phone = new Phone(enteredPhone, account);
                    phone.setType(type);
                    phones.add(phone);
                } catch (IncorrectDataException e) {
                    phoneView.setError(e.getType().getPropertyKey());
                    paramsAccepted = false;
                }
            }
        }
        return phones;
    }

    public Password getPassword(Account account, String password, String confirmPassword) {
        Password enteredPassword;
        if (Objects.equals(password, confirmPassword) && !password.isEmpty()) {
            enteredPassword = new Password();
            enteredPassword.setPassword(password);
            enteredPassword.setAccount(account);
        } else {
            req.setAttribute("passNotMatch", "error.passwordNotMatch");
            paramsAccepted = false;
            enteredPassword = getNullPassword();
        }
        return enteredPassword;
    }

    public void getValuesFromParams(AccountInfoKeeper accountInfoKeeper, Collection<String> enteredPrivatePhones,
                                    Collection<String> enteredWorkPhones) {
        Collection<Phone> privatePhones = getPhonesFromParams(enteredPrivatePhones, PRIVATE_PHONES_ATTR, PRIVATE,
                accountInfoKeeper);
        Collection<Phone> workPhones = getPhonesFromParams(enteredWorkPhones, WORK_PHONES_ATTR, WORK, accountInfoKeeper);

        Collection<Phone> phones = accountInfoKeeper.getPhones();
        phones.clear();
        phones.addAll(privatePhones);
        phones.addAll(workPhones);
    }

    public String acceptActionOrRetry(boolean updated, AccountInfoKeeper accountInfoKeeper) {
        if (updated) {
            session.removeAttribute(ACCOUNT_INFO);
            session.removeAttribute(PRIVATE_PHONES_ATTR);
            session.removeAttribute(WORK_PHONES_ATTR);
            session.removeAttribute(PHOTO);
            return "redirect:" + updateSuccessUrl;
        }
        return getView(accountInfoKeeper, updateFailView);
    }

    public String handleInfoExceptions(IncorrectDataException e, AccountInfoKeeper accountInfoKeeper) {
        if (e.getType() == IncorrectData.EMAIL_DUPLICATE) {
            req.setAttribute(EMAIL_DUPLICATE, e.getType().getPropertyKey());
        }
        if (e.getType() == IncorrectData.PHONE_DUPLICATE) {
            req.setAttribute(PHONE_DUPLICATE, e.getType().getPropertyKey());
        }
        if (e.getType() == IncorrectData.UPLOADING_ERROR) {
            req.setAttribute(UPLOAD_ERROR, e.getType().getPropertyKey());
        }
        return getView(accountInfoKeeper, updateFailView);
    }


    public void setSuccessUrl(String successUrl, String param, long value) {
        updateSuccessUrl = successUrl + '?' + param + '=' + value;
    }

    public boolean isParamsAccepted() {
        return paramsAccepted;
    }
}
