package com.getjavajob.training.yarginy.socialnetwork.dao;

import com.getjavajob.training.yarginy.socialnetwork.common.datasource.DataSourceHolder;
import com.getjavajob.training.yarginy.socialnetwork.dao.factories.ddl.ScriptExecutorImpl;
import org.apache.commons.dbcp2.BasicDataSource;

import static java.util.Objects.isNull;

public class TestDataSourceInitializer {
    private static final String SCRIPTS_DIR = "./src/main/resources/scripts/H2/";
    private static final String CREATION_SCRIPT = "creation_script.sql";

    public static void initDataSource() {
        if (!isNull(DataSourceHolder.getDataSource())) {
            return;
        }

        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriver(new org.h2.Driver());
        dataSource.setUrl("jdbc:h2:mem:test?user=sa&DB_CLOSE_DELAY=-1&MODE=MYSQL");

        DataSourceHolder.setDataSource(dataSource);
        new ScriptExecutorImpl(dataSource, SCRIPTS_DIR).executeScript(CREATION_SCRIPT);
    }
}
