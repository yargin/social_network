package com.getjavajob.training.yarginy.socialnetwork.dao.entities.phones;

import com.getjavajob.training.yarginy.socialnetwork.common.entities.NullEntitiesFactory;
import com.getjavajob.training.yarginy.socialnetwork.common.entities.phone.Phone;
import com.getjavajob.training.yarginy.socialnetwork.common.entities.phone.PhoneImpl;
import com.getjavajob.training.yarginy.socialnetwork.common.entities.phone.additionaldata.PhoneType;
import com.getjavajob.training.yarginy.socialnetwork.common.exceptions.IncorrectDataException;
import com.getjavajob.training.yarginy.socialnetwork.dao.entities.AbstractDml;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import static com.getjavajob.training.yarginy.socialnetwork.dao.entities.phones.PhonesTable.*;
import static com.getjavajob.training.yarginy.socialnetwork.dao.utils.querybuilder.SqlQueryBuilder.buildQuery;
import static java.util.Objects.isNull;

public class PhonesDml extends AbstractDml<Phone> {
    public static final String SELECT_ALL = buildQuery().select(TABLE).build();
    public static final String SELECT_BY_ID = buildQuery().select(TABLE).where(ID).build();
    public static final String SELECT_BY_NUMBER = buildQuery().select(TABLE).where(NUMBER).build();

    @Override
    public PreparedStatement getSelectStatement(Connection connection, long id) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID, ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_UPDATABLE);
        statement.setLong(1, id);
        return statement;
    }

    @Override
    public PreparedStatement getSelectStatement(Connection connection, String identifier) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(SELECT_BY_NUMBER, ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_UPDATABLE);
        statement.setString(1, identifier);
        return statement;
    }

    @Override
    public Phone selectFromRow(ResultSet resultSet) throws SQLException {
        Phone phone = new PhoneImpl();
        phone.setId(resultSet.getInt(ID));
        phone.setNumber(resultSet.getString(NUMBER));
        if (!isNull(resultSet.getString(TYPE))) {
            phone.setType(PhoneType.valueOf(resultSet.getString(TYPE)));
        }
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
    public String getSelectAllQuery() {
        return SELECT_ALL;
    }

    @Override
    public Phone getNullEntity() {
        return NullEntitiesFactory.getNullPhone();
    }
}
