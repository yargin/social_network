package com.getjavajob.training.yarginy.socialnetwork.web.interceptors.common;

import com.getjavajob.training.yarginy.socialnetwork.web.helpers.Redirector;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Attributes.RECEIVER_ID;
import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Attributes.REQUESTER_ID;

@Component
public class TwoIdsInterceptor extends HandlerInterceptorAdapter {
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
            redirector.redirectToReferer(req, resp);
            return false;
        }
        if (firstRequestedId < 1 || secondRequestedId < 1) {
            redirector.redirectToReferer(req, resp);
            return false;
        }
        req.setAttribute(REQUESTER_ID, firstRequestedId);
        req.setAttribute(RECEIVER_ID, secondRequestedId);
        return true;
    }
}