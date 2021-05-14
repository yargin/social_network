package com.getjavajob.training.yarginy.socialnetwork.web.interceptors.common;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Account;
import com.getjavajob.training.yarginy.socialnetwork.service.AccountService;
import com.getjavajob.training.yarginy.socialnetwork.web.helpers.Redirector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Attributes.USER;
import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Attributes.USER_ID;
import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Messages.CHECK_PASSED;
import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Pages.LOGIN;
import static java.util.Objects.isNull;

@Component
public class SessionAuthChecker extends HandlerInterceptorAdapter {
    private static final Logger logger = LoggerFactory.getLogger(SessionAuthChecker.class);
    private final Redirector redirector;
    private final AccountService accountService;

    public SessionAuthChecker(Redirector redirector, AccountService accountService) {
        this.redirector = redirector;
        this.accountService = accountService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession(true);
        Object userIdObject = session.getAttribute(USER_ID);
        if (isNull(userIdObject)) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (isNull(authentication)) {
                redirector.redirect(request, response, LOGIN);
                return false;
            }
            Object principal = authentication.getPrincipal();
            String username;
            if (principal instanceof UserDetails) {
                username = ((UserDetails) principal).getUsername();
            } else {
                username = principal.toString();
            }
            Account account = new Account();
            account.setEmail(username);
            account = accountService.get(account);
            session.setAttribute(USER, account);
            session.setAttribute(USER_ID, account.getId());
            logger.info("account info set successfully from security context to session");
        } else {
            logger.info(CHECK_PASSED);
        }
        return true;
    }
}
