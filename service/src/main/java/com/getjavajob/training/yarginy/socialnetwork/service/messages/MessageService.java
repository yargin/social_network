package com.getjavajob.training.yarginy.socialnetwork.service.messages;

import com.getjavajob.training.yarginy.socialnetwork.common.models.message.Message;

import java.util.Collection;

public interface MessageService {
    boolean addMessage(Message message);

    boolean deleteMessage(Message message);

    Collection<Message> selectMessages(long id);
}
