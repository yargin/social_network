package com.getjavajob.training.yarginy.socialnetwork.web.controllers;

import com.getjavajob.training.yarginy.socialnetwork.common.exceptions.IncorrectDataException;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.additionaldata.Role;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.additionaldata.Sex;
import com.getjavajob.training.yarginy.socialnetwork.common.models.password.Password;
import com.getjavajob.training.yarginy.socialnetwork.service.AccountInfoService;
import com.getjavajob.training.yarginy.socialnetwork.service.AccountService;
import com.getjavajob.training.yarginy.socialnetwork.service.AuthService;
import com.getjavajob.training.yarginy.socialnetwork.service.infokeepers.AccountInfoKeeper;
import com.getjavajob.training.yarginy.socialnetwork.web.controllers.editors.RoleEditor;
import com.getjavajob.training.yarginy.socialnetwork.web.controllers.editors.SexEditor;
import com.getjavajob.training.yarginy.socialnetwork.web.helpers.updaters.AccountFieldsUpdater;
import com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Attributes;
import com.getjavajob.training.yarginy.socialnetwork.web.validators.AccountValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Collection;

import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Attributes.ACCOUNT_INFO;
import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Attributes.PHOTO;
import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Pages.LOGOUT;
import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Views.ACCOUNT_UPDATE_VIEW;
import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Views.REGISTRATION_VIEW;
import static java.util.Objects.isNull;

@Controller
public class AccountCrudController {
    private final AuthService authService;
    private final AccountInfoService accountInfoService;
    private final AccountService accountService;
    private final AccountValidator accountValidator;

    @Autowired
    public AccountCrudController(AuthService authService, AccountInfoService accountInfoService,
                                 AccountService accountService, AccountValidator accountValidator) {
        this.authService = authService;
        this.accountInfoService = accountInfoService;
        this.accountService = accountService;
        this.accountValidator = accountValidator;
    }

    @GetMapping("/registration")
    public ModelAndView showRegister(HttpSession session) {
        AccountFieldsUpdater updater = new AccountFieldsUpdater(session, REGISTRATION_VIEW);

        AccountInfoKeeper accountInfoKeeper = new AccountInfoKeeper();
        if (isNull(session.getAttribute(ACCOUNT_INFO))) {
            session.setAttribute(ACCOUNT_INFO, accountInfoKeeper);
        }

        return updater.getModelAndView(accountInfoKeeper);
    }

    @PostMapping("/registration")
    public ModelAndView register(HttpSession session, @ModelAttribute Account account, BindingResult result,
                                 @RequestParam String password, @RequestParam String confirmPassword,
                                 @RequestParam(required = false) Collection<String> privatePhones,
                                 @RequestParam(required = false) Collection<String> workPhones) {
        AccountFieldsUpdater updater = new AccountFieldsUpdater(session, REGISTRATION_VIEW);

        AccountInfoKeeper accountInfoKeeper = (AccountInfoKeeper) session.getAttribute(Attributes.ACCOUNT_INFO);

        Account storedAccount = accountInfoKeeper.getAccount();
        if (account.getPhoto().length == 0) {
            account.setPhoto(storedAccount.getPhoto());
        }
        accountInfoKeeper.setAccount(account);

        updater.getPhonesFromParams(accountInfoKeeper, privatePhones, workPhones);

        Password enteredPassword = updater.getPassword(account, password, confirmPassword);

        boolean accepted = updater.isParamsAccepted();
        updater.addAttribute(ACCOUNT_INFO, accountInfoKeeper);
        if (!accepted) {
            return updater.getModelAndView(accountInfoKeeper);
        } else {
            accountValidator.validate(account, result);
            if (result.hasErrors()) {
                return updater.getModelAndView(accountInfoKeeper);
            }
            return register(updater, accountInfoKeeper, enteredPassword);
        }
    }

