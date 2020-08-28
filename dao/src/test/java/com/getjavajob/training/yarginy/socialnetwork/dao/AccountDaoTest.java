package com.getjavajob.training.yarginy.socialnetwork.dao;

import com.getjavajob.training.yarginy.socialnetwork.common.entities.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.entities.account.AccountImpl;
import com.getjavajob.training.yarginy.socialnetwork.common.exceptions.IncorrectDataException;
import com.getjavajob.training.yarginy.socialnetwork.dao.entities.Dao;
import com.getjavajob.training.yarginy.socialnetwork.dao.factories.AbstractDbFactory;
import com.getjavajob.training.yarginy.socialnetwork.dao.factories.DbFactory;
import org.junit.Before;
import org.junit.Test;

import static com.getjavajob.training.yarginy.socialnetwork.dao.utils.TestResultPrinter.printPassed;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

public class AccountDaoTest {
    private static final DbFactory DB_FACTORY = AbstractDbFactory.getDbFactory();
    private static final String CLASS = "AccountDaoTest";
    private static final Dao<Account> ACCOUNT_DAO = DB_FACTORY.getAccountDao();
    private static final Account ACCOUNT = new AccountImpl();

    @Before
    public void accountInit() {
        ACCOUNT.setEmail("email@site.site");
        ACCOUNT.setName("Vasya");
        ACCOUNT.setSurname("Pupkin");
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
        printPassed(CLASS, "testCreateAccount");
    }

    @Test
    public void testCreateExistingAccount() {
        ACCOUNT_DAO.create(ACCOUNT);
        boolean actual = ACCOUNT_DAO.create(ACCOUNT);
        assertSame(false, actual);
        printPassed(CLASS, "testCreateExistingAccount");
    }

    @Test
    public void testSelectAccount() {
        ACCOUNT_DAO.create(ACCOUNT);
        Account actual = ACCOUNT_DAO.select(ACCOUNT.getEmail());
        assertEquals(ACCOUNT, actual);
        actual = ACCOUNT_DAO.select(actual.getId());
        assertEquals(ACCOUNT, actual);
        printPassed(CLASS, "testSelectAccount");
    }

    @Test
    public void testSelectNonExistingAccount() {
        ACCOUNT_DAO.delete(ACCOUNT);
        Account actual = ACCOUNT_DAO.select("non existing email");
        assertEquals(ACCOUNT_DAO.getNullEntity(), actual);
        actual = ACCOUNT_DAO.select(123);
        assertEquals(ACCOUNT_DAO.getNullEntity(), actual);
        printPassed(CLASS, "testSelectNonExistingAccount");
    }

    @Test
    public void testUpdateAccount() {
        ACCOUNT_DAO.create(ACCOUNT);
        String newPatronymic = "new Patronymic";
        ACCOUNT.setPatronymic(newPatronymic);
        boolean actual = ACCOUNT_DAO.update(ACCOUNT);
        assertSame(true, actual);
        Account storageAccount = ACCOUNT_DAO.select(ACCOUNT.getEmail());
        assertEquals(newPatronymic, storageAccount.getPatronymic());
        printPassed(CLASS, "testUpdateAccount");
    }

    @Test
    public void testUpdateNonExistingAccount() {
        Account nonExisting = new AccountImpl();
        nonExisting.setEmail("email@that.doesnt.exist");
        boolean actual = ACCOUNT_DAO.update(nonExisting);
        assertSame(false, actual);
        printPassed(CLASS, "testUpdateNonExistingAccount");
    }

    @Test
    public void testDeleteNonExisting() {
        Account nonExisting = new AccountImpl();
        nonExisting.setEmail("email@that.doesnt.exist");
        boolean actual;
        actual = ACCOUNT_DAO.delete(nonExisting);
        assertSame(false, actual);
        printPassed(CLASS, "testDeleteNonExisting");
    }

    @Test
    public void testDeleteAccount() {
        ACCOUNT_DAO.create(ACCOUNT);
        boolean actual = ACCOUNT_DAO.delete(ACCOUNT);
        assertSame(true, actual);
        assertEquals(ACCOUNT_DAO.getNullEntity(), ACCOUNT_DAO.select(ACCOUNT.getEmail()));
        printPassed(CLASS, "testDeleteAccount");
    }
}
