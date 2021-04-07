package com.getjavajob.training.yarginy.socialnetwork.dao.jdbctemplates.facades.messages;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Message;
import com.getjavajob.training.yarginy.socialnetwork.dao.jdbctemplates.modeldao.Dao;
import com.getjavajob.training.yarginy.socialnetwork.dao.jdbctemplates.relationsdao.onetomany.OneToManyDao;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository("accountWallMessageDaoFacade")
public class AccountWallMessageDaoImpl implements AccountWallMessageDaoFacade {
    private final Dao<Message> accountWallMessageDao;
    private final OneToManyDao<Message> accountWallMessagesDao;

    public AccountWallMessageDaoImpl(@Qualifier("accountWallMessageDao") Dao<Message> accountWallMessageDao,
                                     @Qualifier("accountWallMessagesDao") OneToManyDao<Message> accountWallMessagesDao) {
        this.accountWallMessageDao = accountWallMessageDao;
        this.accountWallMessagesDao = accountWallMessagesDao;
    }

    @Override
    public Message select(long messageId) {
        return accountWallMessageDao.select(messageId);
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
        return accountWallMessageDao.getNullModel();
    }

    @Override
    public Collection<Message> getMessages(long accountId) {
        return accountWallMessagesDao.selectMany(accountId);
    }
}
