package com.getjavajob.training.yarginy.socialnetwork.web.interceptors.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Attributes.REQUESTED_ID;
import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Attributes.USER_ID;
import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Messages.TWO_STRINGS_WITH_SPACE;
import static java.util.Objects.isNull;

@Component
public class IdSetter extends HandlerInterceptorAdapter {
    private static final Logger logger = LoggerFactory.getLogger(IdSetter.class);

    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse resp, Object handler) throws Exception {
        if (!isNull(req.getParameter(REQUESTED_ID))) {
            return true;
        }
        long requestedId = (long) req.getSession().getAttribute(USER_ID);
        req.setAttribute(REQUESTED_ID, requestedId);
        logger.info(TWO_STRINGS_WITH_SPACE, req.getRequestURI(), "id was set from session");
        return true;
    }
}
