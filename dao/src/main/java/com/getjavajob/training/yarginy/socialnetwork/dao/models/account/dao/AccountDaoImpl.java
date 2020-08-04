package com.getjavajob.training.yarginy.socialnetwork.dao.models.account.dao;

import com.getjavajob.training.yarginy.socialnetwork.dao.factories.connector.DbConnector;
import com.getjavajob.training.yarginy.socialnetwork.dao.models.AbstractEntityDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.dao.models.account.AccountDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.models.account.AccountImpl;
import com.getjavajob.training.yarginy.socialnetwork.dao.models.account.dml.AccountDml;
import com.getjavajob.training.yarginy.socialnetwork.dao.models.group.Group;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;

public class AccountDaoImpl extends AbstractEntityDao<Account> implements AccountDao {
    private final AccountDml accountDml;

    public AccountDaoImpl(DbConnector dbConnector, AccountDml accountDml) {
        super(dbConnector, accountDml);
        this.accountDml = accountDml;
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
            return accountDml.selectGroupsByOwner(connection, account);
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public boolean deleteOwnedGroup(Account account, Group group) {
        throw new UnsupportedOperationException("not implemented yet");
    }

    @Override
    public Collection<Group> selectJoinedGroups(Account account) {
        try (Connection connection = dbConnector.getConnection()) {
            return accountDml.selectGroupsByMember(connection, account);
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public boolean joinGroup(Account account, Group group) {
        throw new UnsupportedOperationException("not implemented yet");
    }

    @Override
    public boolean leaveGroup(Account account, Group group) {
        throw new UnsupportedOperationException("not implemented yet");
    }
}
