package com.getjavajob.training.yarginy.socialnetwork.dao.jdbctemplates.facades.messages;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Message;

import java.io.Serializable;
import java.util.Collection;

public interface MessageDao extends Serializable {
    Message select(long id);

    Message select(Message message);

    boolean create(Message message);

    boolean update(Message message, Message storedMessage);

    boolean delete(Message message);

    Collection<Message> selectAll();

    Message getNullMessage();

    Collection<Message> getMessages(long id);
}
