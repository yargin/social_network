package com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.dmls;

import com.getjavajob.training.yarginy.socialnetwork.common.exceptions.IncorrectDataException;
import com.getjavajob.training.yarginy.socialnetwork.common.models.NullEntitiesFactory;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.phone.Phone;
import com.getjavajob.training.yarginy.socialnetwork.common.models.phone.PhoneImpl;
import com.getjavajob.training.yarginy.socialnetwork.common.models.phone.additionaldata.PhoneType;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.AbstractDml;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.Dao;
import com.getjavajob.training.yarginy.socialnetwork.dao.tables.AccountsTable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import static com.getjavajob.training.yarginy.socialnetwork.dao.factories.AbstractDbFactory.getDbFactory;
import static com.getjavajob.training.yarginy.socialnetwork.dao.tables.PhonesTable.*;
import static com.getjavajob.training.yarginy.socialnetwork.dao.utils.querybuilder.SqlQueryBuilder.buildQuery;
import static java.util.Objects.isNull;

public class PhonesDml implements AbstractDml<Phone> {
    public static final String SELECT_ALL = buildQuery().selectJoin(TABLE, AccountsTable.TABLE, OWNER, AccountsTable.ID).
            build();
    public static final String SELECT_UPDATE = buildQuery().select(TABLE).where(NUMBER).build();
    public static final String SELECT_BY_ID = buildQuery().selectJoin(TABLE, AccountsTable.TABLE, OWNER,
            AccountsTable.ID).where(ID).build();
    public static final String SELECT_BY_NUMBER = buildQuery().selectJoin(TABLE, AccountsTable.TABLE, OWNER,
            AccountsTable.ID).where(NUMBER).build();
    private final AccountDml accountDml = new AccountDml();
    private final Dao<Account> accountDao = getDbFactory().getAccountDao();

    @Override
    public PreparedStatement getUpdatableSelect(Connection connection, Phone phone) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(SELECT_UPDATE, ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_UPDATABLE);
        statement.setString(1, phone.getNumber());
        return statement;
    }

    @Override
    public PreparedStatement getSelect(Connection connection, long id) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID);
        statement.setLong(1, id);
        return statement;
    }

    @Override
    public PreparedStatement getSelectAll(Connection connection) throws SQLException {
        return connection.prepareStatement(SELECT_ALL);
    }

    @Override
    public PreparedStatement getSelect(Connection connection, Phone phone) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(SELECT_BY_NUMBER);
        statement.setString(1, phone.getNumber());
        return statement;
    }

    @Override
    public Phone selectFromRow(ResultSet resultSet) throws SQLException {
        Phone phone = new PhoneImpl();
        phone.setId(resultSet.getLong(ID));
        phone.setNumber(resultSet.getString(NUMBER));
        if (!isNull(resultSet.getString(TYPE))) {
            phone.setType(PhoneType.valueOf(resultSet.getString(TYPE)));
        }
        Account owner = accountDml.selectFromRow(resultSet);
        phone.setOwner(owner);
        return phone;
    }

    @Override
    public Collection<Phone> selectEntities(ResultSet resultSet) throws SQLException {
        Collection<Phone> phones = new ArrayList<>();
        while (resultSet.next()) {
            Phone phone = selectFromRow(resultSet);
            phones.add(phone);
        }
        return phones;
    }

    @Override
    public void updateRow(ResultSet resultSet, Phone phone) throws SQLException {
        resultSet.updateString(NUMBER, phone.getNumber());
        if (!isNull(phone.getType())) {
            resultSet.updateString(TYPE, phone.getType().toString());
        }
        if (isNull(phone.getOwner())) {
            throw new IncorrectDataException("owner can't be null");
        }
        if (phone.getOwner().getId() < 1) {
            phone.setOwner(accountDao.select(phone.getOwner()));
        }
        resultSet.updateLong(OWNER, phone.getOwner().getId());
    }

    @Override
    public Phone getNullEntity() {
        return NullEntitiesFactory.getNullPhone();
    }
}
