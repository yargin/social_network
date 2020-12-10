package com.getjavajob.training.yarginy.socialnetwork.dao;

import com.getjavajob.training.yarginy.socialnetwork.common.exceptions.IncorrectDataException;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.AccountImpl;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.AccountFacade;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.AccountFacadeImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class AccountFacadeTest {
    static {
        TestDataSourceInitializer.initDataSource();
    }

    private static final AccountFacade ACCOUNT_DAO = new AccountFacadeImpl();
    private static final Account ACCOUNT = new AccountImpl();

    @After
    public static void deleteTestValues() {
        ACCOUNT_DAO.delete(ACCOUNT);
    }

    @Before
    public void accountInit() {
        ACCOUNT.setEmail("test@test.test");
        ACCOUNT.setName("test");
        ACCOUNT.setSurname("test");
    }

    @Test
    public void testCreateAccount() {
        ACCOUNT_DAO.delete(ACCOUNT);
        boolean actual;
        try {
            ACCOUNT.setEmail(null);
            actual = ACCOUNT_DAO.create(ACCOUNT);
        } catch (IncorrectDataException e) {
            actual = false;
        }
        assertSame(false, actual);
        try {
            ACCOUNT.setEmail("");
            actual = ACCOUNT_DAO.create(ACCOUNT);
        } catch (IncorrectDataException e) {
            actual = false;
        }
        assertFalse(actual);
        ACCOUNT.setEmail("test@test.test");
        actual = ACCOUNT_DAO.create(ACCOUNT);
        assertTrue(actual);
    }

    @Test
    public void testCreateExistingAccount() {
        ACCOUNT_DAO.create(ACCOUNT);
        assertFalse(ACCOUNT_DAO.create(ACCOUNT));
    }

    @Test
    public void testSelectAccount() {
        ACCOUNT_DAO.create(ACCOUNT);
        Account actual = ACCOUNT_DAO.select(ACCOUNT);
        assertEquals(ACCOUNT, actual);
        actual = ACCOUNT_DAO.select(actual);
        assertEquals(ACCOUNT, actual);
    }

    @Test
    public void testSelectNonExistingAccount() {
        ACCOUNT_DAO.delete(ACCOUNT);
        Account actual = ACCOUNT_DAO.select(ACCOUNT_DAO.getNullEntity());
        assertEquals(ACCOUNT_DAO.getNullEntity(), actual);
        actual = ACCOUNT_DAO.select(actual);
        assertEquals(ACCOUNT_DAO.getNullEntity(), actual);
    }

    @Test
    public void testUpdateAccount() {
        ACCOUNT_DAO.create(ACCOUNT);
        String newPatronymic = "new Patronymic";
        ACCOUNT.setPatronymic(newPatronymic);
        Account storedAccount = ACCOUNT_DAO.select(ACCOUNT);
        boolean actual = ACCOUNT_DAO.update(ACCOUNT, storedAccount);
        assertTrue(actual);
        Account storageAccount = ACCOUNT_DAO.select(ACCOUNT);
        assertEquals(newPatronymic, storageAccount.getPatronymic());
    }

    @Test
    public void testUpdateNonExistingAccount() {
        Account nonExisting = new AccountImpl();
        nonExisting.setEmail("email@that.doesnt.exist");
        assertFalse(ACCOUNT_DAO.update(nonExisting, nonExisting));
    }

    @Test
    public void testDeleteNonExisting() {
        Account nonExisting = new AccountImpl();
        nonExisting.setEmail("testEmail@that.doesnt.exist");
        assertFalse(ACCOUNT_DAO.delete(nonExisting));
    }

    @Test
    public void testDeleteAccount() {
        ACCOUNT_DAO.create(ACCOUNT);
        assertTrue(ACCOUNT_DAO.delete(ACCOUNT));
        assertEquals(ACCOUNT_DAO.getNullEntity(), ACCOUNT_DAO.select(ACCOUNT));
    }
}
