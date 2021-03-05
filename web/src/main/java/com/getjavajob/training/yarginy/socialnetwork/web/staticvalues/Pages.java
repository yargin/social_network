package com.getjavajob.training.yarginy.socialnetwork.web.staticvalues;

import javax.servlet.http.HttpServlet;

public final class Pages extends HttpServlet {
    public static final String LOGIN = "/login";
    public static final String WALL = "/account/wall";
    public static final String LOGOUT = "/logout";
    public static final String REGISTER = "/registration";
    public static final String DELETE_ACCOUNT = "/deleteAccount";
    public static final String UPDATE_ACCOUNT = "/updateinfo";
    public static final String GROUP = "/group";
    public static final String GROUPS = "/account/groups";
    public static final String FRIENDSHIP_REQUESTS = "/account/requests";
    public static final String FRIENDS = "/account/friends";
    public static final String GROUP_REQUESTS = "/grouprequests";
    public static final String GROUP_MEMBERS = "/group/members/show";
    public static final String DIALOGS = "/account/dialogs";
    public static final String DIALOG = "/dialog/show";

    private Pages() {
    }
}
