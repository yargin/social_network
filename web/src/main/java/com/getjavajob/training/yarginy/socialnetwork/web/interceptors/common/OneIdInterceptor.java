package com.getjavajob.training.yarginy.socialnetwork.web.interceptors.common;

import com.getjavajob.training.yarginy.socialnetwork.web.helpers.RedirectHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Attributes.REQUESTED_ID;

@Component
public class OneIdInterceptor extends HandlerInterceptorAdapter {
    private final RedirectHelper redirectHelper;

    @Autowired
    public OneIdInterceptor(RedirectHelper redirectHelper) {
        this.redirectHelper = redirectHelper;
    }

    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse resp, Object handler) throws Exception {
        String stringRequestedId = req.getParameter(REQUESTED_ID);
        long requestedId;
        try {
            requestedId = Long.parseLong(stringRequestedId);
        } catch (NumberFormatException e) {
            redirectHelper.redirectToReferer(req, resp);
            return false;
        }
        if (requestedId < 1) {
            redirectHelper.redirectToReferer(req, resp);
            return false;
        }
        req.setAttribute(REQUESTED_ID, requestedId);
        return true;
    }
}
