package com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.dmls;

import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.AbstractDml;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

public class MessageDml extends AbstractDml<Account> {
    @Override
    public PreparedStatement getSelect(Connection connection, long id) throws SQLException {
        return null;
    }

    @Override
    public PreparedStatement getSelect(Connection connection, Account entity) throws SQLException {
        return null;
    }

    @Override
    public PreparedStatement getUpdatableSelect(Connection connection, Account entity) throws SQLException {
        return null;
    }

    @Override
    public PreparedStatement getSelectAll(Connection connection) throws SQLException {
        return null;
    }

    @Override
    public Account selectFromRow(ResultSet resultSet) throws SQLException {
        return null;
    }

    @Override
    public Collection<Account> selectEntities(ResultSet resultSet) throws SQLException {
        return null;
    }

    @Override
    public void updateRow(ResultSet resultSet, Account entity, Account storedEntity) throws SQLException {

    }

    @Override
    public Account getNullEntity() {
        return null;
    }
}
