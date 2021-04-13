package com.getjavajob.training.yarginy.socialnetwork.dao.jdbctemplates.facades.messages;

import com.getjavajob.training.yarginy.socialnetwork.common.models.messages.AccountWallMessage;
import com.getjavajob.training.yarginy.socialnetwork.dao.jpa.JpaDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.jpa.relationsdao.onetomany.JpaAccountWallMessagesDao;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository("accountWallMessageDaoFacade")
public class AccountWallMessageDaoFacadeImpl implements AccountWallMessageDaoFacade {
    private final JpaDao<AccountWallMessage> accountWallMessageDao;
    private final JpaAccountWallMessagesDao accountWallMessagesDao;

    public AccountWallMessageDaoFacadeImpl(@Qualifier("jpaAccountWallMessageDao") JpaDao<AccountWallMessage> accountWallMessageDao,
                                           JpaAccountWallMessagesDao accountWallMessagesDao) {
        this.accountWallMessageDao = accountWallMessageDao;
        this.accountWallMessagesDao = accountWallMessagesDao;
    }

    @Override
    public AccountWallMessage select(long messageId) {
        return accountWallMessageDao.select(messageId);
    }

    @Override
    public AccountWallMessage select(AccountWallMessage message) {
        return accountWallMessageDao.select(message);
    }

    @Override
    public boolean create(AccountWallMessage message) {
        return accountWallMessageDao.create(message);
    }

    @Override
    public boolean update(AccountWallMessage message, AccountWallMessage storedMessage) {
        //todo
//        return accountWallMessageDao.update(message, storedMessage);
        return accountWallMessageDao.update(message);
    }

    @Override
    public boolean delete(AccountWallMessage message) {
        return accountWallMessageDao.delete(message);
    }

    @Override
    public Collection<AccountWallMessage> selectAll() {
        return accountWallMessageDao.selectAll();
    }

    @Override
    public AccountWallMessage getNullMessage() {
        return accountWallMessageDao.getNullModel();
    }

    @Override
    public Collection<AccountWallMessage> getMessages(long accountId) {
        return accountWallMessagesDao.selectMany(accountId);
    }
}
