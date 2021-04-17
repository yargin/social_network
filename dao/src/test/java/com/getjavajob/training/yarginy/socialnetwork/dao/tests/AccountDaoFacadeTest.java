package com.getjavajob.training.yarginy.socialnetwork.dao.tests;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Account;
import com.getjavajob.training.yarginy.socialnetwork.dao.models.concretedaos.AccountDao;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:daoSpringConfig.xml", "classpath:daoTestOverrideSpringConfig.xml"})
public class AccountDaoFacadeTest {
    private Account account = new Account();
    @Autowired
    private AccountDao accountDao;

    @After
    public void deleteTestValues() {
        accountDao.delete(accountDao.select(account));
    }

    @Before
    public void accountInit() {
        account.setEmail("test@test.test");
        account.setName("test");
        account.setSurname("test");
//        accountDao.delete(accountDao.select(account));
    }

    @Test
    public void testCreateAccount() {
        boolean created;
        try {
            account.setEmail(null);
            created = accountDao.create(account);
        } catch (IllegalArgumentException e) {
            created = false;
        }
        assertFalse(created);
        account = new Account("test", "test", "test@test.test");
        created = accountDao.create(account);
        assertTrue(created);
    }

    @Test
    public void testCreateExistingAccount() {
        accountDao.create(account);
        assertFalse(accountDao.create(account));
    }

    @Test
    public void testSelectAccount() {
        accountDao.create(account);
        Account actual = accountDao.select(account.getId());
        assertEquals(account, actual);
        actual = accountDao.select(actual);
        assertEquals(account, actual);
    }

    @Test
    public void testSelectNonExistingAccount() {
        account = accountDao.select(account);
        accountDao.delete(account);
        Account actual = accountDao.select(accountDao.getNullModel());
        assertEquals(accountDao.getNullModel(), actual);
        actual = accountDao.select(actual);
        assertEquals(accountDao.getNullModel(), actual);
    }

    @Test
    public void testUpdateAccount() {
        accountDao.create(account);
        String newPatronymic = "new Patronymic";
        account.setPatronymic(newPatronymic);
        boolean actual = accountDao.update(account);
        assertTrue(actual);
        Account storageAccount = accountDao.select(account);
        assertEquals(newPatronymic, storageAccount.getPatronymic());
    }

    @Test
    public void testUpdateNonExistingAccount() {
        Account nonExisting = new Account();
        nonExisting.setEmail("email@that.doesnt.exist");
        assertFalse(accountDao.update(nonExisting));
    }

    @Test
    public void testDeleteNonExisting() {
        Account nonExisting = new Account();
        nonExisting.setEmail("testEmail@that.doesnt.exist");
        assertFalse(accountDao.delete(nonExisting));
    }

    @Test
    public void testDeleteAccount() {
        accountDao.create(account);
        assertTrue(accountDao.delete(account));
        assertEquals(accountDao.getNullModel(), accountDao.select(account));
    }

    @Test
    public void testSelectAll() {
        accountDao.create(account);
        Collection<Account> accounts = accountDao.selectAll();
        assertTrue(accounts.size() > 0);
        assertTrue(accounts.contains(account));
    }
}
