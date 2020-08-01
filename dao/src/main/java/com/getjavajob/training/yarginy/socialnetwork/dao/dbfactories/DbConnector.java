package com.getjavajob.training.yarginy.socialnetwork.dao.dbfactories;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * provides connection to database
 */
public interface DbConnector {
    Connection getConnection() throws SQLException;
}
