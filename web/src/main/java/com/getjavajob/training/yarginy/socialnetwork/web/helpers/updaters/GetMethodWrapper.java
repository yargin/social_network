package com.getjavajob.training.yarginy.socialnetwork.web.helpers.updaters;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public interface GetMethodWrapper {
    String performGet(HttpServletRequest req, HttpSession session);
}
