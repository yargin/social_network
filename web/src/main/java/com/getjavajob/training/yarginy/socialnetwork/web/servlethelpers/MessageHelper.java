package com.getjavajob.training.yarginy.socialnetwork.web.servlethelpers;

import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.message.Message;
import com.getjavajob.training.yarginy.socialnetwork.common.utils.DataHandler;
import com.getjavajob.training.yarginy.socialnetwork.service.messages.MessageService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;

import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Attributes.*;
import static java.util.Objects.isNull;

@Component
public final class MessageHelper {
    private final MessageService accountWallMessageService;
    private final MessageService groupWallMessageService;
    private final MessageService accountPrivateMessageService;
    private final DataHandler dataHandler;

    public MessageHelper(@Qualifier("accountWallMessageService") MessageService accountWallMessageService,
                         @Qualifier("groupWallMessageService") MessageService groupWallMessageService,
                         @Qualifier("dialogMessageService") MessageService accountPrivateMessageService,
                         DataHandler dataHandler) {
        this.accountWallMessageService = accountWallMessageService;
        this.groupWallMessageService = groupWallMessageService;
        this.accountPrivateMessageService = accountPrivateMessageService;
        this.dataHandler = dataHandler;
    }

    public boolean addMessage(HttpServletRequest req) {
        Message message;
        try {
            message = getMessageFromRequest(req);
        } catch (IOException | ServletException e) {
            throw new IllegalStateException(e);
        }

        String text = message.getText();
        if ((isNull(text) || text.trim().isEmpty()) && isNull(message.getImage())) {
            return false;
        }

        long receiverId = (long) req.getAttribute(RECEIVER_ID);
        message.setReceiverId(receiverId);

        String type = req.getParameter("type");
        if ("accountWall".equals(type)) {
            return accountWallMessageService.addMessage(message);
        } else if ("accountPrivate".equals(type)) {
            return accountPrivateMessageService.addMessage(message);
        } else if ("groupWall".equals(type)) {
            return groupWallMessageService.addMessage(message);
        }
        throw new IllegalArgumentException("message type not specified");
    }

    public Message getMessageFromRequest(HttpServletRequest req) throws IOException, ServletException {
        Message message = new Message();
        String text = req.getParameter("text");
        message.setText(text);
        Part part = req.getPart("image");
        if (part.getSize() > 1) {
            try (InputStream inputStream = part.getInputStream()) {
                message.setImage(dataHandler.readMessageImage(inputStream));
            }
        }
        long senderId = (long) req.getAttribute(REQUESTER_ID);
        Account author = new Account();
        author.setId(senderId);
        message.setAuthor(author);
        return message;
    }

    public void deleteMessage(HttpServletRequest req) {
        Message message = new Message();
        long messageId = (long) req.getAttribute(REQUESTED_ID);
        message.setId(messageId);
        String type = req.getParameter("type");
        if ("accountWall".equals(type)) {
            accountWallMessageService.deleteMessage(message);
        } else if ("accountPrivate".equals(type)) {
            accountPrivateMessageService.deleteMessage(message);
        } else if ("groupWall".equals(type)) {
            groupWallMessageService.deleteMessage(message);
        }
    }
}
