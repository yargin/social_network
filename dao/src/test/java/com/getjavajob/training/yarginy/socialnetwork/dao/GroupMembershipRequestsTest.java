package com.getjavajob.training.yarginy.socialnetwork.dao;

import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.AccountImpl;
import com.getjavajob.training.yarginy.socialnetwork.common.models.group.Group;
import com.getjavajob.training.yarginy.socialnetwork.common.models.group.GroupImpl;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;

import static org.junit.Assert.*;

public class GroupMembershipRequestsTest {
    static {
        TestDataSourceInitializer.initDataSource();
    }

    private final AccountFacade accountFacade = new AccountFacadeImpl();
    private final GroupFacade groupFacade = new GroupFacadeImpl();
    private final GroupsMembersFacade groupsMembersFacade = new GroupsMembersFacadeImpl();
    private Account requester = new AccountImpl("firstTest", "test", "first@test.test");
    private Account owner = new AccountImpl("secondTest", "test", "second@test.test");
    private Group group = new GroupImpl("testGroup", owner);

    @Before
    public void initTestValues() {
        accountFacade.create(owner);
        owner = accountFacade.select(owner);
        accountFacade.create(requester);
        requester = accountFacade.select(requester);
        groupFacade.create(group);
        group = groupFacade.select(group);
    }

    @After
    public void deleteTestValues() {
        groupsMembersFacade.removeRequest(requester.getId(), group.getId());
        groupFacade.delete(group);
        accountFacade.delete(requester);
        accountFacade.delete(owner);
    }

    @Test
    public void testCreateGroupMembershipRequest() {
        assertTrue(groupsMembersFacade.createRequest(requester.getId(), group.getId()));
        assertEquals(Collections.singletonList(requester), groupsMembersFacade.selectRequests(group.getId()));
    }

    @Test
    public void testCreateExistingRequest() {
        assertTrue(groupsMembersFacade.createRequest(requester.getId(), group.getId()));
        assertFalse(groupsMembersFacade.createRequest(requester.getId(), group.getId()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateRequestToNonExistingAccount() {
        assertFalse(groupsMembersFacade.createRequest(0, group.getId()));
    }

    @Test
    public void testSelectRequests() {
        assertTrue(groupsMembersFacade.createRequest(requester.getId(), group.getId()));
        assertEquals(Collections.singletonList(requester), groupsMembersFacade.selectRequests(group.getId()));
    }
}
