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

public class FriendshipsRequestsDaoTest {
    static {
        TestDataSourceInitializer.initDataSource();
    }

    @Autowired
    private FriendshipsFacade friendshipsFacade;
    @Autowired
    private AccountFacade accountFacade;
    private Account firstAccount = new AccountImpl("firstTest", "test", "first@test.test");
    private Account secondAccount = new AccountImpl("secondTest", "test", "second@test.test");

    @Before
    public void initTestValues() {
        accountFacade.create(firstAccount);
        firstAccount = accountFacade.select(firstAccount);
        accountFacade.create(secondAccount);
        secondAccount = accountFacade.select(secondAccount);
    }

    @After
    public void deleteTestValues() {
        friendshipsFacade.deleteRequest(firstAccount.getId(), secondAccount.getId());
        accountFacade.delete(firstAccount);
        accountFacade.delete(secondAccount);
    }

    @Test
    public void testCreateFriendshipRequest() {
        Collection<Account> accounts = accountFacade.selectAll();
        assertTrue(friendshipsFacade.createRequest(firstAccount.getId(), secondAccount.getId()));
    }

    @Test
    public void testCreateExistingRequest() {
        assert friendshipsFacade.createRequest(firstAccount.getId(), secondAccount.getId());
        assertFalse(friendshipsFacade.createRequest(firstAccount.getId(), secondAccount.getId()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateRequestToNonExistingAccount() {
        assertFalse(friendshipsFacade.createRequest(firstAccount.getId(), 0));
    }

    @Test
    public void testSelectRequests() {
        friendshipsFacade.createRequest(firstAccount.getId(), secondAccount.getId());
        Collection<Account> expected = new ArrayList<>();
        expected.add(firstAccount);
        assertEquals(expected, friendshipsFacade.selectRequests(secondAccount.getId()));
    }
}
