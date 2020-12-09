package com.getjavajob.training.yarginy.socialnetwork.web.servlets;

import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.http.HttpServlet;

public abstract class AbstractServlet extends HttpServlet {
    @Override
    public void init() {
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, getServletContext());
    }
}
