package com.getjavajob.training.yarginy.socialnetwork.web.controllers;

import com.getjavajob.training.yarginy.socialnetwork.common.exceptions.IncorrectDataException;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.additionaldata.Role;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.additionaldata.Sex;
import com.getjavajob.training.yarginy.socialnetwork.common.models.password.Password;
import com.getjavajob.training.yarginy.socialnetwork.service.AuthService;
import com.getjavajob.training.yarginy.socialnetwork.service.infokeepers.AccountInfoKeeper;
import com.getjavajob.training.yarginy.socialnetwork.web.controllers.editors.RoleEditor;
import com.getjavajob.training.yarginy.socialnetwork.web.controllers.editors.SexEditor;
import com.getjavajob.training.yarginy.socialnetwork.web.servlethelpers.AccountFieldsUpdater;
import com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Attributes;
import com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Pages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Attributes.ACCOUNT_INFO;
import static java.util.Objects.isNull;

@Controller
public class AccountCrudController {
    private final AuthService authService;

    @Autowired
    public AccountCrudController(AuthService authService) {
        this.authService = authService;
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
                           @RequestParam String password, @RequestParam String confirmPassword) {
        AccountFieldsUpdater updater = new AccountFieldsUpdater(req, session);

        AccountInfoKeeper accountInfoKeeper = (AccountInfoKeeper) session.getAttribute(Attributes.ACCOUNT_INFO);

        Account storedAccount = accountInfoKeeper.getAccount();
        if (account.getPhoto().length == 0) {
            account.setPhoto(storedAccount.getPhoto());
        }
        accountInfoKeeper.setAccount(account);

        updater.getValuesFromParams(accountInfoKeeper);

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
