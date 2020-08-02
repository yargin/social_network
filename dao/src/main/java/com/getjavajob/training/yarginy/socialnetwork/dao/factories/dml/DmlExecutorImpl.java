package com.getjavajob.training.yarginy.socialnetwork.dao.factories.dml;

import com.getjavajob.training.yarginy.socialnetwork.dao.factories.connector.DbConnector;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static java.lang.System.lineSeparator;

public class DmlExecutorImpl implements DmlExecutor {
    private static final String CREATION_FILE = "creation_script";
    private static final String DELETION_FILE = "deletion_script";
    private final DbConnector dbConnector;
    protected final String scriptsDir;

    public DmlExecutorImpl(DbConnector dbConnector, String scriptsDir) {
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
    public boolean createTables() {
        return executeScript(CREATION_FILE);
    }

    @Override
    public boolean dropTables() {
        return executeScript(DELETION_FILE);
    }
}
