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

    private final AccountDao accountDao = new AccountDaoImpl();
    private final GroupDao groupDao = new GroupDaoImpl();
    private final GroupsMembersDao groupsMembersDao = new GroupsMembersDaoImpl();
    private Account requester = new AccountImpl("firstTest", "test", "first@test.test");
    private Account owner = new AccountImpl("secondTest", "test", "second@test.test");
    private Group group = new GroupImpl("testGroup", owner);

    @Before
    public void initTestValues() {
        accountDao.create(owner);
        owner = accountDao.select(owner);
        accountDao.create(requester);
        requester = accountDao.select(requester);
        groupDao.create(group);
        group = groupDao.select(group);
    }

    @After
    public void deleteTestValues() {
        groupsMembersDao.removeRequest(requester.getId(), group.getId());
        groupDao.delete(group);
        accountDao.delete(requester);
        accountDao.delete(owner);
    }

    @Test
    public void testCreateGroupMembershipRequest() {
        assertTrue(groupsMembersDao.createRequest(requester.getId(), group.getId()));
        assertEquals(Collections.singletonList(requester), groupsMembersDao.selectRequests(group.getId()));
    }

    @Test
    public void testCreateExistingRequest() {
        assertTrue(groupsMembersDao.createRequest(requester.getId(), group.getId()));
        assertFalse(groupsMembersDao.createRequest(requester.getId(), group.getId()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateRequestToNonExistingAccount() {
        assertFalse(groupsMembersDao.createRequest(0, group.getId()));
    }

    @Test
    public void testSelectRequests() {
        assertTrue(groupsMembersDao.createRequest(requester.getId(), group.getId()));
        assertEquals(Collections.singletonList(requester), groupsMembersDao.selectRequests(group.getId()));
    }
}
