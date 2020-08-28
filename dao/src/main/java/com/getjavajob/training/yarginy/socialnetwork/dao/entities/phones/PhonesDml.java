package com.getjavajob.training.yarginy.socialnetwork.dao.entities.phones;

import com.getjavajob.training.yarginy.socialnetwork.common.entities.NullEntitiesFactory;
import com.getjavajob.training.yarginy.socialnetwork.common.entities.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.entities.phone.Phone;
import com.getjavajob.training.yarginy.socialnetwork.common.entities.phone.PhoneImpl;
import com.getjavajob.training.yarginy.socialnetwork.common.entities.phone.additionaldata.PhoneType;
import com.getjavajob.training.yarginy.socialnetwork.common.exceptions.IncorrectDataException;
import com.getjavajob.training.yarginy.socialnetwork.dao.entities.AbstractDml;
import com.getjavajob.training.yarginy.socialnetwork.dao.entities.accounts.AccountDml;
import com.getjavajob.training.yarginy.socialnetwork.dao.entities.accounts.AccountsTable;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import static com.getjavajob.training.yarginy.socialnetwork.dao.entities.phones.PhonesTable.*;
import static com.getjavajob.training.yarginy.socialnetwork.dao.utils.querybuilder.SqlQueryBuilder.buildQuery;
import static java.util.Objects.isNull;

public class PhonesDml extends AbstractDml<Phone> {
    public static final String SELECT_ALL = buildQuery().selectJoin(TABLE, AccountsTable.TABLE, OWNER, AccountsTable.ID).
            build();
    public static final String SELECT_UPDATE = buildQuery().select(TABLE).where(NUMBER).build();
    public static final String SELECT_BY_ID = buildQuery().selectJoin(TABLE, AccountsTable.TABLE, OWNER,
            AccountsTable.ID).where(ID).build();
    public static final String SELECT_BY_NUMBER = buildQuery().selectJoin(TABLE, AccountsTable.TABLE, OWNER,
            AccountsTable.ID).where(NUMBER).build();
    private final AccountDml accountDml = new AccountDml();

    @Override
    protected String getUpdatableSelect() {
        return SELECT_UPDATE;
    }

    @Override
    protected String getSelectAll() {
        return SELECT_ALL;
    }

    @Override
    protected String getSelectById() {
        return SELECT_BY_ID;
    }

    @Override
    protected String getSelectByIdentifier() {
        return SELECT_BY_NUMBER;
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
        resultSet.updateLong(OWNER, phone.getOwner().getId());
    }

    @Override
    public Phone getNullEntity() {
        return NullEntitiesFactory.getNullPhone();
    }
}
