package com.getjavajob.training.yarginy.socialnetwork.dao;

import com.getjavajob.training.yarginy.socialnetwork.dao.factories.AbstractDbFactory;
import com.getjavajob.training.yarginy.socialnetwork.dao.factories.DbFactory;
import com.getjavajob.training.yarginy.socialnetwork.dao.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.dao.models.account.AccountDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.models.account.AccountImpl;
import org.junit.Test;

import static com.getjavajob.training.yarginy.socialnetwork.dao.utils.ResultPrinter.printPassed;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

public class AccountDaoTest {
    private static final DbFactory dbFactory = AbstractDbFactory.getDbFactory();
    private static final String CLASS = "AccountDaoTest";
    private static final AccountDao accountDAO = dbFactory.getAccountDao();
    private static final Account account = new AccountImpl();

    public AccountDaoTest() {
        account.setEmail("email@site");
        account.setName("Vasya");
        account.setSurname("Pupkin");
        account.setPhone("no phone(");
    }

    @Test
    public void testCreateAccount() {
        accountDAO.delete(account);
        boolean actual = accountDAO.create(account);
        assertSame(true, actual);
        printPassed(CLASS, "testCreateAccount");
    }

    @Test(expected = IllegalStateException.class)
    public void testNullFieldCreate() {
        accountDAO.delete(account);
        account.setEmail(null);
        accountDAO.create(account);
        printPassed(CLASS, "testNullFieldCreate");
        account.setEmail("email@site");
    }

    @Test
    public void testCreateExistingAccount() {
        accountDAO.create(account);
        boolean actual = accountDAO.create(account);
        assertSame(false, actual);
        printPassed(CLASS, "testCreateExistingAccount");
    }

    @Test
    public void testSelectAccount() {
        accountDAO.create(account);
        Account actual = accountDAO.select(account.getEmail());
        assertEquals(account, actual);
        actual = accountDAO.select(actual.getId());
        assertEquals(account, actual);
        printPassed(CLASS, "testSelectAccount");
    }

    @Test
    public void testSelectNonExistingAccount() {
        accountDAO.delete(account);
        Account actual = accountDAO.select("non existing email");
        assertEquals(accountDAO.getNullEntity(), actual);
        actual = accountDAO.select(123);
        assertEquals(accountDAO.getNullEntity(), actual);
        printPassed(CLASS, "testSelectNonExistingAccount");
    }

    @Test
    public void testUpdateAccount() {
        accountDAO.create(account);
        String newPatronymic = "new Patronymic";
        account.setPatronymic(newPatronymic);
        boolean actual = accountDAO.update(account);
        assertSame(true, actual);
        Account storageAccount = accountDAO.select(account.getEmail());
        assertEquals(newPatronymic, storageAccount.getPatronymic());
        printPassed(CLASS, "testUpdateAccount");
    }

    @Test
    public void testUpdateNonExistingAccount() {
        Account nonExisting = new AccountImpl();
        boolean actual = accountDAO.update(nonExisting);
        assertSame(false, actual);
        printPassed(CLASS, "testUpdateNonExistingAccount");
    }

    @Test
    public void testDeleteNonExisting() {
        Account nonExisting = new AccountImpl();
        boolean actual = accountDAO.delete(nonExisting);
        assertSame(false, actual);
        printPassed(CLASS, "testDeleteNonExisting");
    }

    @Test
    public void testDeleteAccount() {
        accountDAO.create(account);
        boolean actual = accountDAO.delete(account);
        assertSame(true, actual);
        Account nonExisting = accountDAO.getNullEntity();
        assertEquals(nonExisting, accountDAO.select(account.getEmail()));
        printPassed(CLASS, "testDeleteAccount");
    }
}
