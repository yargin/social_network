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
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
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
    private final String updateFailView;
    private final ModelAndView modelAndView;
    private boolean paramsAccepted = true;

    public AccountFieldsUpdater(HttpSession session, String updateFailView) {
        this.session = session;
        this.updateFailView = updateFailView;
        modelAndView = new ModelAndView();
    }

    public ModelAndView getModelAndView(AccountInfoKeeper accountInfo, String view) {
        modelAndView.addObject("male", MALE.toString());
        modelAndView.addObject("female", FEMALE.toString());

        Account account = accountInfo.getAccount();
        byte[] photoBytes = account.getPhoto();
        if (!isNull(photoBytes)) {
            String photo = dataHandler.getHtmlPhoto(photoBytes);
            modelAndView.addObject(PHOTO, photo);
        }
        modelAndView.addObject("account", account);

        Collection<Phone> phones = accountInfo.getPhones();

        if (isNull(session.getAttribute(PRIVATE_PHONES_ATTR))) {
            Collection<PhoneView> privatePhones = createPhoneViews(phones, PRIVATE);
            session.setAttribute(PRIVATE_PHONES_ATTR, privatePhones);
        }

        if (isNull(session.getAttribute(WORK_PHONES_ATTR))) {
            Collection<PhoneView> workPhones = createPhoneViews(phones, WORK);
            session.setAttribute(WORK_PHONES_ATTR, workPhones);
        }
        modelAndView.setViewName(view);
        return modelAndView;
    }

    private ArrayList<PhoneView> createPhoneViews(Collection<Phone> phones, PhoneType type) {
        return phones.stream().filter(phone -> type.equals(phone.getType())).
                map(phone -> new PhoneView(phone.getNumber(), "")).collect(Collectors.toCollection(ArrayList::new));
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
            modelAndView.addObject("passNotMatch", "error.passwordNotMatch");
            paramsAccepted = false;
            enteredPassword = getNullPassword();
        }
        return enteredPassword;
    }

    public void getPhonesFromParams(AccountInfoKeeper accountInfoKeeper, Collection<String> enteredPrivatePhones,
                                    Collection<String> enteredWorkPhones) {
        Collection<Phone> privatePhones = getPhonesFromParams(enteredPrivatePhones, PRIVATE_PHONES_ATTR, PRIVATE,
                accountInfoKeeper);
        Collection<Phone> workPhones = getPhonesFromParams(enteredWorkPhones, WORK_PHONES_ATTR, WORK, accountInfoKeeper);

        Collection<Phone> phones = accountInfoKeeper.getPhones();
        phones.clear();
        phones.addAll(privatePhones);
        phones.addAll(workPhones);
    }

    public ModelAndView acceptActionOrRetry(boolean updated, AccountInfoKeeper accountInfoKeeper) {
        if (updated && !isNull(accountInfoKeeper.getAccount())) {
            session.removeAttribute(ACCOUNT_INFO);
            session.removeAttribute(PRIVATE_PHONES_ATTR);
            session.removeAttribute(WORK_PHONES_ATTR);
            session.removeAttribute(PHOTO);
            long id = accountInfoKeeper.getAccount().getId();
            return new ModelAndView("redirect:" + ACCOUNT_WALL + '?' + REQUESTED_ID + '=' + id);
        }
        return getModelAndView(accountInfoKeeper, updateFailView);
    }

    public ModelAndView handleInfoExceptions(IncorrectDataException e, AccountInfoKeeper accountInfoKeeper) {
        if (e.getType() == IncorrectData.EMAIL_DUPLICATE) {
            modelAndView.addObject(EMAIL_DUPLICATE, e.getType().getPropertyKey());
        }
        if (e.getType() == IncorrectData.PHONE_DUPLICATE) {
            modelAndView.addObject(PHONE_DUPLICATE, e.getType().getPropertyKey());
        }
        if (e.getType() == IncorrectData.UPLOADING_ERROR) {
            modelAndView.addObject(UPLOAD_ERROR, e.getType().getPropertyKey());
        }
        return getModelAndView(accountInfoKeeper, updateFailView);
    }

    public boolean isParamsAccepted() {
        return paramsAccepted;
    }

    public void addAttribute(String name, Object value) {
        modelAndView.addObject(name, value);
    }
}
