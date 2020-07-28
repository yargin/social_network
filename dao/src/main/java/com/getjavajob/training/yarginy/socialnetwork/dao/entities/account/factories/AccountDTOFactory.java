package com.getjavajob.training.yarginy.socialnetwork.dao.entities.account.factories;

import com.getjavajob.training.yarginy.socialnetwork.dao.entities.Builder;
import com.getjavajob.training.yarginy.socialnetwork.dao.entities.account.entity.Account;
import com.getjavajob.training.yarginy.socialnetwork.dao.entities.account.entity.AccountImpl;

import java.sql.Connection;

public class AccountDTOFactory implements AccountFactory {
    @Override
    public Account getInstance(int id, Connection connection) {
        return createAccount();
    }

    @Override
    public Builder<Account> getBuilder() {
        return new AccountImpl.AccountBuilderImpl();
    }

    private Account createAccount(int id, Connection connection) {
        return new AccountImpl(id, connection);
    }
}
