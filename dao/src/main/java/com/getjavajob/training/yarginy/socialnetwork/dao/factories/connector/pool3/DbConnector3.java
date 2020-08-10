package com.getjavajob.training.yarginy.socialnetwork.dao.factories.connector.pool3;

import java.sql.Connection;
import java.sql.SQLException;

public interface DbConnector3 {
    Connection getConnection() throws SQLException;

    int getCapacity();
}
