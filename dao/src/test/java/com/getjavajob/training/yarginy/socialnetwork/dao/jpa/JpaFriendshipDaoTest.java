package com.getjavajob.training.yarginy.socialnetwork.dao.jpa;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.manytomany.Friendship;
import com.getjavajob.training.yarginy.socialnetwork.dao.jpa.relationsdao.selfmanytomany.JpaFriendshipDao;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static java.util.Collections.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:daoSpringConfig.xml", "classpath:daoTestJpaSpringConfig.xml"})
public class JpaFriendshipDaoTest {
    @Autowired
    private JpaFriendshipDao friendshipDao;
    @Autowired
    private JpaAccountDao accountDao;
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
        assertTrue(friendshipDao.create(firstId, secondId));
        Friendship friendship = new Friendship();
        friendship.setFirstAccount(firstAccount);
        friendship.setSecondAccount(secondAccount);
        assertEquals(singletonList(secondAccount), friendshipDao.select(firstId));
        assertEquals(singletonList(firstAccount), friendshipDao.select(secondId));
        assertTrue(friendshipDao.relationExists(firstId, secondId));
        assertTrue(friendshipDao.relationExists(secondId, firstId));
        assertTrue(friendshipDao.delete(firstId, secondId));
        assertFalse(friendshipDao.relationExists(firstId, secondId));
        assertFalse(friendshipDao.relationExists(secondId, firstId));
        assertEquals(emptyList(), friendshipDao.select(firstId));
        assertEquals(emptyList(), friendshipDao.select(secondId));
    }
}
