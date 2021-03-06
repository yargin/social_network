package com.getjavajob.training.yarginy.socialnetwork.dao.tests;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.Password;
import com.getjavajob.training.yarginy.socialnetwork.dao.configuration.TestDaoOverrideConfig;
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
@ContextConfiguration(classes = {TestDaoOverrideConfig.class})
public class PasswordDaoFacadeTest {
    private final Account account = new Account("testName", "testSurname", "password@test.com");
    private final Password password = new Password();
    @Autowired
    private AccountDaoFacade accountDaoFacade;
    @Autowired
    private PasswordDaoFacade passwordDaoFacade;

    @Before
    public void initTestValues() {
        account.setCity("will not be fetched by password");
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
        passwordDaoFacade.create(password);
        Password passwordd = passwordDaoFacade.select(account.getId());
        assertFalse(passwordDaoFacade.create(passwordd));
    }

    @Test
    public void testSelect() {
//        assertTrue(passwordDaoFacade.create(password));
//        password = passwordDaoFacade.select(new Password(account, password.getStringPassword()));
//        Password actual = passwordDaoFacade.select(new Password(account, password.getStringPassword()));
//        assertEquals(password, actual);
//        actual = passwordDaoFacade.select(actual.getId());
//        assertEquals(password, actual);
//        accountDaoFacade.create(account);
        password.setAccount(account);
        assertTrue(passwordDaoFacade.create(password));
        Password expected = new Password(account, password.getStringPassword());
        Password actual = passwordDaoFacade.select(password);
        assertEquals(expected, actual);
        actual = passwordDaoFacade.select(password.getId());
        assertEquals(expected, actual);
    }

    @Test
    public void testSelectNonExisting() {
        Password nonExistingPassword = new Password();
        nonExistingPassword.setAccount(new Account("Petr", "nonexisting@email.com"));
        nonExistingPassword.setStringPassword("nonExisting1");
        assertEquals(passwordDaoFacade.getNullPassword(), passwordDaoFacade.select(nonExistingPassword));
    }
}
