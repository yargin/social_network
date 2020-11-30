package com.getjavajob.training.yarginy.socialnetwork.dao;

import com.getjavajob.training.yarginy.socialnetwork.common.exceptions.IncorrectDataException;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.AccountImpl;
import com.getjavajob.training.yarginy.socialnetwork.dao.factories.AbstractDbFactory;
import com.getjavajob.training.yarginy.socialnetwork.dao.factories.DbFactory;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.Dao;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class AccountDaoTest {
    private static final DbFactory DB_FACTORY = AbstractDbFactory.getDbFactory();
    private static final Dao<Account> ACCOUNT_DAO = DB_FACTORY.getAccountDao();
    private static final Account ACCOUNT = new AccountImpl();

    @Before
    public void accountInit() {
        ACCOUNT.setEmail("email@site.site");
        ACCOUNT.setName("Vasya");
        ACCOUNT.setSurname("Pupkin");
    }

    @AfterClass
    public static void deleteValues() {
        ACCOUNT_DAO.delete(ACCOUNT);
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
        assertSame(false, actual);
        ACCOUNT.setEmail("email@site.site");
        actual = ACCOUNT_DAO.create(ACCOUNT);
        assertSame(true, actual);
    }

    @Test
    public void testCreateExistingAccount() {
        ACCOUNT_DAO.create(ACCOUNT);
        boolean actual = ACCOUNT_DAO.create(ACCOUNT);
        assertSame(false, actual);
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
        assertSame(true, actual);
        Account storageAccount = ACCOUNT_DAO.select(ACCOUNT);
        assertEquals(newPatronymic, storageAccount.getPatronymic());
    }

    @Test
    public void testUpdateNonExistingAccount() {
        Account nonExisting = new AccountImpl();
        nonExisting.setEmail("email@that.doesnt.exist");
        boolean actual = ACCOUNT_DAO.update(nonExisting, nonExisting);
        assertSame(false, actual);
    }

    @Test
    public void testDeleteNonExisting() {
        Account nonExisting = new AccountImpl();
        nonExisting.setEmail("testEmail@that.doesnt.exist");
        boolean actual;
        actual = ACCOUNT_DAO.delete(nonExisting);
        assertSame(false, actual);
    }

    @Test
    public void testDeleteAccount() {
        ACCOUNT_DAO.create(ACCOUNT);
        assertTrue(ACCOUNT_DAO.delete(ACCOUNT));
        assertEquals(ACCOUNT_DAO.getNullEntity(), ACCOUNT_DAO.select(ACCOUNT));
    }
}
