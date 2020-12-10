package com.getjavajob.training.yarginy.socialnetwork.dao;

import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.AccountImpl;
import com.getjavajob.training.yarginy.socialnetwork.common.models.password.Password;
import com.getjavajob.training.yarginy.socialnetwork.common.models.password.PasswordImpl;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.AccountFacade;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.AccountFacadeImpl;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.PasswordFacade;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.PasswordFacadeImpl;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class PasswordFacadeTest {
    private static final PasswordFacade PASSWORD_DAO = new PasswordFacadeImpl();
    private static final AccountFacade ACCOUNT_DAO = new AccountFacadeImpl();
    private static final Account ACCOUNT = new AccountImpl();
    private static final Password PASSWORD = new PasswordImpl();

    @BeforeClass
    public static void initTestValues() {
        ACCOUNT.setEmail("password@test.com");
        ACCOUNT.setName("testAccount");
        ACCOUNT.setSurname("testSurname");
        ACCOUNT_DAO.delete(ACCOUNT);
        assert ACCOUNT_DAO.create(ACCOUNT);
        PASSWORD.setAccount(ACCOUNT);
        PASSWORD.setPassword("qwe123rty");
    }

    @AfterClass
    public static void deleteTestValues() {
        assert ACCOUNT_DAO.delete(ACCOUNT);
    }

    @Test
    public void testCreate() {
        assertTrue(PASSWORD_DAO.create(PASSWORD));
    }

    @Test
    public void testCreateExisting() {
        ACCOUNT_DAO.create(ACCOUNT);
        PASSWORD_DAO.create(PASSWORD);
        assertFalse(PASSWORD_DAO.create(PASSWORD));
    }

    @Test
    public void testUpdate() {
        ACCOUNT_DAO.create(ACCOUNT);
        PASSWORD_DAO.create(PASSWORD);
        PASSWORD.setPassword("updatedPassword1");
        //mistake because select by both
        assertTrue(PASSWORD_DAO.update(PASSWORD, PASSWORD_DAO.select(PASSWORD)));
        PASSWORD.setPassword("qwe123rty");
    }

    @Test
    public void testUpdateNonExisting() {
        PASSWORD.setAccount(new AccountImpl("petya", "fake@email.com"));
        assertFalse(PASSWORD_DAO.update(PASSWORD, PASSWORD_DAO.select(PASSWORD)));
        PASSWORD.setAccount(ACCOUNT);
    }

    @Test
    public void testSelect() {
        ACCOUNT_DAO.create(ACCOUNT);
        PASSWORD_DAO.create(PASSWORD);
        assertEquals(PASSWORD, PASSWORD_DAO.select(PASSWORD));
    }

    @Test
    public void testSelectNonExisting() {
        Password nonExistingPassword = new PasswordImpl();
        nonExistingPassword.setAccount(new AccountImpl("Petr", "nonexisting@email.com"));
        nonExistingPassword.setPassword("nonExisting1");
        assertEquals(PASSWORD_DAO.getNullPassword(), PASSWORD_DAO.select(nonExistingPassword));
    }
}
