package com.getjavajob.training.yarginy.socialnetwork.web.staticvalues;

import javax.servlet.http.HttpServlet;

public final class Pages extends HttpServlet {
    public static final String LOGIN = "/login";
    public static final String ACCOUNT_WALL = "/account/wall";
    public static final String LOGOUT = "/logout";
    public static final String REGISTER = "/registration";
    public static final String DELETE_ACCOUNT = "/account/delete";
    public static final String UPDATE_ACCOUNT = "/account/update";
    public static final String GROUP_WALL = "/group/wall";
    public static final String GROUPS = "/account/groups";
    public static final String FRIENDSHIP_REQUESTS = "/account/requests";
    public static final String FRIENDS = "/account/friends";
    public static final String GROUP_REQUESTS = "/group/requests";
    public static final String GROUP_MEMBERS = "/group/members";
    public static final String GROUP_MODERATORS = "/group/moderators";
    public static final String DIALOGS = "/account/dialogs";
    public static final String DIALOG = "/dialog/show";

    private Pages() {
    }
}
