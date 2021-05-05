package com.getjavajob.training.yarginy.socialnetwork.web.helpers.updaters;

import com.getjavajob.training.yarginy.socialnetwork.common.exceptions.IncorrectData;
import com.getjavajob.training.yarginy.socialnetwork.common.exceptions.IncorrectDataException;
import com.getjavajob.training.yarginy.socialnetwork.common.models.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.Phone;
import com.getjavajob.training.yarginy.socialnetwork.common.models.additionaldata.PhoneType;
import com.getjavajob.training.yarginy.socialnetwork.common.utils.DataHandler;
import com.getjavajob.training.yarginy.socialnetwork.web.controllers.datakeepers.AccountInfoMvcModel;
import com.getjavajob.training.yarginy.socialnetwork.web.controllers.datakeepers.PhoneView;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

import static com.getjavajob.training.yarginy.socialnetwork.common.models.additionaldata.PhoneType.PRIVATE;
import static com.getjavajob.training.yarginy.socialnetwork.common.models.additionaldata.PhoneType.WORK;
import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Attributes.ACCOUNT_INFO;
import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Attributes.PHOTO;
import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Attributes.REQUESTED_ID;
import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Pages.ACCOUNT_WALL;
import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Pages.REDIRECT;
import static java.util.Objects.isNull;

public final class AccountFieldsUpdater {
    private final DataHandler dataHandler = new DataHandler();
    private final HttpSession session;
    private final String updateFailView;
    private final ModelAndView modelAndView;

    public AccountFieldsUpdater(HttpSession session, String updateFailView) {
        this.session = session;
        this.updateFailView = updateFailView;
        modelAndView = new ModelAndView();
    }

    public ModelAndView getModelAndView(AccountInfoMvcModel accountInfoMvcModel) {
        Account account = accountInfoMvcModel.getAccount();
        if (!isNull(account)) {
            byte[] photoBytes = account.getPhoto();
            if (!isNull(photoBytes)) {
                String photo = dataHandler.getHtmlPhoto(photoBytes);
                modelAndView.addObject(PHOTO, photo);
            }
        }

        modelAndView.addObject("accountInfoMvcModel", accountInfoMvcModel);
        modelAndView.addObject("privatePhones", accountInfoMvcModel.getPrivatePhones());
        modelAndView.addObject("workPhones", accountInfoMvcModel.getWorkPhones());

        modelAndView.setViewName(updateFailView);
        return modelAndView;
    }

    public ModelAndView acceptActionOrRetry(boolean updated, AccountInfoMvcModel accountInfoMvcModel) {
        if (updated && !isNull(accountInfoMvcModel.getAccount())) {
            session.removeAttribute(PHOTO);
            session.removeAttribute(ACCOUNT_INFO);
            long id = accountInfoMvcModel.getAccount().getId();
            return new ModelAndView(REDIRECT + ACCOUNT_WALL + '?' + REQUESTED_ID + '=' + id);
        }
        accountInfoMvcModel.setPassword(null);
        accountInfoMvcModel.setConfirmPassword(null);
        return getModelAndView(accountInfoMvcModel);
    }

    public ModelAndView handleInfoExceptions(IncorrectDataException e, AccountInfoMvcModel accountInfoMvcModel,
                                             BindingResult result) {
        if (e.getType() == IncorrectData.EMAIL_DUPLICATE) {
            result.rejectValue("account.email", "error.accountDuplicate");
        }
        if (e.getType() == IncorrectData.PHONE_DUPLICATE) {
            result.rejectValue("privatePhones", "error.phoneDuplicate");
            result.rejectValue("workPhones", "error.phoneDuplicate");
        }
        if (e.getType() == IncorrectData.UPLOADING_ERROR) {
            result.rejectValue("account.photo", "error.failedToUpload");
        }
        return getModelAndView(accountInfoMvcModel);
    }

    public Collection<Phone> getPhonesFromModel(AccountInfoMvcModel accountInfoMvcModel) {
        Account account = accountInfoMvcModel.getAccount();
        Collection<Phone> phones = getPhonesFromModel(accountInfoMvcModel.getPrivatePhones(), PRIVATE, account);
        phones.addAll(getPhonesFromModel(accountInfoMvcModel.getWorkPhones(), WORK, account));
        return phones;
    }

    private Collection<Phone> getPhonesFromModel(Collection<PhoneView> phoneViews, PhoneType type, Account account) {
        Collection<Phone> phones = new ArrayList<>();
        if (!isNull(phoneViews)) {
            phones.addAll(phoneViews.stream().
                    map(e -> {
                        Phone phone = new Phone(e.getNumber(), account);
                        phone.setType(type);
                        return phone;
                    }).collect(Collectors.toList()));
        }
        return phones;
    }
}
