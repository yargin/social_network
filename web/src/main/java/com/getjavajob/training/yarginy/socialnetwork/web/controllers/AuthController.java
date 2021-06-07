package com.getjavajob.training.yarginy.socialnetwork.web.controllers;

import com.getjavajob.training.yarginy.socialnetwork.web.helpers.Redirector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.nio.file.Paths;

import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Pages.ACCOUNT_WALL;
import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Pages.LOGIN;
import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Pages.REDIRECT;
import static java.util.Objects.isNull;

@Controller
public class AuthController {
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
    private final Redirector redirector;

    public AuthController(Redirector redirector) {
        this.redirector = redirector;
    }

    @RequestMapping("/")
    public String welcomeRedirect() {
//        return "hello";
        return REDIRECT + ACCOUNT_WALL;
    }

    @GetMapping(LOGIN)
    public String showLogin(HttpServletRequest req) {
        logger.warn("\nWOKING DIRECTOY: " + Paths.get("").toAbsolutePath() + "\n");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!isNull(authentication) && authentication.isAuthenticated()) {
            logger.warn("\nAUTHENTICATED - REDIRECTING BACK FROM LOGIN CONTROLLER\n");
            return redirector.redirectBackView(req);
        } else {
            logger.warn("\nMOVING TO LOGIN PAGE\n");
            return "login";
        }
    }

    @PostMapping(LOGIN)
    public String login(HttpServletRequest req) {
        return showLogin(req);
    }
}
