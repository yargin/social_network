package com.getjavajob.training.yarginy.socialnetwork.dao.tests.jpa;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.manytomany.Friendship;
import com.getjavajob.training.yarginy.socialnetwork.dao.configuration.TestDaoOverrideConfig;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.AccountDaoFacadeImpl;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.FriendshipsDaoFacadeImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestDaoOverrideConfig.class})
public class JpaFriendshipDaoTest {
    @Autowired
    private FriendshipsDaoFacadeImpl friendshipDao;
    @Autowired
    private AccountDaoFacadeImpl accountDao;
    private final Account firstAccount = new Account("firstAccount", "firstAccount", "firstAccount");
    private final Account secondAccount = new Account("secondAccount", "secondAccount", "secondAccount");

    @Before
    public void initAccounts() {
        accountDao.create(firstAccount);
        accountDao.create(secondAccount);
    }

    @After
    public void deleteAccounts() {
        accountDao.delete(firstAccount);
        accountDao.delete(secondAccount);
    }

    @Test
    public void testCreateSelectDeleteFriendship() {
        long firstId = firstAccount.getId();
        long secondId = secondAccount.getId();
        try {
            friendshipDao.areFriends(firstId, 1111111);
        } catch (IllegalArgumentException e) {
            assertTrue(true);
        }
        assertTrue(friendshipDao.createFriendship(firstId, secondId));
        Friendship friendship = new Friendship();
        friendship.setFirstAccount(firstAccount);
        friendship.setSecondAccount(secondAccount);
        assertEquals(singletonList(secondAccount), friendshipDao.selectFriends(firstId));
        assertEquals(emptyList(), friendshipDao.selectFriends(1111111));
        assertEquals(singletonList(firstAccount), friendshipDao.selectFriends(secondId));
        assertTrue(friendshipDao.areFriends(firstId, secondId));
        assertTrue(friendshipDao.areFriends(secondId, firstId));
        assertFalse(friendshipDao.removeFriendship(firstId, 1111111));
        assertTrue(friendshipDao.removeFriendship(firstId, secondId));
        assertFalse(friendshipDao.areFriends(firstId, secondId));
        assertFalse(friendshipDao.areFriends(secondId, firstId));
        assertEquals(emptyList(), friendshipDao.selectFriends(firstId));
        assertEquals(emptyList(), friendshipDao.selectFriends(secondId));
    }
}
