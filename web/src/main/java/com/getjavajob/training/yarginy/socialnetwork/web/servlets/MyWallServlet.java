package com.getjavajob.training.yarginy.socialnetwork.web.servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.getjavajob.training.yarginy.socialnetwork.web.servlethelpers.AuthHelper.checkAuth;

public class MyWallServlet extends HttpServlet {
    private static final String JSP = "/WEB-INF/jsps/mywall.jsp";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //todo service.get messages
        checkAuth(req, resp);
        req.getRequestDispatcher(JSP).forward(req, resp);
    }
}
