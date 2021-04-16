package com.getjavajob.training.yarginy.socialnetwork.dao.facades.messages;

import com.getjavajob.training.yarginy.socialnetwork.common.models.messages.AccountWallMessage;
import com.getjavajob.training.yarginy.socialnetwork.dao.models.Dao;
import com.getjavajob.training.yarginy.socialnetwork.dao.relationsdao.onetomany.OneToManyDao;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository("accountWallMessageDaoFacade")
public class AccountWallMessageDaoFacadeImpl implements AccountWallMessageDaoFacade {
    private final Dao<AccountWallMessage> accountWallMessageDao;
    private final OneToManyDao<AccountWallMessage> accountWallMessagesDao;

    public AccountWallMessageDaoFacadeImpl(@Qualifier("accountWallMessageDao") Dao<AccountWallMessage>
                                                   accountWallMessageDao,
                                           @Qualifier("accountWallMessagesDao") OneToManyDao<AccountWallMessage>
                                                   accountWallMessagesDao) {
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
    public boolean update(AccountWallMessage message) {
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
