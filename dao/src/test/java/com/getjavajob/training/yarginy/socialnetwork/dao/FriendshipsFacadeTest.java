package com.getjavajob.training.yarginy.socialnetwork.dao;

import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.AccountImpl;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.AccountFacade;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.FriendshipsFacade;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Collection;

import static org.junit.Assert.*;

public class FriendshipsFacadeTest {
    static {
        TestDataSourceInitializer.initDataSource();
    }

    @Autowired
    private AccountFacade accountFacade;
    @Autowired
    private FriendshipsFacade friendshipsFacade;
    private Account friend = new AccountImpl("testFriend", "testSurname", "testFriend@test.test");
    private Account account = new AccountImpl("testAccount", "testSurname", "test@test.test");

    @Before
    public void initTestValues() {
        accountFacade.create(friend);
        accountFacade.create(account);
        friend = accountFacade.select(friend);
        account = accountFacade.select(account);
    }

    @After
    public void deleteTestValues() {
        accountFacade.delete(account);
        accountFacade.delete(friend);
    }

    @Test
    public void testSelectNoFriends() {
        Collection<Account> friends = friendshipsFacade.selectFriends(0);
        assertSame(true, friends.isEmpty());
    }

    @Test
    public void testSelectFriends() {
        friendshipsFacade.createFriendship(account.getId(), friend.getId());
        Collection<Account> friends = friendshipsFacade.selectFriends(friend.getId());
        Collection<Account> expected = new ArrayList<>();
        expected.add(accountFacade.select(account.getId()));
        assertEquals(expected, friends);
        //select by another
        expected = new ArrayList<>();
        expected.add(accountFacade.select(friend.getId()));
        friends = friendshipsFacade.selectFriends(account.getId());
        assertEquals(expected, friends);
    }

    @Test
    public void testNonExistingAccount() {
        Collection<Account> friends = friendshipsFacade.selectFriends(0);
        assertSame(true, friends.isEmpty());
    }

    @Test
    public void testDeleteFriendship() {
        friendshipsFacade.createFriendship(account.getId(), friend.getId());
        boolean deleted = friendshipsFacade.removeFriendship(account.getId(), friend.getId());
        assertTrue(deleted);
        //delete by another
        friendshipsFacade.createFriendship(account.getId(), friend.getId());
        deleted = friendshipsFacade.removeFriendship(friend.getId(), account.getId());
        assertTrue(deleted);
    }

    @Test
    public void createExistingFriendship() {
        friendshipsFacade.createFriendship(account.getId(), friend.getId());
        boolean created = friendshipsFacade.createFriendship(account.getId(), friend.getId());
        assertFalse(created);
        created = friendshipsFacade.createFriendship(friend.getId(), account.getId());
        assertFalse(created);
    }

    @Test
    public void deleteNonExistingFriendship() {
        boolean deleted = friendshipsFacade.removeFriendship(account.getId(), friend.getId());
        assertFalse(deleted);
    }
}
