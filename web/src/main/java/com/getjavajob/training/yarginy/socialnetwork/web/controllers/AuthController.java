package com.getjavajob.training.yarginy.socialnetwork.web.controllers;

import com.getjavajob.training.yarginy.socialnetwork.common.exceptions.IncorrectDataException;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.service.AuthService;
import com.getjavajob.training.yarginy.socialnetwork.web.helpers.Redirector;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Attributes.USER;
import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Attributes.USER_ID;
import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Attributes.USER_NAME;
import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Pages.ACCOUNT_WALL;
import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Pages.LOGIN;
import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Pages.REDIRECT;
import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Views.LOGIN_VIEW;
import static java.util.Objects.isNull;

@Controller
public class AuthController {
    private static final String ERROR = "logerror";
    private static final String EMAIL = "email";
    private static final String PASSWORD = "password";
    private final AuthService authService;
    private final Redirector redirector;

    public AuthController(AuthService authService, Redirector redirector) {
        this.authService = authService;
        this.redirector = redirector;
    }

    @RequestMapping("/")
    public String welcomeRedirect() {
        return REDIRECT + ACCOUNT_WALL;
    }

    @GetMapping(LOGIN)
    public String showLogin(HttpServletRequest req, @CookieValue(required = false) String email,
                            @CookieValue(required = false) String password) {
        try {
            Account account = authService.login(email, password);
            assignSessionParameters(req, account);
            return redirector.redirectBackView(req);
        } catch (Exception e) {
            return LOGIN_VIEW;
        }
    }

    private void assignSessionParameters(HttpServletRequest req, Account account) {
        req.getSession().setAttribute(USER, account);
        req.getSession().setAttribute(USER_ID, account.getId());
    }

    @PostMapping(LOGIN)
    public ModelAndView login(@RequestParam String email, @RequestParam String password, HttpServletRequest req,
                              @RequestParam(required = false) String rememberMe, HttpServletResponse resp) {
        Account account;
        ModelAndView modelAndView = new ModelAndView(LOGIN_VIEW);
        try {
            account = authService.login(email, password);
        } catch (IncorrectDataException e) {
            switch (e.getType()) {
                case NOT_AN_EMAIL:
                case WRONG_EMAIL:
                    modelAndView.addObject(ERROR, "error.wrongEmail");
                    break;
                case NOT_A_PASSWORD:
                case WRONG_PASSWORD:
                case PASSWORD_TOO_LONG:
                case PASSWORD_TOO_SHORT:
                    modelAndView.addObject(ERROR, "error.wrongPassword");
                    if (!isNull(email)) {
                        modelAndView.addObject(EMAIL, email);
                    }
                    break;
                default:
                    break;
            }
            return modelAndView;
        }

        if ("on".equals(rememberMe)) {
            Cookie emailCookie = new Cookie(EMAIL, email);
            emailCookie.setMaxAge(2);
            Cookie passwordCookie = new Cookie(PASSWORD, email);
            passwordCookie.setMaxAge(2);
            resp.addCookie(emailCookie);
            resp.addCookie(passwordCookie);
        }

        assignSessionParameters(req, account);
        return new ModelAndView(redirector.redirectBackView(req));
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest req, HttpServletResponse resp, HttpSession session) {
        session.invalidate();

        Cookie[] cookies = req.getCookies();

        if (!isNull(cookies)) {
            for (Cookie cookie : cookies) {
                if (USER_NAME.equals(cookie.getName()) || PASSWORD.equals(cookie.getName())) {
                    cookie.setValue("");
                    cookie.setMaxAge(0);
                    resp.addCookie(cookie);
                }
            }
        }
        return REDIRECT + LOGIN;
    }
}
