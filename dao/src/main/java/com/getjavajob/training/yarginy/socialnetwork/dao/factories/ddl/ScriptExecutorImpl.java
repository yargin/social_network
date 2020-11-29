package com.getjavajob.training.yarginy.socialnetwork.dao.factories.ddl;


import com.getjavajob.training.yarginy.socialnetwork.dao.factories.connectionpool.ConnectionPool;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static java.lang.System.lineSeparator;

public class ScriptExecutorImpl implements ScriptExecutor {
    private final ConnectionPool connectionPool;
    private final String scriptsDir;

    public ScriptExecutorImpl(ConnectionPool connectionPool, String scriptsDir) {
        this.connectionPool = connectionPool;
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
            throw new IllegalStateException(e);
        }
    }

    @Override
    public boolean executeScript(String scriptFile) {
        String query = readQueryFromFile(scriptsDir + scriptFile);
        try (Connection connection = connectionPool.getConnection();
             Statement statement = connection.createStatement()) {
            return statement.execute(query);
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
