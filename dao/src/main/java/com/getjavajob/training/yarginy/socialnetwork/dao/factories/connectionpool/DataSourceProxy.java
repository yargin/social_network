package com.getjavajob.training.yarginy.socialnetwork.dao.factories.connectionpool;

import javax.sql.DataSource;
import java.sql.SQLException;

public class DataSourceProxy extends AbstractDataSource {
    private final DataSource dataSource;

    public DataSourceProxy(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    protected void closeConnectionProxy(ConnectionProxy connectionProxy) {
        try {
            connectionProxy.closeActually();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    ConnectionProxy retrieveConcreteConnectionProxy() {
        try {
            return new ConnectionProxy(dataSource.getConnection(), this);
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }
}
