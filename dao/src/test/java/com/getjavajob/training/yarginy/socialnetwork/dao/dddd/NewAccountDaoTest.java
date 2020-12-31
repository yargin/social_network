package com.getjavajob.training.yarginy.socialnetwork.dao.dddd;

import com.getjavajob.training.yarginy.socialnetwork.common.exceptions.IncorrectDataException;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.AccountImpl;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.Dao;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:daoSpringConfig.xml", "classpath:daoOverrideSpringConfig.xml"})
public class NewAccountDaoTest {
    private static final Account ACCOUNT = new AccountImpl();
    @Autowired
    @Qualifier("accountDao")
    private Dao<Account> accountDao;

    @After
    public void deleteTestValues() {
        accountDao.delete(ACCOUNT);
    }

    @Before
    public void accountInit() {
        ACCOUNT.setEmail("test@test.test");
        ACCOUNT.setName("test");
        ACCOUNT.setSurname("test");
    }

    @Test
    public void testCreateAccount() {
        accountDao.delete(ACCOUNT);
        boolean actual;
        try {
            ACCOUNT.setEmail(null);
            actual = accountDao.create(ACCOUNT);
        } catch (IncorrectDataException e) {
            actual = false;
        }
        assertSame(false, actual);
        try {
            ACCOUNT.setEmail("");
            actual = accountDao.create(ACCOUNT);
        } catch (IncorrectDataException e) {
            actual = false;
        }
        assertFalse(actual);
        ACCOUNT.setEmail("test@test.test");
        actual = accountDao.create(ACCOUNT);
        assertTrue(actual);
    }

    @Test
    public void testCreateExistingAccount() {
        accountDao.create(ACCOUNT);
        assertFalse(accountDao.create(ACCOUNT));
    }

    @Test
    public void testSelectAccount() {
        accountDao.create(ACCOUNT);
        Account actual = accountDao.select(ACCOUNT);
        assertEquals(ACCOUNT, actual);
        actual = accountDao.select(actual);
        assertEquals(ACCOUNT, actual);
    }

    @Test
    public void testSelectNonExistingAccount() {
        accountDao.delete(ACCOUNT);
        Account actual = accountDao.select(accountDao.getNullEntity());
        assertEquals(accountDao.getNullEntity(), actual);
        actual = accountDao.select(actual);
        assertEquals(accountDao.getNullEntity(), actual);
    }

    @Test
    public void testUpdateAccount() {
        accountDao.create(ACCOUNT);
        String newPatronymic = "new Patronymic";
        ACCOUNT.setPatronymic(newPatronymic);
        Account storedAccount = accountDao.select(ACCOUNT);
        boolean actual = accountDao.update(ACCOUNT, storedAccount);
        assertTrue(actual);
        Account savedAccount = accountDao.select(ACCOUNT);
        assertEquals(newPatronymic, savedAccount.getPatronymic());
    }

    @Test
    public void testUpdateNonExistingAccount() {
        Account nonExisting = new AccountImpl();
        nonExisting.setEmail("email@that.doesnt.exist");
        assertFalse(accountDao.update(nonExisting, nonExisting));
    }

    @Test
    public void testDeleteNonExisting() {
        Account nonExisting = new AccountImpl();
        nonExisting.setEmail("testEmail@that.doesnt.exist");
        assertFalse(accountDao.delete(nonExisting));
    }

    @Test
    public void testDeleteAccount() {
        accountDao.create(ACCOUNT);
        assertTrue(accountDao.delete(ACCOUNT));
        assertEquals(accountDao.getNullEntity(), accountDao.select(ACCOUNT));
    }
}

