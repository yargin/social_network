package com.getjavajob.training.yarginy.socialnetwork.dao.tests.jpa;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Account;
import com.getjavajob.training.yarginy.socialnetwork.dao.configuration.TestDaoOverrideConfig;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.AccountDaoFacadeImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestDaoOverrideConfig.class})
public class TransactionsTest {
    @Autowired
    private AccountDaoFacadeImpl accountDao;

    @Test
    @Transactional
    public void testCreateAccount() {
        Account account = new Account("test", "test", "test");
        accountDao.create(account);
        Account created = accountDao.select(account);
        if (accountDao.create(created)) {
            created = accountDao.select(created);
        }
        assertEquals(account, created);
    }
}
