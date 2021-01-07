package com.getjavajob.training.yarginy.socialnetwork.web.servlets;

import com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Jsps;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public abstract class AbstractPostServlet extends AbstractServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            safeDoPost(req, resp);
        } catch (ServletException | IOException e) {
            e.printStackTrace();
            req.getRequestDispatcher(Jsps.ERROR).forward(req, resp);
        }
    }

    protected abstract void safeDoPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
            IOException;
}
