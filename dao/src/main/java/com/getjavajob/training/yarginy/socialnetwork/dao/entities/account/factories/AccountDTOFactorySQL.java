package com.getjavajob.training.yarginy.socialnetwork.dao.entities.account.factories;

import com.getjavajob.training.yarginy.socialnetwork.dao.entities.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.dao.entities.account.dto.AccountSQL;

import java.sql.Connection;
import java.sql.SQLException;

public class AccountDTOFactorySQL implements AccountFactorySQL {
    @Override
    public Account getInstance(int id, Connection connection) throws SQLException {
        return new AccountSQL(id, connection);
    }

    @Override
    public Account getInstance(String email, Connection connection) throws SQLException {
        return new AccountSQL(email, connection);
    }
}