    private ModelAndView register(AccountFieldsUpdater updater, AccountInfoKeeper accountInfoKeeper, Password password) {
        boolean registered;
        try {
            registered = authService.register(accountInfoKeeper, password);
        } catch (IncorrectDataException e) {
            return updater.handleInfoExceptions(e, accountInfoKeeper);
        }
        return updater.acceptActionOrRetry(registered, accountInfoKeeper);
    }

    @GetMapping("/account/update")
    public ModelAndView showUpdate(HttpSession session, @RequestAttribute("id") long requestedUserId) {
        AccountFieldsUpdater updater = new AccountFieldsUpdater(session, ACCOUNT_UPDATE_VIEW);

        //select at first visit
        AccountInfoKeeper accountInfoKeeper = accountInfoService.select(requestedUserId);
        //save original to session if wasn't
        if (isNull(session.getAttribute(ACCOUNT_INFO))) {
            session.setAttribute(ACCOUNT_INFO, accountInfoKeeper);
        }

        return updater.getModelAndView(accountInfoKeeper);
    }

    @PostMapping("/account/update")
    public ModelAndView performUpdate(HttpSession session, @ModelAttribute Account account, BindingResult result,
                                      @RequestParam(required = false) Collection<String> privatePhones,
                                      @RequestParam(required = false) Collection<String> workPhones,
                                      @RequestParam(required = false) String save) {
        AccountFieldsUpdater updater = new AccountFieldsUpdater(session, ACCOUNT_UPDATE_VIEW);

        AccountInfoKeeper storedAccountInfoKeeper = (AccountInfoKeeper) session.getAttribute(ACCOUNT_INFO);
        if ("cancel".equals(save)) {
            return updater.acceptActionOrRetry(true, storedAccountInfoKeeper);
        }

        AccountInfoKeeper accountInfoKeeper = new AccountInfoKeeper(account, new ArrayList<>());
        Account storedAccount = storedAccountInfoKeeper.getAccount();
        //set non updatable values
        account.setEmail(storedAccount.getEmail());
        account.setRegistrationDate(storedAccount.getRegistrationDate());

        updater.getPhonesFromParams(accountInfoKeeper, privatePhones, workPhones);

        if (account.getPhoto().length == 0) {
            if (isNull(session.getAttribute(PHOTO))) {
                account.setPhoto(storedAccount.getPhoto());
                session.setAttribute(PHOTO, storedAccount.getPhoto());
            } else {
                account.setPhoto((byte[]) session.getAttribute(PHOTO));
            }
        } else {
            session.setAttribute(PHOTO, account.getPhoto());
        }

        updater.addAttribute(ACCOUNT_INFO, accountInfoKeeper);
        boolean accepted = updater.isParamsAccepted();
        accountValidator.validate(account, result);
        if (!accepted || result.hasErrors()) {
            return updater.getModelAndView(accountInfoKeeper);
        } else {
            return update(updater, accountInfoKeeper, storedAccountInfoKeeper);
        }
    }

    private ModelAndView update(AccountFieldsUpdater updater, AccountInfoKeeper accountInfoKeeper,
                                AccountInfoKeeper storedAccountInfoKeeper) {
        boolean updated;
        try {
            updated = accountInfoService.update(accountInfoKeeper, storedAccountInfoKeeper);
        } catch (IncorrectDataException e) {
            return updater.handleInfoExceptions(e, accountInfoKeeper);
        }
        return updater.acceptActionOrRetry(updated, accountInfoKeeper);
    }

    @GetMapping("/account/delete")
    public String delete(@ModelAttribute Account accountToDelete) {
        if (accountService.deleteAccount(accountToDelete)) {
            return "redirect:" + LOGOUT;
        }
        return "error";
    }

    @InitBinder("account")
    public void registerPhotoEditor(WebDataBinder binder) {
        binder.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());
    }

    @InitBinder("role")
    public void registerRoleEditor(WebDataBinder binder) {
        binder.registerCustomEditor(Role.class, new RoleEditor());
    }

    @InitBinder("sex")
    public void registerSexEditor(WebDataBinder binder) {
        binder.registerCustomEditor(Sex.class, new SexEditor());
    }
}
