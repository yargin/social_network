package com.getjavajob.training.yarginy.socialnetwork.web.servlets.messages;

import com.getjavajob.training.yarginy.socialnetwork.web.servlethelpers.MessageHelper;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.getjavajob.training.yarginy.socialnetwork.web.servlethelpers.RedirectHelper.redirectToReferer;

public class DeleteMessageServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        MessageHelper.deleteMessage(req);
        redirectToReferer(req, resp);
    }
}
