package com.getjavajob.training.yarginy.socialnetwork.dao.factories.connector;

import java.sql.Connection;
import java.sql.SQLException;

public interface DbConnector {
    int getCapacity();

    Connection bindConnection() throws SQLException;

    void unbindConnection() throws SQLException;
}
