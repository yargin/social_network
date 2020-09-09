package com.getjavajob.training.yarginy.socialnetwork.dao.entities.accounts;

import com.getjavajob.training.yarginy.socialnetwork.common.entities.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.entities.account.AccountImpl;
import com.getjavajob.training.yarginy.socialnetwork.common.entities.account.additionaldata.Role;
import com.getjavajob.training.yarginy.socialnetwork.common.entities.account.additionaldata.Sex;
import com.getjavajob.training.yarginy.socialnetwork.dao.entities.AbstractDml;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
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
    protected String getSelectById() {
        return SELECT_BY_ID;
    }

    @Override
    protected String getSelectByIdentifier() {
        return SELECT_BY_EMAIL;
    }

    @Override
    protected String getUpdatableSelect() {
        return SELECT_BY_EMAIL;
    }

    @Override
    protected String getSelectAll() {
        return SELECT_ALL;
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
        if (!isNull(resultSet.getDate(REGISTRATION_DATE))) {
            account.setRegistrationDate(resultSet.getDate(REGISTRATION_DATE).toLocalDate());
        }
        account.setEmail(resultSet.getString(EMAIL));
        account.setAdditionalEmail(resultSet.getString(ADDITIONAL_EMAIL));
//        account.setPassword(resultSet.getString(PASSWORD));
        if (!isNull(resultSet.getString(ROLE))) {
            account.setSex(Sex.valueOf(resultSet.getString(ROLE)));
        }
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
        if (!isNull(account.getRole())) {
            resultSet.updateString(ROLE, account.getRole().toString());
        }
        if (!isNull(account.getBirthDate())) {
            resultSet.updateDate(BIRTH_DATE, Date.valueOf(account.getBirthDate()));
        }
        if (!isNull(account.getRegistrationDate())) {
            resultSet.updateDate(REGISTRATION_DATE, Date.valueOf(account.getRegistrationDate()));
        }
        resultSet.updateString(ICQ, account.getIcq());
        resultSet.updateString(SKYPE, account.getSkype());
        resultSet.updateString(EMAIL, account.getEmail());
        resultSet.updateString(ADDITIONAL_EMAIL, account.getAdditionalEmail());
//        resultSet.updateString(PASSWORD, account.getPassword());
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
