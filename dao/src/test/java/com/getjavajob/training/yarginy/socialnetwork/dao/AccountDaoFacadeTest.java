package com.getjavajob.training.yarginy.socialnetwork.dao;

import com.getjavajob.training.yarginy.socialnetwork.common.exceptions.IncorrectDataException;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.AccountImpl;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.AccountDaoFacade;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:daoSpringConfig.xml", "classpath:daoOverrideSpringConfig.xml"})
public class AccountDaoFacadeTest {
    private static final Account ACCOUNT = new AccountImpl();
    @Autowired
    private AccountDaoFacade accountDaoFacade;

    @After
    public void deleteTestValues() {
        accountDaoFacade.delete(ACCOUNT);
    }

    @Before
    public void accountInit() {
        ACCOUNT.setEmail("test@test.test");
        ACCOUNT.setName("test");
        ACCOUNT.setSurname("test");
    }

    @Test
    public void testCreateAccount() {
        accountDaoFacade.delete(ACCOUNT);
        boolean actual;
        try {
            ACCOUNT.setEmail(null);
            actual = accountDaoFacade.create(ACCOUNT);
        } catch (IncorrectDataException e) {
            actual = false;
        }
        assertSame(false, actual);
        try {
            ACCOUNT.setEmail("");
            actual = accountDaoFacade.create(ACCOUNT);
        } catch (IncorrectDataException e) {
            actual = false;
        }
        assertFalse(actual);
        ACCOUNT.setEmail("test@test.test");
        actual = accountDaoFacade.create(ACCOUNT);
        assertTrue(actual);
    }

    @Test
    public void testCreateExistingAccount() {
        accountDaoFacade.create(ACCOUNT);
        assertFalse(accountDaoFacade.create(ACCOUNT));
    }

    @Test
    public void testSelectAccount() {
        accountDaoFacade.create(ACCOUNT);
        Account actual = accountDaoFacade.select(ACCOUNT);
        assertEquals(ACCOUNT, actual);
        actual = accountDaoFacade.select(actual);
        assertEquals(ACCOUNT, actual);
    }

    @Test
    public void testSelectNonExistingAccount() {
        accountDaoFacade.delete(ACCOUNT);
        Account actual = accountDaoFacade.select(accountDaoFacade.getNullEntity());
        assertEquals(accountDaoFacade.getNullEntity(), actual);
        actual = accountDaoFacade.select(actual);
        assertEquals(accountDaoFacade.getNullEntity(), actual);
    }

    @Test
    public void testUpdateAccount() {
        accountDaoFacade.create(ACCOUNT);
        String newPatronymic = "new Patronymic";
        ACCOUNT.setPatronymic(newPatronymic);
        Account storedAccount = accountDaoFacade.select(ACCOUNT);
        boolean actual = accountDaoFacade.update(ACCOUNT, storedAccount);
        assertTrue(actual);
        Account storageAccount = accountDaoFacade.select(ACCOUNT);
        assertEquals(newPatronymic, storageAccount.getPatronymic());
    }

    @Test
    public void testUpdateNonExistingAccount() {
        Account nonExisting = new AccountImpl();
        nonExisting.setEmail("email@that.doesnt.exist");
        assertFalse(accountDaoFacade.update(nonExisting, nonExisting));
    }

    @Test
    public void testDeleteNonExisting() {
        Account nonExisting = new AccountImpl();
        nonExisting.setEmail("testEmail@that.doesnt.exist");
        assertFalse(accountDaoFacade.delete(nonExisting));
    }

    @Test
    public void testDeleteAccount() {
        accountDaoFacade.create(ACCOUNT);
        assertTrue(accountDaoFacade.delete(ACCOUNT));
        assertEquals(accountDaoFacade.getNullEntity(), accountDaoFacade.select(ACCOUNT));
    }
}
