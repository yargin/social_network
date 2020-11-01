package com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.dmls;

import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.AbstractDml;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

public class MessageDml extends AbstractDml<Account> {
    @Override
    protected String getSelectById() {
        return null;
    }

    @Override
    protected String getSelectAll() {
        return null;
    }

    @Override
    protected String getSelectByAltKey() {
        return null;
    }

    @Override
    protected String getSelectForUpdate() {
        return null;
    }

    @Override
    protected void setAltKeyParams(PreparedStatement statement, Account entity) throws SQLException {

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
