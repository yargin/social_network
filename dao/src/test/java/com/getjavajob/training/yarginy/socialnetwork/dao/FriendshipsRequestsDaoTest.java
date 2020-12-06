package com.getjavajob.training.yarginy.socialnetwork.dao;

import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.AccountImpl;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.AccountDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.AccountDaoImpl;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.FriendshipsDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.FriendshipsDaoImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;

import static org.junit.Assert.*;

public class FriendshipsRequestsDaoTest {
    static {
        TestDataSourceInitializer.initDataSource();
    }

    private final FriendshipsDao friendshipsDao = new FriendshipsDaoImpl();
    private final AccountDao accountDao = new AccountDaoImpl();
    private Account firstAccount = new AccountImpl("firstTest", "test", "first@test.test");
    private Account secondAccount = new AccountImpl("secondTest", "test", "second@test.test");

    @Before
    public void initTestValues() {
        accountDao.create(firstAccount);
        firstAccount = accountDao.select(firstAccount);
        accountDao.create(secondAccount);
        secondAccount = accountDao.select(secondAccount);
    }

    @After
    public void deleteTestValues() {
        friendshipsDao.deleteRequest(firstAccount.getId(), secondAccount.getId());
        accountDao.delete(firstAccount);
        accountDao.delete(secondAccount);
    }

    @Test
    public void testCreateFriendshipRequest() {
        Collection<Account> accounts = accountDao.selectAll();
        assertTrue(friendshipsDao.createRequest(firstAccount.getId(), secondAccount.getId()));
    }

    @Test
    public void testCreateExistingRequest() {
        assert friendshipsDao.createRequest(firstAccount.getId(), secondAccount.getId());
        assertFalse(friendshipsDao.createRequest(firstAccount.getId(), secondAccount.getId()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateRequestToNonExistingAccount() {
        assertFalse(friendshipsDao.createRequest(firstAccount.getId(), 0));
    }

    @Test
    public void testSelectRequests() {
        friendshipsDao.createRequest(firstAccount.getId(), secondAccount.getId());
        Collection<Account> expected = new ArrayList<>();
        expected.add(firstAccount);
        assertEquals(expected, friendshipsDao.selectRequests(secondAccount.getId()));
    }
}
