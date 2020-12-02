package com.getjavajob.training.yarginy.socialnetwork.dao.facades;

import com.getjavajob.training.yarginy.socialnetwork.dao.factories.connectionpool.Transaction;
import com.getjavajob.training.yarginy.socialnetwork.dao.factories.connectionpool.TransactionManagerImpl;

public class TransactionManager {
    public Transaction getTransaction() {
        return TransactionManagerImpl.getTransaction();
    }
}
