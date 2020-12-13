package com.getjavajob.training.yarginy.socialnetwork.dao;

import com.getjavajob.training.yarginy.socialnetwork.dao.factories.ddl.ScriptExecutorImpl;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

public class TestDataSource extends DriverManagerDataSource {
    private static final String SCRIPT_DIR = "src/test/resources/H2/";
    private static final String SCRIPT = "run_creation.sql";

    public void init() {
        new ScriptExecutorImpl(this, SCRIPT_DIR).executeScript(SCRIPT);
    }
}
