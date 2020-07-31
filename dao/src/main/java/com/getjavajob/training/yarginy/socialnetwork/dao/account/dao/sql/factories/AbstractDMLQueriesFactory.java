package com.getjavajob.training.yarginy.socialnetwork.dao.account.dao.sql.factories;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static java.lang.System.lineSeparator;

public abstract class AbstractDMLQueriesFactory {
    private final AbstractConnectionFactory connectionFactory;

    public AbstractDMLQueriesFactory(AbstractConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
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

    private boolean executeQuery(String query) {
        try(Connection connection = connectionFactory.getConnection();
            Statement statement = connection.createStatement()) {
            return statement.execute(query);
        } catch (SQLException e) {
            throw new IllegalStateException(e.getMessage());
        }
    }

    public boolean createAccounts() {
        return executeQuery(readQueryFromFile(getCreateAccountsQueryFile()));
    }

    public boolean dropAccounts() {
        return executeQuery(readQueryFromFile(getDropAccountsQueryFile()));
    }

    protected abstract String getCreateAccountsQueryFile();

    protected abstract String getDropAccountsQueryFile();
}
