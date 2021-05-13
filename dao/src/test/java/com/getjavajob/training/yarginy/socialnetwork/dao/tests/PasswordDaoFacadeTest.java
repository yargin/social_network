package com.getjavajob.training.yarginy.socialnetwork.dao.tests;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.Password;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.AccountDaoFacade;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.PasswordDaoFacade;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:daoSpringConfig.xml", "classpath:daoTestOverrideSpringConfig.xml"})
public class PasswordDaoFacadeTest {
    private final Account account = new Account("testName", "testSurname", "password@test.com");
    private final Password password = new Password();
    @Autowired
    private AccountDaoFacade accountDaoFacade;
    @Autowired
    private PasswordDaoFacade passwordDaoFacade;

    @Before
    public void initTestValues() {
        accountDaoFacade.create(account);
        password.setAccount(account);
        password.setStringPassword("qwe123rty");
    }

    @After
    public void deleteTestValues() {
        passwordDaoFacade.delete(password);
        accountDaoFacade.delete(account);
    }

    @Test
    public void testCreate() {
        password.setAccount(account);
        assertTrue(passwordDaoFacade.create(password));
    }

    @Test
    public void testCreateExisting() {
        accountDaoFacade.create(account);
        passwordDaoFacade.create(password);
        Password passwordd = passwordDaoFacade.select(password);
        passwordDaoFacade.select(password);
        assertFalse(passwordDaoFacade.create(password));
    }

    @Test
    public void testSelect() {
        accountDaoFacade.create(account);
        password.setAccount(account);
        assertTrue(passwordDaoFacade.create(password));
        Account testAccount = new Account();
        testAccount.setEmail(account.getEmail());
        Password testPassword = new Password();
        testPassword.setAccount(testAccount);
        System.out.println("============");
        Password created = passwordDaoFacade.select(testPassword);
        System.out.println("==============");
        assertEquals(password, created);
    }

    @Test
    public void testSelectNonExisting() {
        Password nonExistingPassword = new Password();
        nonExistingPassword.setAccount(new Account("Petr", "nonexisting@email.com"));
        nonExistingPassword.setStringPassword("nonExisting1");
        assertEquals(passwordDaoFacade.getNullPassword(), passwordDaoFacade.select(nonExistingPassword));
    }
}