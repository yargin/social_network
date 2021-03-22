package com.getjavajob.training.yarginy.socialnetwork.web.interceptors.common;

import com.getjavajob.training.yarginy.socialnetwork.web.helpers.Redirector;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Attributes.REQUESTED_ID;

@Component
public class OneIdInterceptor extends HandlerInterceptorAdapter {
    private final Redirector redirector;

    public OneIdInterceptor(Redirector redirector) {
        this.redirector = redirector;
    }

    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse resp, Object handler) throws Exception {
        String stringRequestedId = req.getParameter(REQUESTED_ID);
        long requestedId;
        try {
            requestedId = Long.parseLong(stringRequestedId);
        } catch (NumberFormatException e) {
            redirector.redirectToReferer(req, resp);
            return false;
        }
        if (requestedId < 1) {
            redirector.redirectToReferer(req, resp);
            return false;
        }
        req.setAttribute(REQUESTED_ID, requestedId);
        return true;
    }
}
