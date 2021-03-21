package com.getjavajob.training.yarginy.socialnetwork.web.interceptors.common;

import com.getjavajob.training.yarginy.socialnetwork.web.helpers.Redirector;
import com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Pages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Attributes.USER_ID;
import static java.util.Objects.isNull;

@Component
public class AuthChecker extends HandlerInterceptorAdapter {
    private final Redirector redirector;

    @Autowired
    public AuthChecker(Redirector redirector) {
        this.redirector = redirector;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession(true);
        Object userIdObject = session.getAttribute(USER_ID);
        if (isNull(userIdObject)) {
            redirector.redirect(request, response, Pages.LOGIN);
            return false;
        }
        long userId = (long) userIdObject;

        if (userId < 1) {
            redirector.redirect(request, response, Pages.LOGIN);
            return false;
        }
        return true;
    }
}
