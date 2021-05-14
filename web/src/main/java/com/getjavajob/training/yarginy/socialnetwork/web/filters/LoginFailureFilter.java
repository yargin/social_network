package com.getjavajob.training.yarginy.socialnetwork.web.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Pages.LOGIN;

public class LoginFailureFilter implements AuthenticationFailureHandler {
    private static final Logger logger = LoggerFactory.getLogger(LoginFailureFilter.class);

    @Override
    public void onAuthenticationFailure(HttpServletRequest req, HttpServletResponse resp, AuthenticationException e)
            throws IOException, ServletException {
        String email = req.getParameter("email");
        if (e instanceof UsernameNotFoundException) {
            req.setAttribute("logerror", "error.wrongEmail");
            logger.info("authentication failed - user not found");
        } else if (e instanceof BadCredentialsException) {
            req.setAttribute("logerror", "error.wrongPassword");
            logger.info("authentication failed - wrong password");
        }
        req.setAttribute("email", email);
        req.getRequestDispatcher(LOGIN).forward(req, resp);
    }
}
