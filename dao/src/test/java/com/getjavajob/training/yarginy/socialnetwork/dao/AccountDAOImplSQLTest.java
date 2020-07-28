package com.getjavajob.training.yarginy.socialnetwork.dao;

import com.getjavajob.training.yarginy.socialnetwork.dao.entities.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.dao.entities.account.AccountDAO;
import com.getjavajob.training.yarginy.socialnetwork.dao.entities.account.dao.AccountDAOImplSQL;
import org.junit.Test;

public class AccountDAOImplSQLTest {
    @Test
    public void testRetrieveAccount() {
        AccountDAO accountDAO = new AccountDAOImplSQL();
        Account account = accountDAO.retrieveAccount(1);
    }
}
