package com.getjavajob.training.yarginy.socialnetwork.dao.entities.accounts;

import com.getjavajob.training.yarginy.socialnetwork.common.entities.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.entities.account.AccountImpl;
import com.getjavajob.training.yarginy.socialnetwork.common.entities.account.additionaldata.Sex;
import com.getjavajob.training.yarginy.socialnetwork.dao.entities.AbstractDml;
import com.getjavajob.training.yarginy.socialnetwork.dao.entities.groups.Groups;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

import static com.getjavajob.training.yarginy.socialnetwork.common.utils.StringHandler.*;
import static com.getjavajob.training.yarginy.socialnetwork.dao.utils.querybuilder.SqlQueryBuilder.buildQuery;
import static java.util.Objects.isNull;

public class AccountDml extends AbstractDml<Account> {
    private static final String SELECT_ALL = buildQuery().select(Accounts.TABLE).build();
    private static final String SELECT_BY_ID = buildQuery().select(Accounts.TABLE).where(Accounts.ID).build();
    private static final String SELECT_BY_EMAIL = buildQuery().select(Accounts.TABLE).where(Accounts.EMAIL).build();
    private static final String SELECT_GROUPS_BY_OWNER = buildQuery().select(Groups.TABLE).where(Groups.OWNER).build();

    @Override
    public String getSelectAllQuery() {
        return SELECT_ALL;
    }

    @Override
    public PreparedStatement getSelectStatement(Connection connection, int id) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID, ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_UPDATABLE);
        statement.setInt(1, id);
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
        account.setId(resultSet.getInt(Accounts.ID));
        account.setName(resultSet.getString(Accounts.NAME));
        account.setSurname(resultSet.getString(Accounts.SURNAME));
        account.setPatronymic(resultSet.getString(Accounts.PATRONYMIC));
        if (!isNull(resultSet.getString(Accounts.SEX))) {
            account.setSex(Sex.valueOf(resultSet.getString(Accounts.SEX)));
        }
        if (!isNull(resultSet.getDate(Accounts.BIRTH_DATE))) {
            account.setBirthDate(resultSet.getDate(Accounts.BIRTH_DATE).toLocalDate());
        }
        account.setPhone(resultSet.getString(Accounts.PHONE));
        account.setAdditionalPhone(resultSet.getString(Accounts.ADDITIONAL_PHONE));
        account.setEmail(resultSet.getString(Accounts.EMAIL));
        account.setAdditionalEmail(resultSet.getString(Accounts.ADDITIONAL_EMAIL));
        account.setIcq(resultSet.getString(Accounts.ICQ));
        account.setSkype(resultSet.getString(Accounts.SKYPE));
        account.setCity(resultSet.getString(Accounts.CITY));
        account.setCountry(resultSet.getString(Accounts.COUNTRY));
        return account;
    }

    @Override
    public void updateRow(ResultSet resultSet, Account account) throws SQLException {
        resultSet.updateString(Accounts.NAME, checkAndTrim(account.getName()));
        resultSet.updateString(Accounts.SURNAME, trimString(account.getSurname()));
        resultSet.updateString(Accounts.PATRONYMIC, trimString(account.getPatronymic()));
        if (!isNull(account.getSex())) {
            resultSet.updateString(Accounts.SEX, account.getSex().toString());
        }
        if (!isNull(account.getBirthDate())) {
            resultSet.updateDate(Accounts.BIRTH_DATE, Date.valueOf(account.getBirthDate()));
        }
        resultSet.updateString(Accounts.PHONE, checkAndTrim(account.getPhone()));
        resultSet.updateString(Accounts.ADDITIONAL_PHONE, trimString(account.getAdditionalPhone()));
        resultSet.updateString(Accounts.ICQ, trimString(account.getIcq()));
        resultSet.updateString(Accounts.SKYPE, trimString(account.getSkype()));
        resultSet.updateString(Accounts.EMAIL, checkAndPrepareEmail(account.getEmail()));
        resultSet.updateString(Accounts.ADDITIONAL_EMAIL, trimString(account.getAdditionalEmail()));
        resultSet.updateString(Accounts.COUNTRY, trimString(account.getCountry()));
        resultSet.updateString(Accounts.CITY, trimString(account.getCity()));
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
        Account nullAccount = new AccountImpl();
        nullAccount.setId(-1);
        nullAccount.setEmail("");
        return nullAccount;
    }

//    public Collection<Group> selectGroupsByOwner(Connection connection, Account account) throws SQLException {
//        try (PreparedStatement statement = connection.prepareStatement(SELECT_GROUPS_BY_OWNER)) {
//            statement.setInt(1, account.getId());
//            try (ResultSet resultSet = statement.executeQuery()) {
//                AbstractDml<Group> groupSql = new GroupDml();
//                return groupSql.selectEntities(resultSet);
//            }
//        }
//    }
}
