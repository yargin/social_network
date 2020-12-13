package com.getjavajob.training.yarginy.socialnetwork.dao;

import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.AccountImpl;
import com.getjavajob.training.yarginy.socialnetwork.common.models.password.Password;
import com.getjavajob.training.yarginy.socialnetwork.common.models.password.PasswordImpl;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.AccountDaoFacade;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.PasswordDaoFacade;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:daoSpringConfig.xml", "classpath:daoOverrideSpringConfig.xml"})
public class PasswordDaoFacadeTest {
    private static final Account ACCOUNT = new AccountImpl();
    private static final Password PASSWORD = new PasswordImpl();
    @Autowired
    private AccountDaoFacade accountDaoFacade;
    @Autowired
    private PasswordDaoFacade passwordDaoFacade;

    @Before
    public void initTestValues() {
        ACCOUNT.setEmail("password@test.com");
        ACCOUNT.setName("testAccount");
        ACCOUNT.setSurname("testSurname");
        accountDaoFacade.delete(ACCOUNT);
        assert accountDaoFacade.create(ACCOUNT);
        PASSWORD.setAccount(ACCOUNT);
        PASSWORD.setPassword("qwe123rty");
    }

    @After
    public void deleteTestValues() {
        assert accountDaoFacade.delete(ACCOUNT);
    }

    @Test
    public void testCreate() {
        assertTrue(passwordDaoFacade.create(PASSWORD));
    }

    @Test
    public void testCreateExisting() {
        accountDaoFacade.create(ACCOUNT);
        passwordDaoFacade.create(PASSWORD);
        assertFalse(passwordDaoFacade.create(PASSWORD));
    }

    @Test
    public void testUpdate() {
        accountDaoFacade.create(ACCOUNT);
        passwordDaoFacade.create(PASSWORD);
        PASSWORD.setPassword("updatedPassword1");
        //mistake because select by both
        assertTrue(passwordDaoFacade.update(PASSWORD, passwordDaoFacade.select(PASSWORD)));
        PASSWORD.setPassword("qwe123rty");
    }

    @Test
    public void testUpdateNonExisting() {
        PASSWORD.setAccount(new AccountImpl("petya", "fake@email.com"));
        assertFalse(passwordDaoFacade.update(PASSWORD, passwordDaoFacade.select(PASSWORD)));
        PASSWORD.setAccount(ACCOUNT);
    }

    @Test
    public void testSelect() {
        accountDaoFacade.create(ACCOUNT);
        passwordDaoFacade.create(PASSWORD);
        assertEquals(PASSWORD, passwordDaoFacade.select(PASSWORD));
    }

    @Test
    public void testSelectNonExisting() {
        Password nonExistingPassword = new PasswordImpl();
        nonExistingPassword.setAccount(new AccountImpl("Petr", "nonexisting@email.com"));
        nonExistingPassword.setPassword("nonExisting1");
        assertEquals(passwordDaoFacade.getNullPassword(), passwordDaoFacade.select(nonExistingPassword));
    }
}
