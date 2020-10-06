package com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.password;

import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.password.Password;
import com.getjavajob.training.yarginy.socialnetwork.common.models.password.PasswordImpl;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.AbstractDml;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.dmls.AccountDml;
import com.getjavajob.training.yarginy.socialnetwork.dao.tables.AccountsTable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

import static com.getjavajob.training.yarginy.socialnetwork.common.models.NullEntitiesFactory.getNullPassword;
import static com.getjavajob.training.yarginy.socialnetwork.dao.tables.PasswordTable.*;
import static com.getjavajob.training.yarginy.socialnetwork.dao.utils.querybuilder.SqlQueryBuilder.buildQuery;

public class PasswordDml extends AbstractDml<Password> {
    public static final String SELECT = buildQuery().selectJoin(TABLE, AccountsTable.TABLE, EMAIL, AccountsTable.EMAIL).
            where(EMAIL).build();
    public static final String SELECT_UPDATE = buildQuery().select(TABLE).where(EMAIL).build();
    public static final AccountDml ACCOUNT_DML = new AccountDml();

    @Override
    public PreparedStatement getUpdatableSelect(Connection connection, Password password) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(SELECT_UPDATE, ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_UPDATABLE);
        statement.setString(1, password.getAccount().getEmail());
        return statement;
    }


    @Override
    public PreparedStatement getSelect(Connection connection, Password password) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(SELECT);
        statement.setString(1, password.getAccount().getEmail());
        return statement;
    }

    @Override
    public Password selectFromRow(ResultSet resultSet) throws SQLException {
        Password password = new PasswordImpl();
        Account account = ACCOUNT_DML.selectFromRow(resultSet);
        password.setAccount(account);
        password.setEncryptedPassword(resultSet.getString(PASSWORD));
        return password;
    }

    @Override
    public Password getNullEntity() {
        return getNullPassword();
    }

    @Override
    public void updateRow(ResultSet resultSet, Password password, Password storedPassword) throws SQLException {
        updateFieldIfDiffers(password::getPassword, storedPassword::getPassword, resultSet::updateString, PASSWORD);
        updateFieldIfDiffers(password::getAccount, storedPassword::getAccount, resultSet::updateString, EMAIL,
                Account::getEmail);
    }

    @Override
    public Collection<Password> selectEntities(ResultSet resultSet) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public PreparedStatement getSelect(Connection connection, long id) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public PreparedStatement getSelectAll(Connection connection) throws SQLException {
        throw new UnsupportedOperationException();
    }
}
