package com.getjavajob.training.yarginy.socialnetwork.web.controllers;

import com.getjavajob.training.yarginy.socialnetwork.common.exceptions.IncorrectDataException;
import com.getjavajob.training.yarginy.socialnetwork.common.models.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.Phone;
import com.getjavajob.training.yarginy.socialnetwork.common.models.additionaldata.PhoneType;
import com.getjavajob.training.yarginy.socialnetwork.common.models.additionaldata.Role;
import com.getjavajob.training.yarginy.socialnetwork.service.AccountService;
import com.getjavajob.training.yarginy.socialnetwork.service.RegisterService;
import com.getjavajob.training.yarginy.socialnetwork.service.dto.AccountInfoXml;
import com.getjavajob.training.yarginy.socialnetwork.service.xml.AccountInfoXmlServiceImpl;
import com.getjavajob.training.yarginy.socialnetwork.web.controllers.datakeepers.AccountInfoMvcModel;
import com.getjavajob.training.yarginy.socialnetwork.web.controllers.datakeepers.PhoneView;
import com.getjavajob.training.yarginy.socialnetwork.web.helpers.Redirector;
import com.getjavajob.training.yarginy.socialnetwork.web.helpers.updaters.AccountFieldsUpdater;
import com.getjavajob.training.yarginy.socialnetwork.web.validators.composite.AccountInfoValidator;
import com.getjavajob.training.yarginy.socialnetwork.web.validators.composite.RegistrationValidator;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.List;

import static com.getjavajob.training.yarginy.socialnetwork.common.models.additionaldata.PhoneType.PRIVATE;
import static com.getjavajob.training.yarginy.socialnetwork.common.models.additionaldata.PhoneType.WORK;
import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Attributes.ACCOUNT_INFO;
import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Attributes.PHOTO;
import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Pages.ACCOUNT_WALL;
import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Views.ACCOUNT_UPDATE_VIEW;
import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Views.REGISTRATION_VIEW;
import static java.util.Objects.isNull;
import static java.util.stream.Collectors.toList;

@Controller
public class AccountCrudController {
    private final RegisterService registerService;
    private final AccountService accountService;
    private final AccountInfoXmlServiceImpl accountInfoXmlService;
    private final RegistrationValidator registrationValidator;
    private final AccountInfoValidator accountInfoValidator;
    private final Redirector redirector;

