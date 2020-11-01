package com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.dmls;

import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.AccountImpl;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.additionaldata.Role;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.additionaldata.Sex;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.AbstractDml;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import static com.getjavajob.training.yarginy.socialnetwork.common.models.NullEntitiesFactory.getNullAccount;
import static com.getjavajob.training.yarginy.socialnetwork.dao.tables.AccountsTable.*;
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
    protected String getSelectAll() {
        return SELECT_ALL;
    }

    @Override
    protected String getSelectByAltKey() {
        return SELECT_BY_EMAIL;
    }

    @Override
    protected String getSelectForUpdate() {
        return SELECT_BY_EMAIL;
    }

    @Override
    protected void setAltKeyParams(PreparedStatement statement, Account account) throws SQLException {
        statement.setString(1, account.getEmail());
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
            account.setBirthDate(resultSet.getDate(BIRTH_DATE));
        }
        if (!isNull(resultSet.getDate(REGISTRATION_DATE))) {
            account.setRegistrationDate(resultSet.getDate(REGISTRATION_DATE));
        }
        account.setEmail(resultSet.getString(EMAIL));
        account.setAdditionalEmail(resultSet.getString(ADDITIONAL_EMAIL));
        if (!isNull(resultSet.getString(ROLE))) {
            account.setRole(Role.valueOf(resultSet.getString(ROLE)));
        }
        account.setIcq(resultSet.getString(ICQ));
        account.setSkype(resultSet.getString(SKYPE));
        account.setCity(resultSet.getString(CITY));
        account.setCountry(resultSet.getString(COUNTRY));
        return account;
    }

    @Override
    public void updateRow(ResultSet resultSet, Account account, Account storedAccount) throws SQLException {
        updateFieldIfDiffers(account::getName, storedAccount::getName, resultSet::updateString, NAME);
        updateFieldIfDiffers(account::getSurname, storedAccount::getSurname, resultSet::updateString, SURNAME);
        updateFieldIfDiffers(account::getPatronymic, storedAccount::getPatronymic, resultSet::updateString, PATRONYMIC);
        updateFieldIfDiffers(account::getSex, storedAccount::getSex, resultSet::updateString, SEX, Sex::toString);
        updateFieldIfDiffers(account::getBirthDate, storedAccount::getBirthDate, resultSet::updateDate, BIRTH_DATE);
        updateFieldIfDiffers(account::getRegistrationDate, storedAccount::getRegistrationDate, resultSet::updateDate,
                REGISTRATION_DATE);
        updateFieldIfDiffers(account::getRole, storedAccount::getRole, resultSet::updateString, ROLE, Role::toString);
        updateFieldIfDiffers(account::getIcq, storedAccount::getIcq, resultSet::updateString, ICQ);
        updateFieldIfDiffers(account::getSkype, storedAccount::getSkype, resultSet::updateString, SKYPE);
        updateFieldIfDiffers(account::getEmail, storedAccount::getEmail, resultSet::updateString, EMAIL);
        updateFieldIfDiffers(account::getAdditionalEmail, storedAccount::getAdditionalEmail, resultSet::updateString,
                ADDITIONAL_EMAIL);
        updateFieldIfDiffers(account::getCountry, storedAccount::getCountry, resultSet::updateString, COUNTRY);
        updateFieldIfDiffers(account::getCity, storedAccount::getCity, resultSet::updateString, CITY);
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
