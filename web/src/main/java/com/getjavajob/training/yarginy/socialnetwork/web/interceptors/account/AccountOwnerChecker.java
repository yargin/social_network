package com.getjavajob.training.yarginy.socialnetwork.web.interceptors.account;

import com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Pages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Messages.CHECK_FAILED;
import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Messages.CHECK_PASSED;
import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Messages.TWO_STRINGS_WITH_SPACE;
import static java.util.Objects.isNull;

@Component
public class AccountOwnerChecker extends HandlerInterceptorAdapter {
    private static final Logger logger = LoggerFactory.getLogger(AccountOwnerChecker.class);

    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler) throws Exception {
        if (!isNull(req.getAttribute("admin")) || !isNull(req.getAttribute("owner"))) {
            logger.info(TWO_STRINGS_WITH_SPACE, req.getRequestURI(), CHECK_PASSED);
            return true;
        } else {
            logger.info(TWO_STRINGS_WITH_SPACE, req.getRequestURI(), CHECK_FAILED);
            res.sendRedirect(req.getContextPath() + Pages.ACCOUNT_WALL);
        }
        return false;
    }
}
