package com.getjavajob.training.yarginy.socialnetwork.dao;

import com.getjavajob.training.yarginy.socialnetwork.dao.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.dao.account.AccountDAO;
import com.getjavajob.training.yarginy.socialnetwork.dao.dbfactories.DbFactory;
import com.getjavajob.training.yarginy.socialnetwork.dao.dbfactories.DmlQueriesExecutor;
import com.getjavajob.training.yarginy.socialnetwork.dao.dbfactories.databases.H2Factory;
import com.getjavajob.training.yarginy.socialnetwork.dao.account.dto.AccountImpl;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

public class AccountDAOTest {
    private static final DbFactory dbFactory = new H2Factory();
    private static final String CLASS = "AccountDAOTest.";
    private static final String PASSED = "() passed";
    private static final DmlQueriesExecutor dmlQueriesExecutor = dbFactory.getDmlQueriesExecutor();
    private static final AccountDAO accountDAO = dbFactory.getAccountDao();
    private static final Account account = new AccountImpl();

    public AccountDAOTest() {
        account.setEmail("asd@asd");
        account.setName("Vasya");
        account.setSurname("Pupkin");
        account.setPhone("666");
        account.setId(1);
    }

    @BeforeClass
    public static void createTableAccounts() {
        dmlQueriesExecutor.createAccounts();
    }

    @AfterClass
    public static void dropTableAccounts() {
        dmlQueriesExecutor.dropAccounts();
    }

    @Test
    public void testCreateAccount() {
        accountDAO.deleteAccount(account);
        boolean actual = accountDAO.createAccount(account);
        assertSame(true, actual);
        System.out.println(CLASS + "testCreateAccount" + PASSED);
    }

    @Test
    public void testCreateExistingAccount() {
        accountDAO.createAccount(account);
        boolean actual = accountDAO.createAccount(account);
        assertSame(false, actual);
        System.out.println(CLASS + "testCreateExistingAccount" + PASSED);
    }

    @Test
    public void testSelectAccount() {
        accountDAO.createAccount(account);
        Account actual = accountDAO.selectAccount(account.getEmail());
        assertEquals(account, actual);
        actual = accountDAO.selectAccount(actual.getId());
        assertEquals(account, actual);
        System.out.println(CLASS + "testSelectAccount" + PASSED);
    }

    @Test
    public void testSelectNonExistingAccount() {
        accountDAO.deleteAccount(account);
        Account actual = accountDAO.selectAccount("non existing email");
        assertEquals(Account.getNullAccount(), actual);
        actual = accountDAO.selectAccount(123);
        assertEquals(Account.getNullAccount(), actual);
        System.out.println(CLASS + "testSelectNonExistingAccount" + PASSED);
    }

    @Test
    public void testUpdateAccount() {
        accountDAO.createAccount(account);
        String newPatronymic = "new Patronymic";
        account.setPatronymic(newPatronymic);
        boolean actual = accountDAO.updateAccount(account);
        assertSame(true, actual);
        Account storageAccount = accountDAO.selectAccount(account.getEmail());
        assertEquals(newPatronymic, storageAccount.getPatronymic());
        System.out.println(CLASS + "testUpdateAccount" + PASSED);
    }

    @Test
    public void testUpdateNonExistingAccount() {
        Account nonExisting = new AccountImpl();
        boolean actual = accountDAO.updateAccount(nonExisting);
        assertSame(false, actual);
        System.out.println(CLASS + "testUpdateNonExistingAccount" + PASSED);
    }

    @Test
    public void testDeleteNonExisting() {
        Account nonExisting = new AccountImpl();
        boolean actual = accountDAO.deleteAccount(nonExisting);
        assertSame(false, actual);
        System.out.println(CLASS + "testDeleteNonExisting" + PASSED);
    }

    @Test
    public void testDeleteAccount() {
        accountDAO.createAccount(account);
        boolean actual = accountDAO.deleteAccount(account);
        assertSame(true, actual);
        Account nonExisting = Account.getNullAccount();
        assertEquals(nonExisting, accountDAO.selectAccount(account.getEmail()));
        System.out.println(CLASS + "testDeleteAccount" + PASSED);
    }
}
