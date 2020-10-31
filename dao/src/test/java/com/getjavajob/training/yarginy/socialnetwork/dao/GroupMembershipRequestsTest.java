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
    private final GroupsModeratorsDao groupsModeratorsDao = new GroupsModeratorsDaoImpl();
    private final AccountDao accountDao = new AccountDaoImpl();
    private final GroupDao groupDao = new GroupDaoImpl();
    private final GroupsMembersDao groupsMembersDao = new GroupsMembersDaoImpl();
    private final Account requester = new AccountImpl("firstTest", "test", "first@test.test");
    private final Account owner = new AccountImpl("secondTest", "test", "second@test.test");
    private final Group group = new GroupImpl("testGroup", owner);

    @Before
    public void initTestValues() {
        accountDao.create(owner);
        accountDao.create(requester);
        groupDao.create(group);
    }

    @After
    public void deleteTestValues() {
        groupsMembersDao.removeRequest(requester, group);
        groupDao.delete(group);
        accountDao.delete(requester);
        accountDao.delete(owner);
    }

    @Test
    public void testCreateGroupMembershipRequest() {
        assertTrue(groupsMembersDao.createRequest(requester, group));
        assertEquals(Collections.singletonList(requester), groupsMembersDao.selectRequests(group));
    }

    @Test
    public void testCreateExistingRequest() {
        assertTrue(groupsMembersDao.createRequest(requester, group));
        assertFalse(groupsMembersDao.createRequest(requester, group));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateRequestToNonExistingAccount() {
        Account account = new AccountImpl("test", "test", "test@test.test");
        assertFalse(groupsMembersDao.createRequest(account, group));
    }

    @Test
    public void testSelectRequests() {
        assertTrue(groupsMembersDao.createRequest(requester, group));
        assertEquals(Collections.singletonList(requester), groupsMembersDao.selectRequests(group));
    }
}
