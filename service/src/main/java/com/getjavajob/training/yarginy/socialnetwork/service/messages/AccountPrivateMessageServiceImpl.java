package com.getjavajob.training.yarginy.socialnetwork.service.messages;

import com.getjavajob.training.yarginy.socialnetwork.common.models.message.Message;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.AccountPrivateMessageDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.AccountPrivateMessageDaoImpl;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collection;

public class AccountPrivateMessageServiceImpl implements MessageService {
    AccountPrivateMessageDao messageDao = new AccountPrivateMessageDaoImpl();

    @Override
    public boolean addMessage(Message message) {
        message.setDate(Timestamp.valueOf(LocalDateTime.now().withNano(0)));
        return messageDao.create(message);
    }

    @Override
    public boolean deleteMessage(Message message) {
        return messageDao.delete(message);
    }

    @Override
    public Collection<Message> selectMessages(long accountId) {
        return messageDao.getAccountPrivateMessages(accountId);
    }
}
