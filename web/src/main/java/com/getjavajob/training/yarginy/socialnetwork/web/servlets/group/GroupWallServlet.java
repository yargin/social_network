package com.getjavajob.training.yarginy.socialnetwork.web.servlets.group;

import com.getjavajob.training.yarginy.socialnetwork.common.models.group.Group;
import com.getjavajob.training.yarginy.socialnetwork.common.models.message.Message;
import com.getjavajob.training.yarginy.socialnetwork.service.GroupService;
import com.getjavajob.training.yarginy.socialnetwork.service.GroupServiceImpl;
import com.getjavajob.training.yarginy.socialnetwork.service.messages.GroupWallMessageServiceImpl;
import com.getjavajob.training.yarginy.socialnetwork.service.messages.MessageService;
import com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Attributes;
import com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Jsps;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

public class GroupWallServlet extends HttpServlet {
    private final GroupService groupService = new GroupServiceImpl();
    private final MessageService groupWallMessageService = new GroupWallMessageServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long requestedId = (long) req.getAttribute(Attributes.REQUESTED_ID);
        Group group = groupService.get(requestedId);
        Collection<Message> messages = groupWallMessageService.selectMessages(requestedId);
        req.setAttribute("messages", messages);
        req.setAttribute("group", group);
        req.setAttribute("type", "groupWall");
        req.setAttribute("id", requestedId);
        req.setAttribute("tab", "wall");
        req.getRequestDispatcher(Jsps.GROUP_JSP).forward(req, resp);
    }
}
