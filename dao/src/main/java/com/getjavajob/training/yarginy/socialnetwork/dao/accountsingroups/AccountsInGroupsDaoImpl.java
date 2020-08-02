package com.getjavajob.training.yarginy.socialnetwork.dao.accountsingroups;

import com.getjavajob.training.yarginy.socialnetwork.dao.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.dao.dbfactories.DbConnector;
import com.getjavajob.training.yarginy.socialnetwork.dao.group.Group;

import java.sql.SQLException;
import java.util.List;

import static com.getjavajob.training.yarginy.socialnetwork.dao.account.dao.sql.AccountSQLQueriesHandler.selectById;

public class AccountsInGroupsDaoImpl implements AccountsInGroupsDao {
    private final DbConnector dbConnector;

    public AccountsInGroupsDaoImpl(DbConnector dbConnector) {
        this.dbConnector = dbConnector;
    }

    @Override
    public List<Account> selectAccounts(int groupId) {
        try {
            return selectById(dbConnector.getConnection(), groupId);
        } catch (SQLException e) {
            throw new IllegalStateException(e.getMessage());
        }
    }

    @Override
    public List<Group> selectGroups(int accountId) {
        return null;
    }

    @Override
    public List<Account> updateAccounts(int groupId) {
        return null;
    }

    @Override
    public List<Group> updateGroups(int accountId) {
        return null;
    }
}
