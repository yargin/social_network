package com.getjavajob.training.yarginy.socialnetwork.dao.facades.messages;

import com.getjavajob.training.yarginy.socialnetwork.common.models.message.Message;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.Dao;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.testetst.messages.AccountWallMessageDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.relationsdao.testtest.onetomany.AbstractOneToManyDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.relationsdao.testtest.onetomany.AccountWallMessagesDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository("accountWallMessageDaoFacade")
public class AccountWallMessageDaoImpl implements AccountWallMessageDaoFacade {
    private final Dao<Message> accountWallMessageDao;
    private AbstractOneToManyDao<Message> accountWallMessagesOneToManyDao;

    @Autowired
    public AccountWallMessageDaoImpl(AccountWallMessageDao accountWallMessageDao) {
        this.accountWallMessageDao = accountWallMessageDao;
    }

    @Autowired
    public void setAccountWallMessagesOneToManyDao(AccountWallMessagesDao accountWallMessagesOneToManyDao) {
        this.accountWallMessagesOneToManyDao = accountWallMessagesOneToManyDao;
    }

    @Override
    public Message select(long id) {
        return accountWallMessageDao.select(id);
    }

    @Override
    public Message select(Message message) {
        return accountWallMessageDao.select(message);
    }

    @Override
    public boolean create(Message message) {
        return accountWallMessageDao.create(message);
    }

    @Override
    public boolean update(Message message, Message storedMessage) {
        return accountWallMessageDao.update(message, storedMessage);
    }

    @Override
    public boolean delete(Message message) {
        return accountWallMessageDao.delete(message);
    }

    @Override
    public Collection<Message> selectAll() {
        return accountWallMessageDao.selectAll();
    }

    @Override
    public Message getNullMessage() {
        return accountWallMessageDao.getNullEntity();
    }

    @Override
    public Collection<Message> getMessages(long accountId) {
        return accountWallMessagesOneToManyDao.selectMany(accountId);
    }
}
