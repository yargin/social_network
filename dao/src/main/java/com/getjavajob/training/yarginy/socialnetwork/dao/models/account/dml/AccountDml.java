package com.getjavajob.training.yarginy.socialnetwork.dao.models.account.dml;

import com.getjavajob.training.yarginy.socialnetwork.dao.models.EntityDml;
import com.getjavajob.training.yarginy.socialnetwork.dao.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.dao.models.account.AccountImpl;
import com.getjavajob.training.yarginy.socialnetwork.dao.models.group.Group;
import com.getjavajob.training.yarginy.socialnetwork.dao.models.group.sql.GroupDml;
import com.getjavajob.training.yarginy.socialnetwork.dao.models.group.sql.Groups;
import com.getjavajob.training.yarginy.socialnetwork.dao.models.relations.AccountsInGroups;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

import static com.getjavajob.training.yarginy.socialnetwork.dao.models.account.additionaldata.Sex.valueOf;
import static com.getjavajob.training.yarginy.socialnetwork.dao.utils.querybuilder.SqlQueryBuilder.buildQuery;
import static java.util.Objects.isNull;

public class AccountDml extends EntityDml<Account> {
    private static final String SELECT_ALL = buildQuery().select(Accounts.TABLE).build();
    private static final String SELECT_BY_ID = buildQuery().select(Accounts.TABLE).where(Accounts.ID).build();
    private static final String SELECT_BY_EMAIL = buildQuery().select(Accounts.TABLE).where(Accounts.EMAIL).build();
    private static final String SELECT_GROUPS_BY_MEMBER = buildQuery().selectJoin(Groups.TABLE, AccountsInGroups.TABLE,
            Groups.ID, AccountsInGroups.GROUP_ID).where(AccountsInGroups.ACCOUNT_ID).build();
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
            account.setSex(valueOf(resultSet.getString(Accounts.SEX)));
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
        resultSet.updateString(Accounts.NAME, account.getName());
        resultSet.updateString(Accounts.SURNAME, account.getSurname());
        resultSet.updateString(Accounts.PATRONYMIC, account.getPatronymic());
        if (!isNull(account.getSex())) {
            resultSet.updateString(Accounts.SEX, account.getSex().toString());
        }
        if (!isNull(account.getBirthDate())) {
            resultSet.updateDate(Accounts.BIRTH_DATE, Date.valueOf(account.getBirthDate()));
        }
        resultSet.updateString(Accounts.PHONE, account.getPhone());
        resultSet.updateString(Accounts.ADDITIONAL_PHONE, account.getAdditionalPhone());
        resultSet.updateString(Accounts.ICQ, account.getIcq());
        resultSet.updateString(Accounts.SKYPE, account.getSkype());
        resultSet.updateString(Accounts.EMAIL, account.getEmail());
        resultSet.updateString(Accounts.ADDITIONAL_EMAIL, account.getAdditionalEmail());
        resultSet.updateString(Accounts.COUNTRY, account.getCountry());
        resultSet.updateString(Accounts.CITY, account.getCity());
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

    public Collection<Group> selectGroupsByMember(Connection connection, Account account) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(SELECT_GROUPS_BY_MEMBER)) {
            statement.setInt(1, account.getId());
            try (ResultSet resultSet = statement.executeQuery()) {
                EntityDml<Group> groupSql = new GroupDml();
                return groupSql.selectEntities(resultSet);
            }
        }
    }

    public Collection<Group> selectGroupsByOwner(Connection connection, Account account) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(SELECT_GROUPS_BY_OWNER)) {
            statement.setInt(1, account.getId());
            try (ResultSet resultSet = statement.executeQuery()) {
                EntityDml<Group> groupSql = new GroupDml();
                return groupSql.selectEntities(resultSet);
            }
        }
    }
}
