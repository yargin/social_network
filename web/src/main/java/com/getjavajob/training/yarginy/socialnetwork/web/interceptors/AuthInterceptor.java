package com.getjavajob.training.yarginy.socialnetwork.web.interceptors;

import com.getjavajob.training.yarginy.socialnetwork.web.servlethelpers.RedirectHelper;
import com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Attributes;
import com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Pages;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static java.util.Objects.isNull;

@Component
public class AuthInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession(true);
        Object userIdObject = session.getAttribute(Attributes.USER_ID);
        if (isNull(userIdObject)) {
            RedirectHelper.redirect(request, response, Pages.LOGIN);
            return false;
        }
        long userId = (long) userIdObject;

        if (userId < 1) {
            RedirectHelper.redirect(request, response, Pages.LOGIN);
            return false;
        }
        return true;
    }
}
