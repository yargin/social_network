package com.getjavajob.training.yarginy.socialnetwork.dao;

import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.AccountImpl;
import com.getjavajob.training.yarginy.socialnetwork.common.models.password.Password;
import com.getjavajob.training.yarginy.socialnetwork.common.models.password.PasswordImpl;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.AccountFacade;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.PasswordFacade;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

public class PasswordFacadeTest {
    private static final Account ACCOUNT = new AccountImpl();
    private static final Password PASSWORD = new PasswordImpl();
    @Autowired
    private static AccountFacade accountFacade;
    @Autowired
    private PasswordFacade passwordFacade;

    @BeforeClass
    public static void initTestValues() {
        ACCOUNT.setEmail("password@test.com");
        ACCOUNT.setName("testAccount");
        ACCOUNT.setSurname("testSurname");
        accountFacade.delete(ACCOUNT);
        assert accountFacade.create(ACCOUNT);
        PASSWORD.setAccount(ACCOUNT);
        PASSWORD.setPassword("qwe123rty");
    }

    @AfterClass
    public static void deleteTestValues() {
        assert accountFacade.delete(ACCOUNT);
    }

    @Test
    public void testCreate() {
        assertTrue(passwordFacade.create(PASSWORD));
    }

    @Test
    public void testCreateExisting() {
        accountFacade.create(ACCOUNT);
        passwordFacade.create(PASSWORD);
        assertFalse(passwordFacade.create(PASSWORD));
    }

    @Test
    public void testUpdate() {
        accountFacade.create(ACCOUNT);
        passwordFacade.create(PASSWORD);
        PASSWORD.setPassword("updatedPassword1");
        //mistake because select by both
        assertTrue(passwordFacade.update(PASSWORD, passwordFacade.select(PASSWORD)));
        PASSWORD.setPassword("qwe123rty");
    }

    @Test
    public void testUpdateNonExisting() {
        PASSWORD.setAccount(new AccountImpl("petya", "fake@email.com"));
        assertFalse(passwordFacade.update(PASSWORD, passwordFacade.select(PASSWORD)));
        PASSWORD.setAccount(ACCOUNT);
    }

    @Test
    public void testSelect() {
        accountFacade.create(ACCOUNT);
        passwordFacade.create(PASSWORD);
        assertEquals(PASSWORD, passwordFacade.select(PASSWORD));
    }

    @Test
    public void testSelectNonExisting() {
        Password nonExistingPassword = new PasswordImpl();
        nonExistingPassword.setAccount(new AccountImpl("Petr", "nonexisting@email.com"));
        nonExistingPassword.setPassword("nonExisting1");
        assertEquals(passwordFacade.getNullPassword(), passwordFacade.select(nonExistingPassword));
    }
}
