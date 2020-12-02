package com.getjavajob.training.yarginy.socialnetwork.dao.factories.connectionpool;

public final class TransactionManagerImpl {
    private static AbstractDataSource abstractDataSource;

    private TransactionManagerImpl() {
    }

    static void init(AbstractDataSource abstractDataSource) {
        TransactionManagerImpl.abstractDataSource = abstractDataSource;
    }

    public static synchronized Transaction getTransaction() {
        ConnectionProxy connectionProxy = abstractDataSource.getConnectionProxy();
        return new TransactionImpl(connectionProxy);
    }
}
