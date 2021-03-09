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
import com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Pages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Collection;

import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Attributes.*;
import static java.util.Objects.isNull;

@Controller
public class AccountCrudController {
    private final AuthService authService;
    private final AccountInfoService accountInfoService;
    private final AccountService accountService;

    @Autowired
    public AccountCrudController(AuthService authService, AccountInfoService accountInfoService,
                                 AccountService accountService) {
        this.authService = authService;
        this.accountInfoService = accountInfoService;
        this.accountService = accountService;
    }

    @GetMapping("/registration")
    public String showRegister(HttpServletRequest req, HttpSession session) {
        AccountFieldsUpdater updater = new AccountFieldsUpdater(req, session);

        AccountInfoKeeper accountInfoKeeper = updater.getOrCreateAccountInfo(AccountInfoKeeper::new);
        if (isNull(session.getAttribute(ACCOUNT_INFO))) {
            session.setAttribute(ACCOUNT_INFO, accountInfoKeeper);
        }

        updater.initAccountAttributes(accountInfoKeeper);

        req.setAttribute(Attributes.TARGET, Pages.REGISTER);

        return "registration";
    }

    @PostMapping("/registration")
    public String register(HttpServletRequest req, HttpSession session, @ModelAttribute Account account,
                           @RequestParam String password, @RequestParam String confirmPassword,
                           @RequestParam(required = false) Collection<String> privatePhones,
                           @RequestParam(required = false) Collection<String> workPhones) {
        AccountFieldsUpdater updater = new AccountFieldsUpdater(req, session);

        AccountInfoKeeper accountInfoKeeper = (AccountInfoKeeper) session.getAttribute(Attributes.ACCOUNT_INFO);

        Account storedAccount = accountInfoKeeper.getAccount();
        if (account.getPhoto().length == 0) {
            account.setPhoto(storedAccount.getPhoto());
        }
        accountInfoKeeper.setAccount(account);

        updater.getValuesFromParams(accountInfoKeeper, privatePhones, workPhones);

        Password enteredPassword = updater.getPassword(account, password, confirmPassword);

        boolean accepted = updater.isParamsAccepted();
        req.setAttribute(ACCOUNT_INFO, accountInfoKeeper);
        if (!accepted) {
            return showRegister(req, session);
        } else {
            return register(updater, accountInfoKeeper, enteredPassword);
        }
    }

    private String register(AccountFieldsUpdater updater, AccountInfoKeeper accountInfoKeeper, Password password) {
        boolean registered;
        try {
            registered = authService.register(accountInfoKeeper, password);
        } catch (IncorrectDataException e) {
            return updater.handleInfoExceptions(e, this::showRegister);
        }
        return updater.acceptActionOrRetry(registered, this::showRegister);
    }

    @GetMapping("/account/update")
    public String showUpdate(HttpServletRequest req, HttpSession session) {
        AccountFieldsUpdater updater = new AccountFieldsUpdater(req, session);

        //select at first visit
        long requestedUserId = (long) req.getAttribute(REQUESTED_ID);
        AccountInfoKeeper accountInfoKeeper = updater.getOrCreateAccountInfo(
                () -> accountInfoService.select(requestedUserId));
        //save original to session if wasn't
        if (isNull(req.getSession().getAttribute(ACCOUNT_INFO))) {
            session.setAttribute(ACCOUNT_INFO, accountInfoKeeper);
        }

        updater.initAccountAttributes(accountInfoKeeper);

        req.setAttribute(TARGET, Pages.UPDATE_ACCOUNT);

        return "accountUpdate";
    }

    @PostMapping("/account/update")
    public String performUpdate(HttpServletRequest req, HttpSession session, @ModelAttribute Account account,
                                @RequestParam(required = false) Collection<String> privatePhones,
                                @RequestParam(required = false) Collection<String> workPhones) {
        AccountFieldsUpdater updater = new AccountFieldsUpdater(req, req.getSession());

        if ("cancel".equals(req.getParameter("save"))) {
            return updater.acceptActionOrRetry(true, null);
        }

        AccountInfoKeeper accountInfoKeeper = new AccountInfoKeeper(account, new ArrayList<>());
        AccountInfoKeeper storedAccountInfoKeeper = (AccountInfoKeeper) session.getAttribute(ACCOUNT_INFO);
        //set non updatable values
        Account storedAccount = storedAccountInfoKeeper.getAccount();
        account.setEmail(storedAccount.getEmail());
        account.setRegistrationDate(storedAccount.getRegistrationDate());

        updater.getValuesFromParams(accountInfoKeeper, privatePhones, workPhones);

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

        req.setAttribute(ACCOUNT_INFO, accountInfoKeeper);
        boolean accepted = updater.isParamsAccepted();
        if (!accepted) {
            return showUpdate(req, session);
        } else {
            return update(updater, accountInfoKeeper, storedAccountInfoKeeper);
        }
    }

    private String update(AccountFieldsUpdater updater, AccountInfoKeeper accountInfoKeeper,
                          AccountInfoKeeper storedAccountInfoKeeper) {
        boolean updated;
        try {
            updated = accountInfoService.update(accountInfoKeeper, storedAccountInfoKeeper);
        } catch (IncorrectDataException e) {
            return updater.handleInfoExceptions(e, this::showUpdate);
        }
        return updater.acceptActionOrRetry(updated, this::showUpdate);
    }

    @GetMapping("/account/delete")
    public String delete(@ModelAttribute Account accountToDelete) {
        if (accountService.deleteAccount(accountToDelete)) {
            return "redirect:" + Pages.LOGOUT;
        }
        return "error";
    }

    @InitBinder("account")
    public void registerPhotoBinder(WebDataBinder binder) {
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
