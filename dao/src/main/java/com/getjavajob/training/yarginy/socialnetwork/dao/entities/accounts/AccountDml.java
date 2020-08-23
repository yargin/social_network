package com.getjavajob.training.yarginy.socialnetwork.dao.entities.accounts;

import com.getjavajob.training.yarginy.socialnetwork.common.entities.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.entities.account.AccountImpl;
import com.getjavajob.training.yarginy.socialnetwork.common.entities.account.additionaldata.Sex;
import com.getjavajob.training.yarginy.socialnetwork.dao.entities.AbstractDml;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

import static com.getjavajob.training.yarginy.socialnetwork.common.entities.NullEntitiesFactory.getNullAccount;
import static com.getjavajob.training.yarginy.socialnetwork.dao.entities.accounts.AccountsTable.*;
import static com.getjavajob.training.yarginy.socialnetwork.dao.utils.querybuilder.SqlQueryBuilder.buildQuery;
import static java.util.Objects.isNull;

public class AccountDml extends AbstractDml<Account> {
    private static final String SELECT_ALL = buildQuery().select(TABLE).build();
    private static final String SELECT_BY_ID = buildQuery().select(TABLE).where(ID).build();
    private static final String SELECT_BY_EMAIL = buildQuery().select(TABLE).where(EMAIL).build();

    @Override
    public String getSelectAllQuery() {
        return SELECT_ALL;
    }

    @Override
    public PreparedStatement getSelectStatement(Connection connection, long id) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID, ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_UPDATABLE);
        statement.setLong(1, id);
        return statement;
    }

    @Override
    public PreparedStatement getSelectStatement(Connection connection, String identifier) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(SELECT_BY_EMAIL, ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_UPDATABLE);
        statement.setString(1, identifier);
        return statement;
    }

    @Override
    public Account selectFromRow(ResultSet resultSet) throws SQLException {
        Account account = new AccountImpl();
        account.setId(resultSet.getLong(ID));
        account.setName(resultSet.getString(NAME));
        account.setSurname(resultSet.getString(SURNAME));
        account.setPatronymic(resultSet.getString(PATRONYMIC));
        if (!isNull(resultSet.getString(SEX))) {
            account.setSex(Sex.valueOf(resultSet.getString(SEX)));
        }
        if (!isNull(resultSet.getDate(BIRTH_DATE))) {
            account.setBirthDate(resultSet.getDate(BIRTH_DATE).toLocalDate());
        }
        account.setEmail(resultSet.getString(EMAIL));
        account.setAdditionalEmail(resultSet.getString(ADDITIONAL_EMAIL));
        account.setIcq(resultSet.getString(ICQ));
        account.setSkype(resultSet.getString(SKYPE));
        account.setCity(resultSet.getString(CITY));
        account.setCountry(resultSet.getString(COUNTRY));
        return account;
    }

    @Override
    public void updateRow(ResultSet resultSet, Account account) throws SQLException {
        resultSet.updateString(NAME, account.getName());
        resultSet.updateString(SURNAME, account.getSurname());
        resultSet.updateString(PATRONYMIC, account.getPatronymic());
        if (!isNull(account.getSex())) {
            resultSet.updateString(SEX, account.getSex().toString());
        }
        if (!isNull(account.getBirthDate())) {
            resultSet.updateDate(BIRTH_DATE, Date.valueOf(account.getBirthDate()));
        }
        resultSet.updateString(ICQ, account.getIcq());
        resultSet.updateString(SKYPE, account.getSkype());
        resultSet.updateString(EMAIL, account.getEmail());
        resultSet.updateString(ADDITIONAL_EMAIL, account.getAdditionalEmail());
        resultSet.updateString(COUNTRY, account.getCountry());
        resultSet.updateString(CITY, account.getCity());
    }

    @Override
    public Collection<Account> selectEntities(ResultSet resultSet) throws SQLException {
        Collection<Account> accounts = new ArrayList<>();
        while (resultSet.next()) {
            Account account = selectFromRow(resultSet);
            accounts.add(account);
        }
        return accounts;
    }

    @Override
    public Account getNullEntity() {
        return getNullAccount();
    }
}
