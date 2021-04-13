package com.getjavajob.training.yarginy.socialnetwork.service.messages;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Model;
import com.getjavajob.training.yarginy.socialnetwork.common.models.messages.Message;

import java.io.Serializable;
import java.util.Collection;

public interface MessageService<O extends Model, E extends Message<O>> extends Serializable {
    boolean addMessage(E message);

    boolean deleteMessage(E message);

    Collection<E> selectMessages(long oneId);
}
