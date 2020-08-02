package com.getjavajob.training.yarginy.socialnetwork.dao.models.account.dao;

import com.getjavajob.training.yarginy.socialnetwork.dao.factories.connector.DbConnector;
import com.getjavajob.training.yarginy.socialnetwork.dao.models.AbstractEntityDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.dao.models.account.AccountDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.models.account.AccountImpl;
import com.getjavajob.training.yarginy.socialnetwork.dao.models.account.sql.AccountSql;
import com.getjavajob.training.yarginy.socialnetwork.dao.models.group.Group;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;

public class AccountDaoImpl extends AbstractEntityDao<Account> implements AccountDao {
    private final AccountSql accountSql;

    public AccountDaoImpl(DbConnector dbConnector, AccountSql accountSql) {
        super(dbConnector, accountSql);
        this.accountSql = accountSql;
    }

    @Override
    public Account getNullEntity() {
        Account nullAccount = new AccountImpl();
        nullAccount.setEmail("account not found");
        return nullAccount;
    }

    @Override
    public Collection<Group> selectOwnedGroups(Account account) {
        try (Connection connection = dbConnector.getConnection()) {
            return accountSql.selectGroupsByOwner(connection, account);
        } catch (SQLException e) {
            throw new IllegalStateException(e.getMessage());
        }
    }

    @Override
    public Collection<Group> selectJoinedGroups(Account account) {
        try (Connection connection = dbConnector.getConnection()) {
            return accountSql.selectGroupsByMember(connection, account);
        } catch (SQLException e) {
            throw new IllegalStateException(e.getMessage());
        }
    }

    @Override
    public boolean updateJoinedGroups(Account account) {
        throw new UnsupportedOperationException("not implemented yet");
    }
}
