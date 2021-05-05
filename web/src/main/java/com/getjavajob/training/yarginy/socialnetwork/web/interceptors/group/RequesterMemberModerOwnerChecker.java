package com.getjavajob.training.yarginy.socialnetwork.web.interceptors.group;

import com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Pages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Messages.CHECK_FAILED;
import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Messages.CHECK_PASSED;
import static java.util.Objects.isNull;

@Component
public class RequesterMemberModerOwnerChecker extends HandlerInterceptorAdapter {
    private static final Logger logger = LoggerFactory.getLogger(RequesterMemberModerOwnerChecker.class);

    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler) throws Exception {
        boolean isMemberRequester = !isNull(req.getAttribute("member")) && !isNull(req.getAttribute("requestOwner"));
        if (!isNull(req.getAttribute("admin")) || !isNull(req.getAttribute("moderator")) ||
                !isNull(req.getAttribute("owner")) || isMemberRequester) {
            logger.info(CHECK_PASSED);
            return true;
        }
        res.sendRedirect(req.getContextPath() + Pages.ACCOUNT_WALL);
        logger.info(CHECK_FAILED);
        return false;
    }
}
