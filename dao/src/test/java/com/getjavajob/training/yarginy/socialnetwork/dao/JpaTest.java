package com.getjavajob.training.yarginy.socialnetwork.dao;

import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.AccountDao;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:daoSpringConfig.xml", "classpath:daoOverrideSpringConfig.xml"})
public class JpaTest {
    private static final Account ACCOUNT = new Account("testName", "testSurname", "testEmail");
    private static final Account FISRT_ACCOUNT = new Account("testtName", "testSSurname", "testEEmail");
    private static final MapSqlParameterSource PARAMETER_SOURCE = new MapSqlParameterSource();
    @Autowired
    private AccountDao accountDao;
    @Autowired
    private SimpleJdbcInsert insert;

    @BeforeClass
    public static void init() {
        PARAMETER_SOURCE.addValue("name", "testName");
        PARAMETER_SOURCE.addValue("surname", "testSurname");
        PARAMETER_SOURCE.addValue("email", "testEmail");
    }

    @Before
    public void initValues() {
        insert.setTableName("Accounts");
        try {
            insert.setGeneratedKeyName("id");
            long id = insert.executeAndReturnKey(PARAMETER_SOURCE).longValue();
            ACCOUNT.setId(id);
        } catch (DuplicateKeyException ignore) {
        }
    }

    @Test
    public void testSelectAll() {
        Collection<Account> accounts = accountDao.selectAll();
        assertTrue(accounts.size() > 0);
    }

    @Test
    public void testGetAccountById() {
        Account selectedAccount = accountDao.select(1);
        assertEquals(ACCOUNT, selectedAccount);
        selectedAccount = accountDao.select(22);
        assertEquals(accountDao.getNullModel(), selectedAccount);
    }

    @Test
    public void testGetAccountByIdentifier() {
        assertEquals(ACCOUNT, accountDao.select(ACCOUNT));
        assertEquals(accountDao.getNullModel(), accountDao.select(new Account("newTestAccount", "")));
    }

    @Test
    public void testDeleteAccount() {
        assertTrue(accountDao.delete(ACCOUNT));
        Collection<Account> accounts = accountDao.selectAll();
        assertEquals(0, accounts.size());
        assertFalse(accountDao.delete(ACCOUNT));
    }

    @Test
    public void testCreateAccount() {
        Account account = new Account("newAcc", "newAcc", "newAcc");
        assertTrue(accountDao.create(account));
        assertEquals(account, accountDao.select(account));
        assertFalse(accountDao.create(account));
    }
}
