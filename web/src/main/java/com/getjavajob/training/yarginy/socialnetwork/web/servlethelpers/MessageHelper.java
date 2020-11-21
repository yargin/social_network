package com.getjavajob.training.yarginy.socialnetwork.web.servlethelpers;

import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.AccountImpl;
import com.getjavajob.training.yarginy.socialnetwork.common.models.message.Message;
import com.getjavajob.training.yarginy.socialnetwork.common.models.message.MessageImpl;
import com.getjavajob.training.yarginy.socialnetwork.service.messages.AccountPrivateMessageServiceImpl;
import com.getjavajob.training.yarginy.socialnetwork.service.messages.AccountWallMessageServiceImpl;
import com.getjavajob.training.yarginy.socialnetwork.service.messages.GroupWallMessageServiceImpl;
import com.getjavajob.training.yarginy.socialnetwork.service.messages.MessageService;
import com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Attributes;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;

import static java.util.Objects.isNull;

public class MessageHelper {
    private static final MessageService ACCOUNT_WALL_MESSAGE_SERVICE = new AccountWallMessageServiceImpl();
    private static final MessageService GROUP_WALL_MESSAGE_SERVICE = new GroupWallMessageServiceImpl();
    private static final MessageService ACCOUNT_PRIVATE_MESSAGE_SERVICE = new AccountPrivateMessageServiceImpl();

    public static void addMessage(HttpServletRequest req) throws IOException, ServletException {
        Message message = new MessageImpl();
        long senderId = (long) req.getAttribute(Attributes.REQUESTER_ID);
        long receiverId = (long) req.getAttribute(Attributes.RECEIVER_ID);
        String text = req.getParameter("text");
        Part part = req.getPart("image");
        if (part.getSize() < 1 && text.trim().isEmpty()) {
            return;
        }
        message.setText(text);
        Account author = new AccountImpl();
        author.setId(senderId);
        if (!isNull(part)) {
            try (InputStream inputStream = part.getInputStream()) {
                message.setImage(inputStream);
            }
        }
        message.setAuthor(author);
        message.setReceiverId(receiverId);

        String type = req.getParameter("type");
        if ("accountWall".equals(type)) {
            ACCOUNT_WALL_MESSAGE_SERVICE.addMessage(message);
        } else if ("accountPrivate".equals(type)) {
            ACCOUNT_PRIVATE_MESSAGE_SERVICE.addMessage(message);
        } else if ("groupWall".equals(type)) {
            GROUP_WALL_MESSAGE_SERVICE.addMessage(message);
        }
    }

    public static void deleteMessage(HttpServletRequest req) {
        Message message = new MessageImpl();
        long messageId = (long) req.getAttribute(Attributes.REQUESTED_ID);
        message.setId(messageId);
        String type = req.getParameter("type");
        if ("accountWall".equals(type)) {
            ACCOUNT_WALL_MESSAGE_SERVICE.deleteMessage(message);
        } else if ("accountPrivate".equals(type)) {
            ACCOUNT_PRIVATE_MESSAGE_SERVICE.deleteMessage(message);
        } else if ("groupWall".equals(type)) {
            GROUP_WALL_MESSAGE_SERVICE.deleteMessage(message);
        }
    }
}
