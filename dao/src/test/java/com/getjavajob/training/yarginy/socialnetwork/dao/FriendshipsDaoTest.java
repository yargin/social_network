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

public class FriendshipsDaoTest {
    private static final AccountDao ACCOUNT_DAO = new AccountDaoImpl();
    private static final FriendshipsDao FRIENDSHIP_DAO = new FriendshipsDaoImpl();
    private Account friend = new AccountImpl("testFriend", "testSurname", "testFriend@test.test");
    private Account account = new AccountImpl("testAccount", "testSurname", "test@test.test");

    @Before
    public void initTestValues() {
        ACCOUNT_DAO.create(friend);
        ACCOUNT_DAO.create(account);
        friend = ACCOUNT_DAO.select(friend);
        account = ACCOUNT_DAO.select(account);
    }

    @After
    public void deleteTestValues() {
        ACCOUNT_DAO.delete(account);
        ACCOUNT_DAO.delete(friend);
    }

    @Test
    public void testSelectNoFriends() {
        Collection<Account> friends = FRIENDSHIP_DAO.selectFriends(0);
        assertSame(true, friends.isEmpty());
    }

    @Test
    public void testSelectFriends() {
        FRIENDSHIP_DAO.createFriendship(account.getId(), friend.getId());
        Collection<Account> friends = FRIENDSHIP_DAO.selectFriends(friend.getId());
        Collection<Account> expected = new ArrayList<>();
        expected.add(ACCOUNT_DAO.select(account.getId()));
        assertEquals(expected, friends);
        //select by another
        expected = new ArrayList<>();
        expected.add(ACCOUNT_DAO.select(friend.getId()));
        friends = FRIENDSHIP_DAO.selectFriends(account.getId());
        assertEquals(expected, friends);
    }

    @Test
    public void testNonExistingAccount() {
        Collection<Account> friends = FRIENDSHIP_DAO.selectFriends(0);
        assertSame(true, friends.isEmpty());
    }

    @Test
    public void testDeleteFriendship() {
        FRIENDSHIP_DAO.createFriendship(account.getId(), friend.getId());
        boolean deleted = FRIENDSHIP_DAO.removeFriendship(account.getId(), friend.getId());
        assertTrue(deleted);
        //delete by another
        FRIENDSHIP_DAO.createFriendship(account.getId(), friend.getId());
        deleted = FRIENDSHIP_DAO.removeFriendship(friend.getId(), account.getId());
        assertTrue(deleted);
    }

    @Test
    public void createExistingFriendship() {
        FRIENDSHIP_DAO.createFriendship(account.getId(), friend.getId());
        boolean created = FRIENDSHIP_DAO.createFriendship(account.getId(), friend.getId());
        assertFalse(created);
        created = FRIENDSHIP_DAO.createFriendship(friend.getId(), account.getId());
        assertFalse(created);
    }

    @Test
    public void deleteNonExistingFriendship() {
        boolean deleted = FRIENDSHIP_DAO.removeFriendship(account.getId(), friend.getId());
        assertFalse(deleted);
    }
}
