package com.getjavajob.training.yarginy.socialnetwork.dao.dbfactories.implementations;

import com.getjavajob.training.yarginy.socialnetwork.dao.dbfactories.DbConnector;
import com.getjavajob.training.yarginy.socialnetwork.dao.dbfactories.DmlQueriesExecutor;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static java.lang.System.lineSeparator;

public class DmlQueriesExecutorImpl implements DmlQueriesExecutor {
    private final DbConnector dbConnector;
    private final String scriptsDir;

    public DmlQueriesExecutorImpl(DbConnector dbConnector, String scriptsDir) {
        this.dbConnector = dbConnector;
        this.scriptsDir = scriptsDir;
    }

    private String readQueryFromFile(String queryFile) {
        try (FileReader fileReader = new FileReader(queryFile);
             BufferedReader bufferedReader = new BufferedReader(fileReader)) {
            StringBuilder stringBuilder = new StringBuilder();
            while (bufferedReader.ready()) {
                stringBuilder.append(bufferedReader.readLine()).append(lineSeparator());
            }
            return stringBuilder.toString();
        } catch (IOException e) {
            throw new IllegalStateException(e.getMessage());
        }
    }

    private boolean executeScript(String script) {
        String query = readQueryFromFile(scriptsDir + script);
        try(Connection connection = dbConnector.getConnection();
            Statement statement = connection.createStatement()) {
            return statement.execute(query);
        } catch (SQLException e) {
            throw new IllegalStateException(e.getMessage());
        }
    }

    @Override
    public boolean createAccounts() {
        return executeScript("create_table_accounts");
    }

    @Override
    public boolean dropAccounts() {
        return executeScript("drop_table_accounts");
    }

    @Override
    public boolean createGroups() {
        return false;
    }

    @Override
    public boolean dropGroups() {
        return false;
    }
}
