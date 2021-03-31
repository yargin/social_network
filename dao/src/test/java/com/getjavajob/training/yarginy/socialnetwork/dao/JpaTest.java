package com.getjavajob.training.yarginy.socialnetwork.dao;

import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.AccountDao;
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
@ContextConfiguration(locations = {"classpath:daoSpringConfig.xml", "classpath:daoOverrideSpringConfig.xml"})
public class JpaTest {
    private final Account account = new Account("testName", "testSurname", "testEmail");
    @Autowired
    private AccountDao accountDao;

    @Before
    public void initValues() {
        accountDao.delete(accountDao.select(account));
        accountDao.create(account);
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
        Account newAccount = new Account("newAcc", "newAcc", "newAcc");
        assertTrue(accountDao.create(newAccount));
        assertEquals(newAccount, accountDao.select(newAccount));
        assertFalse(accountDao.create(newAccount));
        accountDao.delete(newAccount);
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
            updated.set(accountDao.update(account2));
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
