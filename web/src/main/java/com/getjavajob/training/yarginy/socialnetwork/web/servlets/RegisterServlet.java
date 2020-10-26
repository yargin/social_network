package com.getjavajob.training.yarginy.socialnetwork.web.servlets;

import com.getjavajob.training.yarginy.socialnetwork.common.exceptions.IncorrectData;
import com.getjavajob.training.yarginy.socialnetwork.common.exceptions.IncorrectDataException;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.accountphoto.AccountPhoto;
import com.getjavajob.training.yarginy.socialnetwork.common.models.password.Password;
import com.getjavajob.training.yarginy.socialnetwork.common.models.phone.Phone;
import com.getjavajob.training.yarginy.socialnetwork.common.models.phone.additionaldata.PhoneType;
import com.getjavajob.training.yarginy.socialnetwork.service.AuthService;
import com.getjavajob.training.yarginy.socialnetwork.service.AuthServiceImpl;
import com.getjavajob.training.yarginy.socialnetwork.service.dto.AccountInfoDTO;
import com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Attributes;
import com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Jsps;
import com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Pages;
import com.getjavajob.training.yarginy.socialnetwork.web.servlets.additionaldata.PhoneExchanger;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.Collection;
import java.util.function.Supplier;

import static com.getjavajob.training.yarginy.socialnetwork.web.servlethelpers.RedirectHelper.redirect;
import static com.getjavajob.training.yarginy.socialnetwork.web.servlethelpers.UpdateFieldHelper.*;
import static java.util.Objects.isNull;

public class RegisterServlet extends HttpServlet {
    private static final String EMAIL_DUPLICATE = "emailDuplicate";
    private static final String PHONE_DUPLICATE = "phoneDuplicate";
    private static final String REG_SUCCESS_URL = Pages.MY_WALL;
    private static final AuthService AUTH_SERVICE = new AuthServiceImpl();
    private final ThreadLocal<Boolean> paramsAccepted = new ThreadLocal<>();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        String userName = (String) session.getAttribute(Attributes.USER_NAME);
        if (!isNull(userName)) {
            resp.sendRedirect(req.getContextPath() + REG_SUCCESS_URL);
            return;
        }

        AccountInfoDTO accountInfoDTO = accountInfoDTOInit(req, AccountInfoDTO::new);
        initAccountAttributes(req, accountInfoDTO);

        req.setAttribute(Attributes.TARGET, Pages.REGISTER);

        req.getRequestDispatcher(Jsps.REGISTER).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        paramsAccepted.set(true);

        AccountInfoDTO accountInfoDTO = (AccountInfoDTO) req.getSession().getAttribute(Attributes.ACCOUNT_INFO);
        if (isNull(accountInfoDTO)) {
            redirect(req, resp, Pages.LOGOUT);
            return;
        }

        getValuesFromParams(req, accountInfoDTO, paramsAccepted);

        Password password = getPassword(req, accountInfoDTO.getAccount(), paramsAccepted);

        if (!paramsAccepted.get()) {
            doGet(req, resp);
        } else {
            register(req, resp, accountInfoDTO, password);
        }
    }

    private void register(HttpServletRequest req, HttpServletResponse resp, AccountInfoDTO accountInfoDTO, Password
            password) throws IOException, ServletException {
        boolean registered;
        try {
            registered = AUTH_SERVICE.register(accountInfoDTO, password);
        } catch (IncorrectDataException e) {
            if (e.getType() == IncorrectData.EMAIL_DUPLICATE) {
                req.setAttribute(EMAIL_DUPLICATE, e.getType().getPropertyKey());
            } else if (e.getType() == IncorrectData.PHONE_DUPLICATE) {
                req.setAttribute(PHONE_DUPLICATE, e.getType().getPropertyKey());
            }
            doGet(req, resp);
            return;
        }
        acceptActionOrRetry(req, resp, registered, REG_SUCCESS_URL, (req1, resp1) -> doGet(req1, resp1));
    }
}
