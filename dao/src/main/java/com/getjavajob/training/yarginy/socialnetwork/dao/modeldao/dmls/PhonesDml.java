package com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.dmls;

import com.getjavajob.training.yarginy.socialnetwork.common.models.NullEntitiesFactory;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.phone.Phone;
import com.getjavajob.training.yarginy.socialnetwork.common.models.phone.PhoneImpl;
import com.getjavajob.training.yarginy.socialnetwork.common.models.phone.additionaldata.PhoneType;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.AbstractDml;
import com.getjavajob.training.yarginy.socialnetwork.dao.tables.AccountsTable;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import static com.getjavajob.training.yarginy.socialnetwork.dao.tables.PhonesTable.*;
import static com.getjavajob.training.yarginy.socialnetwork.dao.utils.querybuilder.SqlQueryBuilder.buildQuery;
import static java.util.Objects.isNull;

public class PhonesDml extends AbstractDml<Phone> {
    public static final String SELECT_ALL = buildQuery().selectJoin(TABLE, AccountsTable.TABLE, OWNER, AccountsTable.ID).
            build();
    public static final String SELECT_UPDATE_BY_NUMBER = buildQuery().selectAll(TABLE).where(NUMBER).build();
    public static final String SELECT_UPDATE_BY_ID = buildQuery().selectAll(TABLE).where(ID).build();
    public static final String SELECT_BY_ID = buildQuery().selectLeftFullRightView(TABLE, AccountsTable.TABLE,
            AccountsTable.VIEW_FIELDS, OWNER, AccountsTable.ID).where(ID).build();
    public static final String SELECT_BY_NUMBER = buildQuery().selectLeftFullRightView(TABLE, AccountsTable.TABLE,
            AccountsTable.VIEW_FIELDS, OWNER, AccountsTable.ID).where(NUMBER).build();
    private final AccountDml accountDml = new AccountDml();

    @Override
    protected String getSelectById() {
        return SELECT_BY_ID;
    }

    @Override
    protected String getSelectAll() {
        return SELECT_ALL;
    }

    @Override
    protected String getSelectByAltKey() {
        return SELECT_BY_NUMBER;
    }

    @Override
    protected String getSelectForUpdateByAltKey() {
        return SELECT_UPDATE_BY_NUMBER;
    }

    @Override
    protected String getSelectForUpdateById() {
        return SELECT_UPDATE_BY_ID;
    }

    @Override
    protected void setAltKeyParams(PreparedStatement statement, Phone phone) throws SQLException {
        statement.setString(1, phone.getNumber());
    }

    @Override
    public Phone retrieveViewFromRow(ResultSet resultSet) throws SQLException {
        Phone phone = new PhoneImpl();
        phone.setId(resultSet.getLong(ID));
        phone.setNumber(resultSet.getString(NUMBER));
        if (!isNull(resultSet.getString(TYPE))) {
            phone.setType(PhoneType.valueOf(resultSet.getString(TYPE)));
        }
        return phone;
    }

    @Override
    public Phone retrieveFromRow(ResultSet resultSet) throws SQLException {
        Phone phone = retrieveViewFromRow(resultSet);
        Account owner = accountDml.retrieveViewFromRow(resultSet);
        phone.setOwner(owner);
        return phone;
    }

    @Override
    public Collection<Phone> retrieveEntities(ResultSet resultSet) throws SQLException {
        Collection<Phone> phones = new ArrayList<>();
        while (resultSet.next()) {
            Phone phone = retrieveViewFromRow(resultSet);
            phones.add(phone);
        }
        return phones;
    }

    @Override
    public void updateRow(ResultSet resultSet, Phone phone, Phone storedPhone) throws SQLException {
        updateFieldIfDiffers(phone::getNumber, storedPhone::getNumber, resultSet::updateString, NUMBER);
        updateFieldIfDiffers(phone::getType, storedPhone::getType, resultSet::updateString, TYPE, PhoneType::toString);
        updateFieldIfDiffers(phone::getOwner, storedPhone::getOwner, resultSet::updateLong, OWNER, Account::getId);
    }

    @Override
    public Phone getNullEntity() {
        return NullEntitiesFactory.getNullPhone();
    }
}
