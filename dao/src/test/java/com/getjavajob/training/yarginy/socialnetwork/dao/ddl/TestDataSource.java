package com.getjavajob.training.yarginy.socialnetwork.dao.ddl;

import org.springframework.jdbc.datasource.DriverManagerDataSource;

//todo maybe download hikari & place here instead non-pool DriverManager?
public class TestDataSource extends DriverManagerDataSource {
    private static final String SCRIPT_DIR = "src/test/resources/H2/";
    private static final String SCRIPT = "run_creation.sql";

    public void init() {
        new ScriptExecutorImpl(this, SCRIPT_DIR).executeScript(SCRIPT);
    }
}
