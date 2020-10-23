package com.getjavajob.training.yarginy.socialnetwork.web.servlets;

import com.getjavajob.training.yarginy.socialnetwork.common.exceptions.IncorrectDataException;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.accountphoto.AccountPhoto;
import com.getjavajob.training.yarginy.socialnetwork.common.models.phone.Phone;
import com.getjavajob.training.yarginy.socialnetwork.common.models.phone.additionaldata.PhoneType;
import com.getjavajob.training.yarginy.socialnetwork.service.AccountInfoService;
import com.getjavajob.training.yarginy.socialnetwork.service.AccountInfoServiceImpl;
import com.getjavajob.training.yarginy.socialnetwork.service.AccountService;
import com.getjavajob.training.yarginy.socialnetwork.service.AccountServiceImpl;
import com.getjavajob.training.yarginy.socialnetwork.service.dto.AccountInfoDTO;
import com.getjavajob.training.yarginy.socialnetwork.web.servlets.additionaldata.PhoneExchanger;
import com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Attributes;
import com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Jsps;
import com.sun.deploy.security.ValidationState;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collection;
import java.util.stream.Collectors;

import static com.getjavajob.training.yarginy.socialnetwork.web.servlethelpers.UpdateFieldHelper.initFields;
import static java.util.Objects.isNull;

public class UpdateAccountServlet extends HttpServlet {
    private static final String PRIVATE_PHONES_ATTRIBUTE = "privatePhones";
    private static final String WORK_PHONES_ATTRIBUTE = "workPhones";
    private static final String PHOTO_ATTRIBUTE = "photo";
    private final AccountService accountService = new AccountServiceImpl();
    private final AccountInfoService accountInfoService = new AccountInfoServiceImpl();
    private final ThreadLocal<Boolean> paramsAccepted = new ThreadLocal<>();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long id = (long) req.getSession().getAttribute(Attributes.USER_ID);

        AccountInfoDTO accountInfo = (AccountInfoDTO) req.getAttribute("accountInfo");

        if (isNull(accountInfo)) {
            accountInfo = accountInfoService.select(id);
        }
        initFields(req, accountInfo);

        Account account = accountInfo.getAccount();

        req.setAttribute("name", account.getName());
        req.setAttribute("surname", account.getSurname());
        req.setAttribute("patronymic", account.getPatronymic());
        req.setAttribute("sex", account.getSex());
        req.setAttribute("email", account.getEmail());
        req.setAttribute("additionalEmail", account.getAdditionalEmail());
        req.setAttribute("birthDate", Date.valueOf(account.getBirthDate()));
        req.setAttribute("icq", account.getId());
        req.setAttribute("skype", account.getSkype());
        req.setAttribute("country", account.getCountry());
        req.setAttribute("city", account.getCity());

        AccountPhoto accountPhoto = accountInfo.getAccountPhoto();
        if (!isNull(accountPhoto)) {
            String photo = Base64.getEncoder().encodeToString(accountPhoto.getPhoto());
            req.setAttribute(PHOTO_ATTRIBUTE, photo);
        }

        req.getRequestDispatcher(Jsps.UPDATE_ACCOUNT).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}
