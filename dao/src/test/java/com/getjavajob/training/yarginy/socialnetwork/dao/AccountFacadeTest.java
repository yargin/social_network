package com.getjavajob.training.yarginy.socialnetwork.dao;

import com.getjavajob.training.yarginy.socialnetwork.common.exceptions.IncorrectDataException;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.AccountImpl;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.AccountFacade;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

public class AccountFacadeTest {
    static {
        TestDataSourceInitializer.initDataSource();
    }

    private static final Account ACCOUNT = new AccountImpl();
    @Autowired
    private static AccountFacade accountFacade;

    @After
    public static void deleteTestValues() {
        accountFacade.delete(ACCOUNT);
    }

    @Before
    public void accountInit() {
        ACCOUNT.setEmail("test@test.test");
        ACCOUNT.setName("test");
        ACCOUNT.setSurname("test");
    }

    @Test
    public void testCreateAccount() {
        accountFacade.delete(ACCOUNT);
        boolean actual;
        try {
            ACCOUNT.setEmail(null);
            actual = accountFacade.create(ACCOUNT);
        } catch (IncorrectDataException e) {
            actual = false;
        }
        assertSame(false, actual);
        try {
            ACCOUNT.setEmail("");
            actual = accountFacade.create(ACCOUNT);
        } catch (IncorrectDataException e) {
            actual = false;
        }
        assertFalse(actual);
        ACCOUNT.setEmail("test@test.test");
        actual = accountFacade.create(ACCOUNT);
        assertTrue(actual);
    }

    @Test
    public void testCreateExistingAccount() {
        accountFacade.create(ACCOUNT);
        assertFalse(accountFacade.create(ACCOUNT));
    }

    @Test
    public void testSelectAccount() {
        accountFacade.create(ACCOUNT);
        Account actual = accountFacade.select(ACCOUNT);
        assertEquals(ACCOUNT, actual);
        actual = accountFacade.select(actual);
        assertEquals(ACCOUNT, actual);
    }

    @Test
    public void testSelectNonExistingAccount() {
        accountFacade.delete(ACCOUNT);
        Account actual = accountFacade.select(accountFacade.getNullEntity());
        assertEquals(accountFacade.getNullEntity(), actual);
        actual = accountFacade.select(actual);
        assertEquals(accountFacade.getNullEntity(), actual);
    }

    @Test
    public void testUpdateAccount() {
        accountFacade.create(ACCOUNT);
        String newPatronymic = "new Patronymic";
        ACCOUNT.setPatronymic(newPatronymic);
        Account storedAccount = accountFacade.select(ACCOUNT);
        boolean actual = accountFacade.update(ACCOUNT, storedAccount);
        assertTrue(actual);
        Account storageAccount = accountFacade.select(ACCOUNT);
        assertEquals(newPatronymic, storageAccount.getPatronymic());
    }

    @Test
    public void testUpdateNonExistingAccount() {
        Account nonExisting = new AccountImpl();
        nonExisting.setEmail("email@that.doesnt.exist");
        assertFalse(accountFacade.update(nonExisting, nonExisting));
    }

    @Test
    public void testDeleteNonExisting() {
        Account nonExisting = new AccountImpl();
        nonExisting.setEmail("testEmail@that.doesnt.exist");
        assertFalse(accountFacade.delete(nonExisting));
    }

    @Test
    public void testDeleteAccount() {
        accountFacade.create(ACCOUNT);
        assertTrue(accountFacade.delete(ACCOUNT));
        assertEquals(accountFacade.getNullEntity(), accountFacade.select(ACCOUNT));
    }
}
