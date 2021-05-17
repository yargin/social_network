package com.getjavajob.training.yarginy.socialnetwork.web.interceptors.common;

import com.getjavajob.training.yarginy.socialnetwork.web.helpers.Redirector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Attributes.REQUESTED_ID;
import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Messages.TWO_STRINGS_WITH_SPACE;
import static java.lang.Long.parseLong;
import static java.util.Objects.isNull;

@Component
public class OneIdInterceptor extends HandlerInterceptorAdapter {
    private static final String WRONG_ID = "{} wrong id '{}' number. Redirect back";
    private static final Logger logger = LoggerFactory.getLogger(OneIdInterceptor.class);
    private final Redirector redirector;

    public OneIdInterceptor(Redirector redirector) {
        this.redirector = redirector;
    }

    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse resp, Object handler) throws Exception {
        String stringRequestedId = req.getParameter(REQUESTED_ID);
        long requestedId;
        if (isNull(stringRequestedId)) {
            requestedId = (long) req.getAttribute(REQUESTED_ID);
        } else {
            try {
                requestedId = parseLong(stringRequestedId);
            } catch (NumberFormatException e) {
                logger.info(WRONG_ID, req.getRequestURI(), stringRequestedId);
                redirector.redirectToReferer(req, resp);
                return false;
            }
        }
        if (requestedId < 1) {
            redirector.redirectToReferer(req, resp);
            logger.info(WRONG_ID, req.getRequestURI(), stringRequestedId);
            return false;
        }
        req.setAttribute(REQUESTED_ID, requestedId);
        logger.info(TWO_STRINGS_WITH_SPACE, req.getRequestURI(), "parsed id number successfully");
        return true;
    }
}
