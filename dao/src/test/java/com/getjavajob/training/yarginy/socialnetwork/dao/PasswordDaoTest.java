package com.getjavajob.training.yarginy.socialnetwork.dao;

import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.AccountImpl;
import com.getjavajob.training.yarginy.socialnetwork.common.models.password.Password;
import com.getjavajob.training.yarginy.socialnetwork.common.models.password.PasswordImpl;
import com.getjavajob.training.yarginy.socialnetwork.dao.factories.AbstractDbFactory;
import com.getjavajob.training.yarginy.socialnetwork.dao.factories.DbFactory;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.Dao;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static com.getjavajob.training.yarginy.socialnetwork.dao.utils.TestResultPrinter.printPassed;
import static org.junit.Assert.*;

public class PasswordDaoTest {
    private static final DbFactory DB_FACTORY = AbstractDbFactory.getDbFactory();
    private static final String CLASS = "PasswordDaoTest";
    private static final Dao<Password> PASSWORD_DAO = DB_FACTORY.getPasswordDao();
    private static final Dao<Account> ACCOUNT_DAO = DB_FACTORY.getAccountDao();
    private static final Account ACCOUNT = new AccountImpl();
    private static final Password PASSWORD = new PasswordImpl();

    @BeforeClass
    public static void initTestData() {
        ACCOUNT.setEmail("password@test.com");
        ACCOUNT.setName("testAccount");
        ACCOUNT.setSurname("testSurname");
        ACCOUNT_DAO.delete(ACCOUNT);
        assert ACCOUNT_DAO.create(ACCOUNT);
        PASSWORD.setAccount(ACCOUNT);
        PASSWORD.setPassword("qwe123rty");
    }

    @AfterClass
    public static void deleteTestData() {
        assert ACCOUNT_DAO.delete(ACCOUNT);
    }

    @Test
    public void testCreate() {
        assertTrue(PASSWORD_DAO.create(PASSWORD));
        printPassed(CLASS, "testCreate");
    }

    @Test
    public void testCreateExisting() {
        ACCOUNT_DAO.create(ACCOUNT);
        PASSWORD_DAO.create(PASSWORD);
        assertFalse(PASSWORD_DAO.create(PASSWORD));
        printPassed(CLASS, "testCreateExisting");
    }

    @Test
    public void testUpdate() {
        ACCOUNT_DAO.create(ACCOUNT);
        PASSWORD_DAO.create(PASSWORD);
        PASSWORD.setPassword("updatedPassword1");
        //mistake because select by both
        assertTrue(PASSWORD_DAO.update(PASSWORD, PASSWORD_DAO.select(PASSWORD)));
        printPassed(CLASS, "testUpdate");
        PASSWORD.setPassword("qwe123rty");
    }

    @Test
    public void testUpdateNonExisting() {
        PASSWORD.setAccount(new AccountImpl("petya", "fake@email.com"));
        assertFalse(PASSWORD_DAO.update(PASSWORD, PASSWORD_DAO.select(PASSWORD)));
        printPassed(CLASS, "testUpdateNonExisting");
        PASSWORD.setAccount(ACCOUNT);
    }

    @Test
    public void testSelect() {
        ACCOUNT_DAO.create(ACCOUNT);
        PASSWORD_DAO.create(PASSWORD);
        assertEquals(PASSWORD, PASSWORD_DAO.select(PASSWORD));
        printPassed(CLASS, "testSelect");
    }

    @Test
    public void testSelectNonExisting() {
        Password nonExistingPassword = new PasswordImpl();
        nonExistingPassword.setAccount(new AccountImpl("Petr", "nonexisting@email.com"));
        nonExistingPassword.setPassword("nonExisting1");
        assertEquals(PASSWORD_DAO.getNullEntity(), PASSWORD_DAO.select(nonExistingPassword));
        printPassed(CLASS, "testSelectNonExisting");
    }
}
