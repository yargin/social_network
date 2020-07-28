package com.getjavajob.training.yarginy.socialnetwork.dao.entities.account.factories;

import com.getjavajob.training.yarginy.socialnetwork.dao.entities.account.Account;

import java.sql.Connection;
import java.sql.SQLException;

public interface AccountFactorySQL {
    Account getInstance(int id, Connection connection) throws SQLException;

    Account getInstance(String email, Connection connection) throws SQLException;
}
