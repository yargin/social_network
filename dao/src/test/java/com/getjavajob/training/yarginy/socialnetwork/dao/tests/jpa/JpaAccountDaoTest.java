package com.getjavajob.training.yarginy.socialnetwork.dao.tests.jpa;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Account;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.AccountDaoFacade;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Collection;
import java.util.concurrent.atomic.AtomicBoolean;

import static java.lang.Thread.sleep;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:daoSpringConfig.xml", "classpath:daoTestOverrideSpringConfig.xml"})
public class JpaAccountDaoTest {
    private final Account account = new Account("testName", "testSurname", "testEmail");
    @Autowired
    private AccountDaoFacade accountDao;

    @Before
    public void initValues() {
        accountDao.create(account);
    }

    @After
    public void deleteValues() {
        accountDao.delete(account);
    }

    @Test
    public void testSelectAll() {
        Collection<Account> accounts = accountDao.selectAll();
        assertTrue(accounts.size() > 0);
    }

    @Test
    public void testGetAccountById() {
        Account selectedAccount = accountDao.select(account.getId());
        assertEquals(account, selectedAccount);
        selectedAccount = accountDao.select(22);
        assertEquals(accountDao.getNullModel(), selectedAccount);
    }

    @Test
    public void testGetAccountByIdentifier() {
        assertEquals(account, accountDao.select(account));
        assertEquals(accountDao.getNullModel(), accountDao.select(new Account("newTestAccount", "")));
    }

    @Test
    public void testDeleteAccount() {
        assertTrue(accountDao.delete(account));
        Collection<Account> accounts = accountDao.selectAll();
        assertEquals(0, accounts.size());
        assertFalse(accountDao.delete(account));
    }

    @Test
    public void testCreateAccount() {
        assertFalse(accountDao.create(account));
        Account newAccount = new Account("newAcc", "newAcc", "newAcc");
        assertTrue(accountDao.create(newAccount));
        assertEquals(newAccount, accountDao.select(newAccount));
        assertFalse(accountDao.create(newAccount));
        assertFalse(accountDao.create(new Account("newAcc", "newAcc", "newAcc")));
        Account newDuplicateEmailAccount = new Account("newAcc", "newAcc", "testEmail");
        assertFalse(accountDao.create(newDuplicateEmailAccount));
        assertTrue(accountDao.delete(newAccount));
    }

    @Test
    public void testUpdate() {
        AtomicBoolean lock = new AtomicBoolean(false);
        AtomicBoolean updated = new AtomicBoolean();
        long id = account.getId();
        Thread thread1 = new Thread(() -> {
            Account account1 = accountDao.select(id);
            account1.setCountry("Russia");
            accountDao.update(account1);
            lock.set(true);
        });
        Thread thread2 = new Thread(() -> {
            Account account2 = accountDao.select(id);
            while (!lock.get()) {
                try {
                    sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            account2.setCity("SPb");
            try {
                accountDao.update(account2);
                updated.set(true);
            } catch (IllegalStateException e) {
                updated.set(false);
            }
        });
        thread2.start();
        thread1.start();
        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertFalse(updated.get());
        assertNull(accountDao.select(id).getCity());
        assertEquals("Russia", accountDao.select(id).getCountry());
    }
}
