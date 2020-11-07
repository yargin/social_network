package com.getjavajob.training.yarginy.socialnetwork.web.staticvalues;

import javax.servlet.http.HttpServlet;

public final class Pages extends HttpServlet {
    public static final String LOGIN = "/login";
    public static final String MY_WALL = "/mywall";
    public static final String LOGOUT = "/logout";
    public static final String REGISTER = "/registration";
    public static final String DELETE_ACCOUNT = "/deleteAccount";
    public static final String UPDATE_ACCOUNT = "/updateInfo";
    public static final String GROUP = "/group";
    public static final String GROUPS = "/groups";
    public static final String FRIENDSHIP_REQUESTS = "/friendsRequests";

    private Pages() {
    }
}
