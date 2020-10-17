package com.getjavajob.training.yarginy.socialnetwork.web.servlethelpers;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static java.util.Objects.isNull;

public class AuthHelper {
    public static boolean checkAuth(HttpServletRequest request, HttpServletResponse response, String jsp) throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        String userName = (String) session.getAttribute("userName");
        if (isNull(userName) || userName.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/login");
            return false;
        } else {
//            request.getRequestDispatcher(jsp).forward(request, response);
            RedirectHelper.redirectToReferer(request, response);
            return true;
        }
    }
}
