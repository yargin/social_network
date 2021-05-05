package com.getjavajob.training.yarginy.socialnetwork.dao.facades.messages;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Model;
import com.getjavajob.training.yarginy.socialnetwork.common.models.messages.Message;

import java.io.Serializable;
import java.util.Collection;

public interface MessageDao<O extends Model, E extends Message<O>> extends Serializable {
    E select(long id);

    E select(E message);

    boolean create(E message);

    boolean update(E message);

    boolean delete(E message);

    Collection<E> selectAll();

    E getNullMessage();

    Collection<E> getMessages(long oneId);
}
