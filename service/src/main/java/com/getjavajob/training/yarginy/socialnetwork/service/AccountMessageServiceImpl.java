package com.getjavajob.training.yarginy.socialnetwork.service;

import com.getjavajob.training.yarginy.socialnetwork.common.models.message.Message;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.AccountWallMessageDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.AccountWallMessageDaoImpl;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collection;

public class AccountMessageServiceImpl implements AccountMessageService {
    private final AccountWallMessageDao accountWallMessageDao = new AccountWallMessageDaoImpl();

    @Override
    public Message selectAccountWallMessage(long messageId) {
        return accountWallMessageDao.select(messageId);
    }

    @Override
    public Message selectAccountWallMessage(Message message) {
        return accountWallMessageDao.select(message);
    }

    @Override
    public boolean addAccountWallMessage(Message message) {
        message.setDate(Timestamp.valueOf(LocalDateTime.now().withNano(0)));
        return accountWallMessageDao.create(message);
    }

    @Override
    public boolean updateMessage(Message message, Message storedMessage) {
        return accountWallMessageDao.update(message, storedMessage);
    }

    @Override
    public boolean deleteMessage(Message message) {
        return accountWallMessageDao.delete(message);
    }

    @Override
    public Collection<Message> selectAccountWallMessages(long accountId) {
        return accountWallMessageDao.getAccountWallMessages(accountId);
    }
}
