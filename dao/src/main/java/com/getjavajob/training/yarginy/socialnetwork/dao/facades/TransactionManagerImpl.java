package com.getjavajob.training.yarginy.socialnetwork.dao.facades;

import com.getjavajob.training.yarginy.socialnetwork.dao.factories.connectionpool.Transaction;

import static com.getjavajob.training.yarginy.socialnetwork.dao.factories.AbstractDbFactory.getDbFactory;

public class TransactionManagerImpl implements TransactionManager {
    private TransactionManager transactionManager = getDbFactory().getTransactionManager();

    @Override
    public Transaction getTransaction() {
        return transactionManager.getTransaction();
    }
}
