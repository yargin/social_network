package com.getjavajob.training.yarginy.socialnetwork.web.interceptors.common;

import com.getjavajob.training.yarginy.socialnetwork.web.helpers.Redirector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Attributes.RECEIVER_ID;
import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Attributes.REQUESTER_ID;
import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Messages.TWO_STRINGS_WITH_SPACE;

@Component
public class TwoIdsInterceptor extends HandlerInterceptorAdapter {
    private static final String WRONG_IDS = "parsing ids({}, {}) failed. Redirect back";
    private static final Logger logger = LoggerFactory.getLogger(TwoIdsInterceptor.class);
    private final Redirector redirector;

    public TwoIdsInterceptor(Redirector redirector) {
        this.redirector = redirector;
    }

    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse resp, Object handler) throws Exception {
        String stringFirstRequestedId = req.getParameter(REQUESTER_ID);
        String stringSecondRequestedId = req.getParameter(RECEIVER_ID);
        long firstRequestedId;
        long secondRequestedId;
        try {
            firstRequestedId = Long.parseLong(stringFirstRequestedId);
            secondRequestedId = Long.parseLong(stringSecondRequestedId);
        } catch (NumberFormatException e) {
            logger.info(WRONG_IDS, stringFirstRequestedId, stringSecondRequestedId);
            redirector.redirectToReferer(req, resp);
            return false;
        }
        if (firstRequestedId < 1 || secondRequestedId < 1) {
            logger.info(WRONG_IDS, stringFirstRequestedId, stringSecondRequestedId);
            redirector.redirectToReferer(req, resp);
            return false;
        }
        req.setAttribute(REQUESTER_ID, firstRequestedId);
        req.setAttribute(RECEIVER_ID, secondRequestedId);
        logger.info(TWO_STRINGS_WITH_SPACE, req.getRequestURI(), "parsed ids number successfully");
        return true;
    }
}
