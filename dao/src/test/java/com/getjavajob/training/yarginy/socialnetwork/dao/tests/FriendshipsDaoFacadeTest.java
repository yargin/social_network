package com.getjavajob.training.yarginy.socialnetwork.dao.tests;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Account;
import com.getjavajob.training.yarginy.socialnetwork.dao.jdbctemplates.facades.AccountDaoFacade;
import com.getjavajob.training.yarginy.socialnetwork.dao.jdbctemplates.facades.FriendshipsDaoFacade;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:daoSpringConfig.xml", "classpath:daoTestJpaSpringConfig.xml"})
public class FriendshipsDaoFacadeTest {
    @Autowired
    private AccountDaoFacade accountDaoFacade;
    @Autowired
    private FriendshipsDaoFacade friendshipsDaoFacade;
    private Account friend = new Account("testFriend", "testSurname", "testFriend@test.test");
    private Account account = new Account("testAccount", "testSurname", "test@test.test");

    @Before
    public void initTestValues() {
        accountDaoFacade.create(friend);
        accountDaoFacade.create(account);
        friend = accountDaoFacade.select(friend);
        account = accountDaoFacade.select(account);
    }

    @After
    public void deleteTestValues() {
        accountDaoFacade.delete(account);
        accountDaoFacade.delete(friend);
    }

    @Test
    public void testSelectNoFriends() {
        Collection<Account> friends = friendshipsDaoFacade.selectFriends(111111);
        assertTrue(friends.isEmpty());
    }

    @Test
    public void testSelectFriends() {
        friendshipsDaoFacade.createFriendship(account.getId(), friend.getId());
        Collection<Account> friends = friendshipsDaoFacade.selectFriends(friend.getId());
        Collection<Account> expected = new ArrayList<>();
        expected.add(accountDaoFacade.select(account.getId()));
        assertEquals(expected, friends);
        //select by another
        expected = new ArrayList<>();
        expected.add(accountDaoFacade.select(friend.getId()));
        friends = friendshipsDaoFacade.selectFriends(account.getId());
        assertEquals(expected, friends);
    }

    @Test
    public void testNonExistingAccount() {
        Collection<Account> friends = friendshipsDaoFacade.selectFriends(1111111);
        assertTrue(friends.isEmpty());
    }

    @Test
    public void testDeleteFriendship() {
        friendshipsDaoFacade.createFriendship(account.getId(), friend.getId());
        boolean deleted = friendshipsDaoFacade.removeFriendship(account.getId(), friend.getId());
        assertTrue(deleted);
        //delete by another
        friendshipsDaoFacade.createFriendship(account.getId(), friend.getId());
        deleted = friendshipsDaoFacade.removeFriendship(friend.getId(), account.getId());
        assertTrue(deleted);
    }

    @Test
    public void createExistingFriendship() {
        friendshipsDaoFacade.createFriendship(account.getId(), friend.getId());
        boolean created = friendshipsDaoFacade.createFriendship(account.getId(), friend.getId());
        assertFalse(created);
        created = friendshipsDaoFacade.createFriendship(friend.getId(), account.getId());
        assertFalse(created);
    }

    @Test
    public void deleteNonExistingFriendship() {
        boolean deleted = friendshipsDaoFacade.removeFriendship(account.getId(), friend.getId());
        assertFalse(deleted);
    }
}
