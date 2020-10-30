package com.getjavajob.training.yarginy.socialnetwork.dao;

import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.AccountImpl;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.AccountDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.AccountDaoImpl;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.AccountsFriendshipsDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.AccountsFriendshipsDaoImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;

import static org.junit.Assert.*;

public class FriendshipsRequestsDaoTest {
    private final AccountsFriendshipsDao accountsFriendshipsDao = new AccountsFriendshipsDaoImpl();
    private final AccountDao accountDao = new AccountDaoImpl();
    private Account firstAccount = new AccountImpl("firstTest", "test", "first@test.test");
    private Account secondAccount = new AccountImpl("secondTest", "test", "second@test.test");

    @Before
    public void initTestValues() {
        assert accountDao.create(firstAccount);
        assert accountDao.create(secondAccount);
//        firstAccount = accountDao.select(firstAccount);
//        secondAccount = accountDao.select(secondAccount);
    }

    @After
    public void deleteTestValues() {
//        firstAccount = accountDao.select(firstAccount);
//        secondAccount = accountDao.select(secondAccount);
        accountsFriendshipsDao.deleteRequest(firstAccount, secondAccount);
        assert accountDao.delete(firstAccount);
        assert accountDao.delete(secondAccount);
    }

    @Test
    public void testCreateFriendshipRequest() {
        Collection<Account> accounts = accountDao.selectAll();
        assertTrue(accountsFriendshipsDao.createRequest(firstAccount, secondAccount));
    }

    @Test
    public void testCreateExistingRequest() {
        assert accountsFriendshipsDao.createRequest(firstAccount, secondAccount);
        assertFalse(accountsFriendshipsDao.createRequest(firstAccount, secondAccount));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateRequestToNonExistingAccount() {
        Account account = new AccountImpl("test", "test", "test@test.test");
        assertFalse(accountsFriendshipsDao.createRequest(firstAccount, account));
    }

    @Test
    public void testSelectRequests() {
        accountsFriendshipsDao.createRequest(firstAccount, secondAccount);
        Collection<Account> expected = new ArrayList<>();
        expected.add(firstAccount);
        assertEquals(expected, accountsFriendshipsDao.selectRequests(secondAccount));
    }
}
