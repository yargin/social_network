package com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.password;

import com.getjavajob.training.yarginy.socialnetwork.common.models.password.Password;
import com.getjavajob.training.yarginy.socialnetwork.dao.factories.connectionpool.ConnectionPool;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.DaoImpl;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.Dml;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

public class PasswordDao extends DaoImpl<Password> {
    public PasswordDao(ConnectionPool connectionPool, Dml<Password> passwordDml) {
        super(connectionPool, passwordDml);
    }

    @Override
    public boolean delete(Password entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Password select(long id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Collection<Password> selectAll() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean update(Password password) {
        Password storedPassword = select(password);
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = dml.getUpdatableSelect(connection, password);
             ResultSet resultSet = statement.executeQuery()) {
            if (!resultSet.next()) {
                return false;
            }
            dml.updateRow(resultSet, password, storedPassword);
            resultSet.updateRow();
            if (resultSet.next()) {
                throw new IllegalStateException("statement returned more then one row");
            }
            return true;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }

    }
}
