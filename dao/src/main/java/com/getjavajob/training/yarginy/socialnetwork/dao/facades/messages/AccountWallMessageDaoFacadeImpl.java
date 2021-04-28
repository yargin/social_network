package com.getjavajob.training.yarginy.socialnetwork.dao.facades.messages;

import com.getjavajob.training.yarginy.socialnetwork.common.models.messages.AccountWallMessage;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.utils.TransactionPerformer;
import com.getjavajob.training.yarginy.socialnetwork.dao.newdaos.modeldaos.implementations.messages.NewAccountWallMessageDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.newdaos.relationdaos.onetomany.implementations.messages.NewAccountWallMessagesDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository("accountWallMessageDaoFacade")
public class AccountWallMessageDaoFacadeImpl implements AccountWallMessageDaoFacade {
    private NewAccountWallMessageDao accountWallMessageDao;
    private NewAccountWallMessagesDao accountWallMessagesDao;
    private TransactionPerformer transactionPerformer;

    @Autowired
    public void setAccountWallMessageDao(NewAccountWallMessageDao accountWallMessageDao) {
        this.accountWallMessageDao = accountWallMessageDao;
    }

    @Autowired
    public void setAccountWallMessagesDao(NewAccountWallMessagesDao accountWallMessagesDao) {
        this.accountWallMessagesDao = accountWallMessagesDao;
    }

    @Autowired
    public void setTransactionPerformer(TransactionPerformer transactionPerformer) {
        this.transactionPerformer = transactionPerformer;
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
        return transactionPerformer.transactionPerformed(accountWallMessageDao::create, message);
    }

    @Override
    public boolean update(AccountWallMessage message) {
        return transactionPerformer.transactionPerformed(accountWallMessageDao::update, message);
    }

    @Override
    public boolean delete(AccountWallMessage message) {
        return transactionPerformer.transactionPerformed(accountWallMessageDao::delete, message);
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
