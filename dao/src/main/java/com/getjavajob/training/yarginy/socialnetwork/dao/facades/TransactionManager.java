package com.getjavajob.training.yarginy.socialnetwork.dao.facades;

import com.getjavajob.training.yarginy.socialnetwork.dao.factories.connectionpool.Transaction;
import com.getjavajob.training.yarginy.socialnetwork.dao.factories.connectionpool.TransactionManagerImpl;

import java.io.Serializable;

public class TransactionManager implements Serializable {
    public Transaction getTransaction() {
        return TransactionManagerImpl.getTransaction();
    }
}
