package com.getjavajob.training.yarginy.socialnetwork.web.servlethelpers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static java.util.Objects.isNull;

public class RedirectHelper {
    public static void redirect(HttpServletRequest request, HttpServletResponse response, String location) {
        response.setHeader("Location", location);
        response.setStatus(HttpServletResponse.SC_FOUND);
    }

    public static void redirectToReferer(HttpServletRequest request, HttpServletResponse response) {
        String referer = request.getHeader("Referer");
        String url = request.getRequestURL().toString();
        if (isNull(referer) || referer.equals(url)) {
            referer = request.getContextPath();
        }
        redirect(request, response, referer);
    }
}
