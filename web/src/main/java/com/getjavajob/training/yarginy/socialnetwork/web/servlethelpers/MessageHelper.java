package com.getjavajob.training.yarginy.socialnetwork.web.servlethelpers;

import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.AccountImpl;
import com.getjavajob.training.yarginy.socialnetwork.common.models.message.Message;
import com.getjavajob.training.yarginy.socialnetwork.common.models.message.MessageImpl;
import com.getjavajob.training.yarginy.socialnetwork.service.messages.DialogMessageServiceImpl;
import com.getjavajob.training.yarginy.socialnetwork.service.messages.GroupWallMessageServiceImpl;
import com.getjavajob.training.yarginy.socialnetwork.service.messages.MessageService;
import com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Attributes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;

import static java.util.Objects.isNull;

@Component
public final class MessageHelper {
    private final MessageService accountWallMessageService;
    private static final MessageService GROUP_WALL_MESSAGE_SERVICE = new GroupWallMessageServiceImpl();
    private static final MessageService ACCOUNT_PRIVATE_MESSAGE_SERVICE = new DialogMessageServiceImpl();

    @Autowired
    public MessageHelper(@Qualifier("accountWallMessageService") MessageService accountWallMessageService) {
        this.accountWallMessageService = accountWallMessageService;
    }

    public boolean addMessage(HttpServletRequest req) throws IOException, ServletException {
        Message message = getMessageFromRequest(req);

        String text = message.getText();
        if ((isNull(text) || text.trim().isEmpty()) && isNull(message.getImage())) {
            return false;
        }

        long receiverId = (long) req.getAttribute(Attributes.RECEIVER_ID);
        message.setReceiverId(receiverId);

        String type = req.getParameter("type");
        if ("accountWall".equals(type)) {
            return accountWallMessageService.addMessage(message);
        } else if ("accountPrivate".equals(type)) {
            return ACCOUNT_PRIVATE_MESSAGE_SERVICE.addMessage(message);
        } else if ("groupWall".equals(type)) {
            return GROUP_WALL_MESSAGE_SERVICE.addMessage(message);
        }
        throw new IllegalArgumentException("message type not specified");
    }

    public Message getMessageFromRequest(HttpServletRequest req) throws IOException, ServletException {
        Message message = new MessageImpl();
        String text = req.getParameter("text");
        message.setText(text);
        Part part = req.getPart("image");
        if (part.getSize() > 1) {
            try (InputStream inputStream = part.getInputStream()) {
                message.setImage(inputStream);
            }
        }
        long senderId = (long) req.getAttribute(Attributes.REQUESTER_ID);
        Account author = new AccountImpl();
        author.setId(senderId);
        message.setAuthor(author);
        return message;
    }

    public void deleteMessage(HttpServletRequest req) {
        Message message = new MessageImpl();
        long messageId = (long) req.getAttribute(Attributes.REQUESTED_ID);
        message.setId(messageId);
        String type = req.getParameter("type");
        if ("accountWall".equals(type)) {
            accountWallMessageService.deleteMessage(message);
        } else if ("accountPrivate".equals(type)) {
            ACCOUNT_PRIVATE_MESSAGE_SERVICE.deleteMessage(message);
        } else if ("groupWall".equals(type)) {
            GROUP_WALL_MESSAGE_SERVICE.deleteMessage(message);
        }
    }
}
