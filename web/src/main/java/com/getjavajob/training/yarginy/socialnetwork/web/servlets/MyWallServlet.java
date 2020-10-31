package com.getjavajob.training.yarginy.socialnetwork.web.servlets;

import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.additionaldata.Role;
import com.getjavajob.training.yarginy.socialnetwork.common.models.accountphoto.AccountPhoto;
import com.getjavajob.training.yarginy.socialnetwork.common.models.phone.Phone;
import com.getjavajob.training.yarginy.socialnetwork.common.models.phone.additionaldata.PhoneType;
import com.getjavajob.training.yarginy.socialnetwork.service.AccountService;
import com.getjavajob.training.yarginy.socialnetwork.service.AccountServiceImpl;
import com.getjavajob.training.yarginy.socialnetwork.service.dto.AccountInfoDTO;
import com.getjavajob.training.yarginy.socialnetwork.web.servlethelpers.RedirectHelper;
import com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Attributes;
import com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Jsps;
import com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Pages;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Base64;
import java.util.Collection;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

public class MyWallServlet extends HttpServlet {
    private static final AccountService ACCOUNT_SERVICE = new AccountServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        long sessionUserId = (long) session.getAttribute(Attributes.USER_ID);
        String stringRequestedId = req.getParameter(Attributes.USER_ID);
        if (isNull(stringRequestedId) || stringRequestedId.isEmpty()) {
            RedirectHelper.redirect(req, resp, Pages.MY_WALL, Attributes.USER_ID, "" + sessionUserId);
            return;
        }

        long requestedUserId = Long.parseLong(stringRequestedId);
        AccountInfoDTO accountInfoDTO = ACCOUNT_SERVICE.getAccountInfo(requestedUserId);

        Account account = accountInfoDTO.getAccount();
        req.setAttribute("user", account);

        Collection<Phone> phones = accountInfoDTO.getPhones();
        Collection<Phone> privatePhones = phones.stream().filter(phone -> phone.getType() == PhoneType.PRIVATE).
                collect(Collectors.toList());
        req.setAttribute("privatePhones", privatePhones);
        Collection<Phone> workPhones = phones.stream().filter(phone -> phone.getType() == PhoneType.WORK).
                collect(Collectors.toList());
        req.setAttribute("workPhones", workPhones);

        AccountPhoto accountPhoto =  accountInfoDTO.getAccountPhoto();
        if (!isNull(accountPhoto) && !isNull(accountPhoto.getPhoto())) {
            String base64Image = Base64.getEncoder().encodeToString(accountPhoto.getPhoto());
            req.setAttribute("photo", base64Image);
        }

        if (sessionUserId != requestedUserId) {
            Role sessionRole = (Role) session.getAttribute(Attributes.USER_ROLE);
            if (!isNull(sessionRole) && (Role.ADMINISTRATOR.equals(sessionRole))) {
                req.setAttribute("updateAble", true);
            }
        } else {
            req.setAttribute("updateAble", true);
        }

        req.setAttribute("accountInfo", accountInfoDTO);
        req.getRequestDispatcher(Jsps.MY_WALL).forward(req, resp);
    }
}
