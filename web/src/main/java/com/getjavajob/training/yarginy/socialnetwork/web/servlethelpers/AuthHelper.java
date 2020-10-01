package com.getjavajob.training.yarginy.socialnetwork.web.servlethelpers;

import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static java.util.Objects.isNull;

public class AuthHelper {
    public static void checkAuth(HttpServletRequest request, HttpServletResponse response, String jsp) throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        Account account = (Account) session.getAttribute("account");
        if (isNull(account)) {
            response.sendRedirect(request.getContextPath() + "/login");
        } else {
            request.getRequestDispatcher(jsp).forward(request, response);
        }
    }
}
