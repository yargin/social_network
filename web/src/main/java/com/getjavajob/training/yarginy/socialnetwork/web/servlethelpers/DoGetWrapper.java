package com.getjavajob.training.yarginy.socialnetwork.web.servlethelpers;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@FunctionalInterface
public interface DoGetWrapper {
    void accept(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException;
}