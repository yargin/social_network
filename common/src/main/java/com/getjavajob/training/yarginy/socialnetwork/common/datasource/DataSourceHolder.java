package com.getjavajob.training.yarginy.socialnetwork.common.datasource;

import javax.sql.DataSource;

public class DataSourceHolder {
    private static DataSource dataSource;

    public static DataSource getDataSource() {
        return dataSource;
    }

    public static void setDataSource(DataSource dataSource) {
        DataSourceHolder.dataSource = dataSource;
    }
}
