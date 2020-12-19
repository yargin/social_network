package com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.password;

import com.getjavajob.training.yarginy.socialnetwork.common.models.password.Password;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.DaoImplOld;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.Dml;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

public class PasswordDao extends DaoImplOld<Password> {
    public PasswordDao(DataSource dataSource, Dml<Password> passwordDml) {
        super(dataSource, passwordDml);
    }

    @Override
    public boolean delete(Password entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Collection<Password> selectAll() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean update(Password password, Password storedPassword) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = dml.getSelect(connection, storedPassword, true);
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
