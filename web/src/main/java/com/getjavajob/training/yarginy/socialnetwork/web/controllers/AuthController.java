package com.getjavajob.training.yarginy.socialnetwork.web.controllers;

import com.getjavajob.training.yarginy.socialnetwork.common.exceptions.IncorrectDataException;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static com.getjavajob.training.yarginy.socialnetwork.common.exceptions.IncorrectData.WRONG_EMAIL;
import static com.getjavajob.training.yarginy.socialnetwork.common.exceptions.IncorrectData.WRONG_PASSWORD;
import static com.getjavajob.training.yarginy.socialnetwork.web.servlethelpers.RedirectHelper.redirectBackView;
import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Attributes.*;
import static java.util.Objects.isNull;

@Controller
public class AuthController {
    private static final String ERROR = "logerror";
    private static final String EMAIL = "email";
    private static final String PASSWORD = "password";
    private static final String LOGIN = "login";
    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @RequestMapping("/")
    public String welcomeRedirect() {
        return "redirect:/account/wall";
    }

    @GetMapping("/login")
    public String showLogin(HttpServletRequest req, @CookieValue(required = false) String email,
                            @CookieValue(required = false) String password) {
        try {
            Account account = authService.login(email, password);
            assignSessionParameters(req, account);
            return redirectBackView(req);
        } catch (Exception e) {
            return LOGIN;
        }
    }

    private void assignSessionParameters(HttpServletRequest req, Account account) {
        req.getSession().setAttribute(USER, account);
        req.getSession().setAttribute(USER_ID, account.getId());
    }

    @PostMapping("/login")
    public ModelAndView login(@RequestParam String email, @RequestParam String password, HttpServletRequest req,
                              @RequestParam(required = false) String rememberMe, HttpServletResponse resp) {
        Account account;
        ModelAndView modelAndView = new ModelAndView(LOGIN);
        try {
            account = authService.login(email, password);
        } catch (IncorrectDataException e) {
            switch (e.getType()) {
                case NOT_AN_EMAIL:
                case WRONG_EMAIL:
                    modelAndView.addObject(ERROR, WRONG_EMAIL.getPropertyKey());
                    break;
                case NOT_A_PASSWORD:
                case WRONG_PASSWORD:
                case PASSWORD_TOO_LONG:
                case PASSWORD_TOO_SHORT:
                    modelAndView.addObject(ERROR, WRONG_PASSWORD.getPropertyKey());
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
        return new ModelAndView(redirectBackView(req));
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession();
        session.removeAttribute(ACCOUNT_INFO);
        session.removeAttribute(PRIVATE_PHONES);
        session.removeAttribute(WORK_PHONES);
        session.removeAttribute(PHOTO);
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
        return LOGIN;
    }
}
