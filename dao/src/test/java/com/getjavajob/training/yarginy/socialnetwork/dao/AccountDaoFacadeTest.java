package com.getjavajob.training.yarginy.socialnetwork.dao;

import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.AccountDaoFacade;
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
@ContextConfiguration(locations = {"classpath:daoSpringConfig.xml", "classpath:daoOverrideSpringConfig.xml"})
public class AccountDaoFacadeTest {
    private Account account = new Account();
    @Autowired
    private AccountDaoFacade accountDaoFacade;

    @After
    public void deleteTestValues() {
        accountDaoFacade.delete(accountDaoFacade.select(account));
    }

    @Before
    public void accountInit() {
        account.setEmail("test@test.test");
        account.setName("test");
        account.setSurname("test");
        accountDaoFacade.delete(accountDaoFacade.select(account));
    }

    @Test
    public void testCreateAccount() {
        boolean created;
        try {
            account.setEmail(null);
            created = accountDaoFacade.create(account);
        } catch (IllegalArgumentException e) {
            created = false;
        }
        assertFalse(created);
        account.setEmail("test@test.test");
        created = accountDaoFacade.create(account);
        assertTrue(created);
    }

    @Test
    public void testCreateExistingAccount() {
        accountDaoFacade.create(account);
        assertFalse(accountDaoFacade.create(account));
    }

    @Test
    public void testSelectAccount() {
        accountDaoFacade.create(account);
        Account actual = accountDaoFacade.select(account);
        assertEquals(account, actual);
        actual = accountDaoFacade.select(actual);
        assertEquals(account, actual);
    }

    @Test
    public void testSelectNonExistingAccount() {
        account = accountDaoFacade.select(account);
        accountDaoFacade.delete(account);
        Account actual = accountDaoFacade.select(accountDaoFacade.getNullModel());
        assertEquals(accountDaoFacade.getNullModel(), actual);
        actual = accountDaoFacade.select(actual);
        assertEquals(accountDaoFacade.getNullModel(), actual);
    }

    @Test
    public void testUpdateAccount() {
        accountDaoFacade.create(account);
        String newPatronymic = "new Patronymic";
        account.setPatronymic(newPatronymic);
        Account storedAccount = accountDaoFacade.select(account);
        boolean actual = accountDaoFacade.update(account, storedAccount);
        assertTrue(actual);
        Account storageAccount = accountDaoFacade.select(account);
        assertEquals(newPatronymic, storageAccount.getPatronymic());
    }

    @Test
    public void testUpdateNonExistingAccount() {
        Account nonExisting = new Account();
        nonExisting.setEmail("email@that.doesnt.exist");
        Account anotherNonExisting = new Account();
        anotherNonExisting.setEmail("anotheremail@that.doesnt.exist");
        assertFalse(accountDaoFacade.update(nonExisting, anotherNonExisting));
    }

    @Test
    public void testDeleteNonExisting() {
        Account nonExisting = new Account();
        nonExisting.setEmail("testEmail@that.doesnt.exist");
        assertFalse(accountDaoFacade.delete(nonExisting));
    }

    @Test
    public void testDeleteAccount() {
        accountDaoFacade.create(account);
        account = accountDaoFacade.select(account);
        assertTrue(accountDaoFacade.delete(account));
        assertEquals(accountDaoFacade.getNullModel(), accountDaoFacade.select(account));
    }

    @Test
    public void testSelectAll() {
        accountDaoFacade.create(account);
        Collection<Account> accounts = accountDaoFacade.selectAll();
        assertTrue(accounts.size() > 0);
        assertTrue(accounts.contains(account));
    }
}
