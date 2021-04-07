package com.getjavajob.training.yarginy.socialnetwork.service.messages;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Message;

import java.io.Serializable;
import java.util.Collection;

public interface MessageService extends Serializable {
    boolean addMessage(Message message);

    boolean deleteMessage(Message message);

    Collection<Message> selectMessages(long id);
}
