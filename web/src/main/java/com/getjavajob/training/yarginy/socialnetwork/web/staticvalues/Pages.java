package com.getjavajob.training.yarginy.socialnetwork.web.staticvalues;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

public final class Pages extends HttpServlet {
    public static final String LOGIN = "/login";
    public static String MY_WALL = "/mywall";
    public static String LOGOUT = "/logout";
    public static String REGISTER = "/registration";
    public static String DELETE_ACCOUNT = "/deleteAccount";
    public static String UPDATE_ACCOUNT = "/updateInfo";

    private Pages() {
    }

    private static class ParamInitializer extends HttpServlet {
        static String login;

        @Override
        public void init() throws ServletException {
            ServletContext context = getServletContext();
            login = context.getContextPath() + context.getInitParameter("loginPage");
        }
    }
}
