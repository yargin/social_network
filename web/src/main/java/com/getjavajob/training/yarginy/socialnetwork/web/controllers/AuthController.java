package com.getjavajob.training.yarginy.socialnetwork.web.controllers;

import com.getjavajob.training.yarginy.socialnetwork.web.helpers.Redirector;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Pages.ACCOUNT_WALL;
import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Pages.LOGIN;
import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Pages.REDIRECT;
import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Views.LOGIN_VIEW;
import static java.util.Objects.isNull;

@Controller
public class AuthController {
    private final Redirector redirector;

    public AuthController(Redirector redirector) {
        this.redirector = redirector;
    }

    @RequestMapping("/")
    public String welcomeRedirect() {
        return REDIRECT + ACCOUNT_WALL;
    }

    @GetMapping(LOGIN)
    public String showLogin(HttpServletRequest req) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!isNull(authentication) && authentication.isAuthenticated()) {
            return redirector.redirectBackView(req);
        } else {
            return LOGIN_VIEW;
        }
    }
}
