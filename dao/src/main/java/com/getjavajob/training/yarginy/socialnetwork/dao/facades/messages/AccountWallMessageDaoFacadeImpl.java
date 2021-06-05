package com.getjavajob.training.yarginy.socialnetwork.dao.facades.messages;

import com.getjavajob.training.yarginy.socialnetwork.common.models.messages.AccountWallMessage;
import com.getjavajob.training.yarginy.socialnetwork.dao.jpa.modeldaos.implementations.messages.AccountWallMessageDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.jpa.relationdaos.onetomany.implementations.messages.AccountWallMessagesDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.utils.TransactionPerformer;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository("accountWallMessageDaoFacade")
public class AccountWallMessageDaoFacadeImpl implements AccountWallMessageDaoFacade {
    private final AccountWallMessageDao accountWallMessageDao;
    private final AccountWallMessagesDao accountWallMessagesDao;
    private final TransactionPerformer transactionPerformer;

    public AccountWallMessageDaoFacadeImpl(AccountWallMessageDao accountWallMessageDao,
                                           AccountWallMessagesDao accountWallMessagesDao,
                                           TransactionPerformer transactionPerformer) {
        this.accountWallMessageDao = accountWallMessageDao;
        this.accountWallMessagesDao = accountWallMessagesDao;
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
        return transactionPerformer.perform(() -> accountWallMessageDao.create(message));
    }

    @Override
    public boolean update(AccountWallMessage message) {
        return transactionPerformer.perform(() -> accountWallMessageDao.update(message));
    }

    @Override
    public boolean delete(AccountWallMessage message) {
        return transactionPerformer.perform(() -> accountWallMessageDao.delete(message));
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
