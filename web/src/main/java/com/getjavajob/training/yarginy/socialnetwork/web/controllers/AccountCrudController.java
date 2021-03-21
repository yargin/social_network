package com.getjavajob.training.yarginy.socialnetwork.web.controllers;

import com.getjavajob.training.yarginy.socialnetwork.common.exceptions.IncorrectDataException;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.password.Password;
import com.getjavajob.training.yarginy.socialnetwork.common.models.phone.Phone;
import com.getjavajob.training.yarginy.socialnetwork.common.models.phone.additionaldata.PhoneType;
import com.getjavajob.training.yarginy.socialnetwork.service.AccountService;
import com.getjavajob.training.yarginy.socialnetwork.service.AuthService;
import com.getjavajob.training.yarginy.socialnetwork.web.controllers.datakeepers.AccountInfoMvcModel;
import com.getjavajob.training.yarginy.socialnetwork.web.controllers.datakeepers.PhoneView;
import com.getjavajob.training.yarginy.socialnetwork.web.helpers.updaters.AccountFieldsUpdater;
import com.getjavajob.training.yarginy.socialnetwork.web.validators.composite.AccountInfoValidator;
import com.getjavajob.training.yarginy.socialnetwork.web.validators.composite.RegistrationValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.Collection;
import java.util.List;

import static com.getjavajob.training.yarginy.socialnetwork.common.models.phone.additionaldata.PhoneType.PRIVATE;
import static com.getjavajob.training.yarginy.socialnetwork.common.models.phone.additionaldata.PhoneType.WORK;
import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Attributes.ACCOUNT_INFO;
import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Attributes.PHOTO;
import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Pages.LOGOUT;
import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Pages.REDIRECT;
import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Views.ACCOUNT_UPDATE_VIEW;
import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Views.REGISTRATION_VIEW;
import static java.util.Objects.isNull;
import static java.util.stream.Collectors.toList;

@Controller
public class AccountCrudController {
    private final AuthService authService;
    private final AccountService accountService;
    private final RegistrationValidator registrationValidator;
    private final AccountInfoValidator accountInfoValidator;

    @Autowired
    public AccountCrudController(AuthService authService, AccountInfoValidator accountInfoValidator,
                                 AccountService accountService, RegistrationValidator registrationValidator) {
        this.authService = authService;
        this.accountService = accountService;
        this.registrationValidator = registrationValidator;
        this.accountInfoValidator = accountInfoValidator;
    }

    @GetMapping("/registration")
    public ModelAndView showRegister(HttpSession session) {
        AccountFieldsUpdater updater = new AccountFieldsUpdater(session, REGISTRATION_VIEW);

        return updater.getModelAndView(new AccountInfoMvcModel());
    }

    @PostMapping("/registration")
    public ModelAndView register(HttpSession session, @ModelAttribute AccountInfoMvcModel accountInfoMvcModel,
                                 BindingResult result) {
        AccountFieldsUpdater updater = new AccountFieldsUpdater(session, REGISTRATION_VIEW);

        setNewPhotoOrGetPrevious(session, accountInfoMvcModel);

        registrationValidator.validate(accountInfoMvcModel, result);
        if (result.hasErrors()) {
            return updater.getModelAndView(accountInfoMvcModel);
        }
        return register(updater, accountInfoMvcModel, result);
    }

    private void setNewPhotoOrGetPrevious(HttpSession session, AccountInfoMvcModel accountInfoMvcModel) {
        byte[] previousPhoto = isNull(session.getAttribute(PHOTO)) ? new byte[0] : (byte[]) session.getAttribute(PHOTO);
        Account account = accountInfoMvcModel.getAccount();
        if (account.getPhoto().length == 0 && previousPhoto.length > 0) {
            account.setPhoto(previousPhoto);
        } else {
            session.setAttribute(PHOTO, account.getPhoto());
        }
    }

    private ModelAndView register(AccountFieldsUpdater updater, AccountInfoMvcModel accountInfoMvcModel,
                                  BindingResult result) {
        boolean registered;
        Collection<Phone> phones = updater.getPhonesFromModel(accountInfoMvcModel);
        Password password = accountInfoMvcModel.getPassword();
        try {
            registered = authService.register(accountInfoMvcModel.getAccount(), phones, password);
        } catch (IncorrectDataException e) {
            return updater.handleInfoExceptions(e, accountInfoMvcModel, result);
        }
        return updater.acceptActionOrRetry(registered, accountInfoMvcModel);
    }

    private List<PhoneView> getPhoneViews(Collection<Phone> allPhones, PhoneType type) {
        return allPhones.stream().filter(phone -> type.equals(phone.getType())).
                map(phone -> new PhoneView(phone.getNumber(), "")).collect(toList());
    }

    @GetMapping("/account/update")
    public ModelAndView showUpdate(HttpSession session, @RequestAttribute long id) {
        AccountFieldsUpdater updater = new AccountFieldsUpdater(session, ACCOUNT_UPDATE_VIEW);
        AccountInfoMvcModel model = new AccountInfoMvcModel();
        Account account = accountService.get(id);
        model.setAccount(account);
        Collection<Phone> allPhones = accountService.getPhones(id);
        model.setPrivatePhones(getPhoneViews(allPhones, PRIVATE));
        model.setWorkPhones(getPhoneViews(allPhones, WORK));
        //save original to session
        session.setAttribute(PHOTO, account.getPhoto());
        session.setAttribute(ACCOUNT_INFO, model);
        return updater.getModelAndView(model);
    }

    @PostMapping("/account/update")
    public ModelAndView performUpdate(HttpSession session, @ModelAttribute AccountInfoMvcModel model,
                                      @RequestParam(required = false) String save, BindingResult result) {
        AccountFieldsUpdater updater = new AccountFieldsUpdater(session, ACCOUNT_UPDATE_VIEW);

        if ("cancel".equals(save)) {
            return updater.acceptActionOrRetry(true, model);
        }
        AccountInfoMvcModel storedModel = (AccountInfoMvcModel) session.getAttribute(ACCOUNT_INFO);
        Account storedAccount = storedModel.getAccount();
        Account account = model.getAccount();
//        //set non updatable values
        account.setId(storedAccount.getId());
        account.setEmail(storedAccount.getEmail());
        account.setRegistrationDate(storedAccount.getRegistrationDate());
        account.setRole(storedAccount.getRole());

        setNewPhotoOrGetPrevious(session, model);

        accountInfoValidator.validate(model, result);
        if (result.hasErrors()) {
            return updater.getModelAndView(model);
        }
        return update(updater, model, storedModel, result);
    }

    private ModelAndView update(AccountFieldsUpdater updater, AccountInfoMvcModel model, AccountInfoMvcModel storedModel,
                                BindingResult result) {
        boolean updated;
        try {
            Collection<Phone> phones = updater.getPhonesFromModel(model);
            Collection<Phone> storedPhones = updater.getPhonesFromModel(storedModel);
            updated = accountService.updateAccount(model.getAccount(), storedModel.getAccount(), phones, storedPhones);
        } catch (IncorrectDataException e) {
            return updater.handleInfoExceptions(e, model, result);
        }
        return updater.acceptActionOrRetry(updated, model);
    }

    @GetMapping("/account/delete")
    public String delete(@ModelAttribute Account accountToDelete) {
        if (accountService.deleteAccount(accountToDelete)) {
            return REDIRECT + LOGOUT;
        }
        return "error";
    }

    //    todo bean form
    @InitBinder
    public void registerPhotoEditor(WebDataBinder binder) {
        binder.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }
}
