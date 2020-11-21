package com.getjavajob.training.yarginy.socialnetwork.dao.facades;

import com.getjavajob.training.yarginy.socialnetwork.common.models.message.Message;

import java.util.Collection;

public interface GroupWallMessageDao {
    Message select(long id);

    Message select(Message message);

    boolean create(Message message);

    boolean update(Message message, Message storedMessage);

    boolean delete(Message message);

    Collection<Message> selectAll();

    Message getNullMessage();

    Collection<Message> getGroupWallMessages(long groupId);
}
