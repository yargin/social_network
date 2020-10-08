package com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.facades;

import com.getjavajob.training.yarginy.socialnetwork.dao.factories.connector.TransactionManager;

import static com.getjavajob.training.yarginy.socialnetwork.dao.factories.AbstractDbFactory.getDbFactory;

public final class TransactionManagerFactory {
    private TransactionManagerFactory() {
    }

    public static TransactionManager getTransactionManager() {
        return getDbFactory().getTransactionManager();
    }
}
