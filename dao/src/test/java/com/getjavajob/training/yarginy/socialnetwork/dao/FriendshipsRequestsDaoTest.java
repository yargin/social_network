package com.getjavajob.training.yarginy.socialnetwork.dao;

import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.AccountDaoFacade;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.FriendshipsDaoFacade;
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
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:daoSpringConfig.xml", "classpath:daoOverrideSpringConfig.xml"})
public class FriendshipsRequestsDaoTest {
    @Autowired
    private FriendshipsDaoFacade friendshipsDaoFacade;
    @Autowired
    private AccountDaoFacade accountDaoFacade;
    private Account firstAccount = new Account("firstTest", "test", "first@test.test");
    private Account secondAccount = new Account("secondTest", "test", "second@test.test");

    @Before
    public void initTestValues() {
        accountDaoFacade.create(firstAccount);
        firstAccount = accountDaoFacade.select(firstAccount);
        accountDaoFacade.create(secondAccount);
        secondAccount = accountDaoFacade.select(secondAccount);
    }

    @After
    public void deleteTestValues() {
        friendshipsDaoFacade.deleteRequest(firstAccount.getId(), secondAccount.getId());
        accountDaoFacade.delete(firstAccount);
        accountDaoFacade.delete(secondAccount);
    }

    @Test
    public void testCreateFriendshipRequest() {
        Collection<Account> accounts = accountDaoFacade.selectAll();
        assertTrue(friendshipsDaoFacade.createRequest(firstAccount.getId(), secondAccount.getId()));
    }

    @Test
    public void testCreateExistingRequest() {
        assert friendshipsDaoFacade.createRequest(firstAccount.getId(), secondAccount.getId());
        assertFalse(friendshipsDaoFacade.createRequest(firstAccount.getId(), secondAccount.getId()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateRequestToNonExistingAccount() {
        friendshipsDaoFacade.createRequest(firstAccount.getId(), 0);
    }

    @Test
    public void testSelectRequests() {
        friendshipsDaoFacade.createRequest(firstAccount.getId(), secondAccount.getId());
        Collection<Account> expected = new ArrayList<>();
        expected.add(firstAccount);
        assertEquals(expected, friendshipsDaoFacade.selectRequests(secondAccount.getId()));
    }
}
