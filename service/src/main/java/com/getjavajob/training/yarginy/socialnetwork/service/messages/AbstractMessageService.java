package com.getjavajob.training.yarginy.socialnetwork.service.messages;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Message;
import com.getjavajob.training.yarginy.socialnetwork.dao.jdbctemplates.facades.messages.MessageDao;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collection;

import static java.util.Objects.isNull;

public abstract class AbstractMessageService implements MessageService {
    private final MessageDao messageDao;

    protected AbstractMessageService(MessageDao messageDao) {
        this.messageDao = messageDao;
    }

    @Override
    public boolean addMessage(Message message) {
        if (isNull(message.getImage()) && isNull(message.getText())) {
            return false;
        }
        message.setDate(Timestamp.valueOf(LocalDateTime.now().withNano(0)));
        return messageDao.create(message);
    }

    @Override
    public boolean deleteMessage(Message message) {
        return messageDao.delete(message);
    }

    @Override
    public Collection<Message> selectMessages(long id) {
        return messageDao.getMessages(id);
    }
}
