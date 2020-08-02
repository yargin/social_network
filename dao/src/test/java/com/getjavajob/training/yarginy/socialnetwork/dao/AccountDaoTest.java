package com.getjavajob.training.yarginy.socialnetwork.dao;

import com.getjavajob.training.yarginy.socialnetwork.dao.factories.DbFactory;
import com.getjavajob.training.yarginy.socialnetwork.dao.factories.databases.H2DbFactory;
import com.getjavajob.training.yarginy.socialnetwork.dao.factories.dml.DmlExecutor;
import com.getjavajob.training.yarginy.socialnetwork.dao.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.dao.models.account.AccountDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.models.account.AccountImpl;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

public class AccountDaoTest {
    private static final DbFactory dbFactory = new H2DbFactory();
    private static final String CLASS = "AccountDaoTest.";
    private static final String PASSED = "() passed";
    private static final AccountDao accountDAO = dbFactory.getAccountDao();
    private static final DmlExecutor dmlExecutor = dbFactory.getDmlExecutor();
    private static final Account account = new AccountImpl();

    public AccountDaoTest() {
        account.setEmail("asd@asd");
        account.setName("Vasya");
        account.setSurname("Pupkin");
        account.setPhone("666");
        account.setId(1);
    }

    @BeforeClass
    public static void createTableAccounts() {
        dmlExecutor.createTables();
    }

    @AfterClass
    public static void dropTableAccounts() {
        dmlExecutor.dropTables();
    }

    @Test
    public void testCreateAccount() {
        accountDAO.delete(account);
        boolean actual = accountDAO.create(account);
        assertSame(true, actual);
        System.out.println(CLASS + "testCreateAccount" + PASSED);
    }

    @Test
    public void testCreateExistingAccount() {
        accountDAO.create(account);
        boolean actual = accountDAO.create(account);
        assertSame(false, actual);
        System.out.println(CLASS + "testCreateExistingAccount" + PASSED);
    }

    @Test
    public void testSelectAccount() {
        accountDAO.create(account);
        Account actual = accountDAO.select(account.getEmail());
        assertEquals(account, actual);
        actual = accountDAO.select(actual.getId());
        assertEquals(account, actual);
        System.out.println(CLASS + "testSelectAccount" + PASSED);
    }

    @Test
    public void testSelectNonExistingAccount() {
        accountDAO.delete(account);
        Account actual = accountDAO.select("non existing email");
        assertEquals(accountDAO.getNullEntity(), actual);
        actual = accountDAO.select(123);
        assertEquals(accountDAO.getNullEntity(), actual);
        System.out.println(CLASS + "testSelectNonExistingAccount" + PASSED);
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
        System.out.println(CLASS + "testUpdateAccount" + PASSED);
    }

    @Test
    public void testUpdateNonExistingAccount() {
        Account nonExisting = new AccountImpl();
        boolean actual = accountDAO.update(nonExisting);
        assertSame(false, actual);
        System.out.println(CLASS + "testUpdateNonExistingAccount" + PASSED);
    }

    @Test
    public void testDeleteNonExisting() {
        Account nonExisting = new AccountImpl();
        boolean actual = accountDAO.delete(nonExisting);
        assertSame(false, actual);
        System.out.println(CLASS + "testDeleteNonExisting" + PASSED);
    }

    @Test
    public void testDeleteAccount() {
        accountDAO.create(account);
        boolean actual = accountDAO.delete(account);
        assertSame(true, actual);
        Account nonExisting = accountDAO.getNullEntity();
        assertEquals(nonExisting, accountDAO.select(account.getEmail()));
        System.out.println(CLASS + "testDeleteAccount" + PASSED);
    }
}
