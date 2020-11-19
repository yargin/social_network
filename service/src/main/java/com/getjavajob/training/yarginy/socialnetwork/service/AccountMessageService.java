package com.getjavajob.training.yarginy.socialnetwork.service;

import com.getjavajob.training.yarginy.socialnetwork.common.models.message.Message;

import java.util.Collection;

public interface AccountMessageService {
    Message selectAccountWallMessage(long messageId);

    Message selectAccountWallMessage(Message message);

    boolean addAccountWallMessage(Message message);

    boolean updateMessage(Message message, Message storedMessage);

    boolean deleteMessage(Message message);

    Collection<Message> selectAccountWallMessages(long accountId);
}
