package com.getjavajob.training.yarginy.socialnetwork.common.datasource;

import javax.sql.DataSource;

public final class DataSourceHolder {
    private static DataSource dataSource;

    private DataSourceHolder() {
    }

    public static DataSource getDataSource() {
        return dataSource;
    }

    public static void setDataSource(DataSource dataSource) {
        DataSourceHolder.dataSource = dataSource;
    }
}
