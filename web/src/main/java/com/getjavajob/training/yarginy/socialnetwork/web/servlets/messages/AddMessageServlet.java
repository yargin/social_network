package com.getjavajob.training.yarginy.socialnetwork.web.servlets.messages;

import com.getjavajob.training.yarginy.socialnetwork.web.servlethelpers.MessageHelper;
import com.getjavajob.training.yarginy.socialnetwork.web.servlets.AbstractPostServlet;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.getjavajob.training.yarginy.socialnetwork.web.servlethelpers.RedirectHelper.redirectToReferer;

public class AddMessageServlet extends AbstractPostServlet {
    private MessageHelper messageHelper;

    @Autowired
    public void setMessageHelper(MessageHelper messageHelper) {
        this.messageHelper = messageHelper;
    }

    @Override
    protected void safeDoPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        messageHelper.addMessage(req);
        redirectToReferer(req, resp);
    }
}