    public AccountCrudController(RegisterService registerService, AccountInfoValidator accountInfoValidator,
                                 AccountService accountService, RegistrationValidator registrationValidator,
                                 AccountInfoXmlServiceImpl accountInfoXmlService, Redirector redirector) {
        this.registerService = registerService;
        this.accountService = accountService;
        this.registrationValidator = registrationValidator;
        this.accountInfoValidator = accountInfoValidator;
        this.accountInfoXmlService = accountInfoXmlService;
        this.redirector = redirector;
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
        String password = accountInfoMvcModel.getPassword();
        try {
            registered = registerService.register(accountInfoMvcModel.getAccount(), phones, password);
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
        Account account = accountService.getFullInfo(id);
        model.setAccount(account);
        Collection<Phone> allPhones = accountService.getPhones(id);
        model.setPrivatePhones(getPhoneViews(allPhones, PRIVATE));
        model.setWorkPhones(getPhoneViews(allPhones, WORK));
        //save original info to session
        session.setAttribute(ACCOUNT_INFO, model);
        session.setAttribute(PHOTO, account.getPhoto());
        return updater.getModelAndView(model);
    }


    @PostMapping("/account/update")
    public ModelAndView performUpdate(HttpSession session, HttpServletRequest request,
                                      @ModelAttribute AccountInfoMvcModel model,
                                      @RequestParam(required = false) String save, BindingResult result) {
        AccountFieldsUpdater updater = new AccountFieldsUpdater(session, ACCOUNT_UPDATE_VIEW);

        if ("cancel".equals(save)) {
            return updater.acceptActionOrRetry(true, model);
        }
        AccountInfoMvcModel storedModel = (AccountInfoMvcModel) session.getAttribute(ACCOUNT_INFO);
        if (isNull(storedModel)) {
            request.setAttribute("concurrentError", "error.concurrentError");
            return showUpdate(session, model.getAccount().getId());
        }
        Account storedAccount = storedModel.getAccount();
        Account account = model.getAccount();
        //set non updatable values
        account.setId(storedAccount.getId());
        account.setEmail(storedAccount.getEmail());
        account.setRegistrationDate(storedAccount.getRegistrationDate());
        account.setRole(storedAccount.getRole());
        account.setVersion(storedAccount.getVersion());

        setNewPhotoOrGetPrevious(session, model);

        accountInfoValidator.validate(model, result);
        if (result.hasErrors()) {
            return updater.getModelAndView(model);
        }
        try {
            return update(updater, model, storedModel, result);
        } catch (IllegalStateException e) {
            request.setAttribute("concurrentError", "error.concurrentError");
            return showUpdate(session, storedAccount.getId());
        }
    }

    private ModelAndView update(AccountFieldsUpdater updater, AccountInfoMvcModel model, AccountInfoMvcModel storedModel,
                                BindingResult result) {
        boolean updated;
        try {
            Collection<Phone> phones = updater.getPhonesFromModel(model);
            Collection<Phone> storedPhones = updater.getPhonesFromModel(storedModel);
            updated = accountService.updateAccount(model.getAccount(), phones, storedPhones);
        } catch (IncorrectDataException e) {
            return updater.handleInfoExceptions(e, model, result);
        }
        return updater.acceptActionOrRetry(updated, model);
    }

    @PostMapping("/account/upload")
    public ModelAndView loadUploadedAccountInfo(@RequestParam MultipartFile accountDataXml, HttpSession session,
                                                @ModelAttribute AccountInfoMvcModel accountInfoMvcModel,
                                                BindingResult result, HttpServletRequest request,
                                                @RequestAttribute long id) throws IOException {
        String xml = new String(accountDataXml.getBytes(), StandardCharsets.UTF_8);
        try {
            AccountInfoXml accountXml = accountInfoXmlService.fromXml(xml);
            accountXml.getAccount().setPhoto(new byte[0]);
            Account account = accountXml.getAccount();
            account.setId(id);
            accountInfoMvcModel.setAccount(account);
            accountInfoMvcModel.setPrivatePhones(getPhoneViews(accountXml.getPhones(), PRIVATE));
            accountInfoMvcModel.setWorkPhones(getPhoneViews(accountXml.getPhones(), WORK));
        } catch (IllegalStateException e) {
            request.setAttribute("uploadError", "error.wrongXml");
            return showUpdate(session, id);
        } catch (IllegalArgumentException e) {
            request.setAttribute("uploadError", "error.wrongAccountData");
            return showUpdate(session, id);
        }
        return performUpdate(session, request, accountInfoMvcModel, "", result);
    }

    @GetMapping("/account/delete")
    @ResponseBody
    public String delete(@RequestAttribute long id) {
        Account accountToDelete = new Account(id);
        try {
            if (accountService.deleteAccount(accountToDelete)) {
                return ACCOUNT_WALL;
            }
        } catch (AccessDeniedException e) {
            return "";
        }
        return "error";
    }

    @GetMapping("/account/savexml")
    public void getFile(HttpServletResponse response, @RequestAttribute long id) throws IOException {
        Account account = accountService.get(id);
        Collection<Phone> allPhones = accountService.getPhones(id);
        String accountXml = accountInfoXmlService.toXml(new AccountInfoXml(account, allPhones));

        AccountInfoMvcModel model = new AccountInfoMvcModel();
        model.setAccount(account);
        model.setPrivatePhones(getPhoneViews(allPhones, PRIVATE));
        model.setWorkPhones(getPhoneViews(allPhones, WORK));
        response.getWriter().print(accountXml);
    }

    @InitBinder
    public void registerPhotoEditor(WebDataBinder binder) {
        binder.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    @GetMapping("/account/upgraderole")
    public String upgradeAccountRoleToAdmin(@RequestAttribute long id) {
        try {
            accountService.setRole(new Account(id), Role.ADMIN);
        } catch (AccessDeniedException e) {
            return "";
        }
        return redirector.getMvcPathForRedirect(ACCOUNT_WALL, id);
    }

    @GetMapping("/account/downgraderole")
    public String downgradeAccountRoleToAdmin(@RequestAttribute long id) {
        accountService.setRole(new Account(id), Role.USER);
        return redirector.getMvcPathForRedirect(ACCOUNT_WALL, id);
    }
}
