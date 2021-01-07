package com.getjavajob.training.yarginy.socialnetwork.dao;

import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.AccountImpl;
import com.getjavajob.training.yarginy.socialnetwork.common.models.group.Group;
import com.getjavajob.training.yarginy.socialnetwork.common.models.group.GroupImpl;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.AccountDaoFacade;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.GroupDaoFacade;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.GroupsMembersDaoFacade;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Collections;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:daoSpringConfig.xml", "classpath:daoOverrideSpringConfig.xml"})
public class GroupMembershipRequestsTest {
    @Autowired
    private GroupDaoFacade groupDaoFacade;
    @Autowired
    private GroupsMembersDaoFacade groupsMembersDaoFacade;
    @Autowired
    private AccountDaoFacade accountDaoFacade;
    private Account requester = new AccountImpl("firstTest", "test", "first@test.test");
    private Account owner = new AccountImpl("secondTest", "test", "second@test.test");
    private Group group = new GroupImpl("testGroup", owner);

    @Before
    public void initTestValues() {
        accountDaoFacade.create(owner);
        owner = accountDaoFacade.select(owner);
        accountDaoFacade.create(requester);
        requester = accountDaoFacade.select(requester);
        group.setOwner(owner);
        groupDaoFacade.create(group);
        group = groupDaoFacade.select(group);
    }

    @After
    public void deleteTestValues() {
        groupsMembersDaoFacade.removeRequest(requester.getId(), group.getId());
        groupDaoFacade.delete(group);
        accountDaoFacade.delete(requester);
        accountDaoFacade.delete(owner);
    }

    @Test
    public void testCreateGroupMembershipRequest() {
        assertTrue(groupsMembersDaoFacade.createRequest(requester.getId(), group.getId()));
        assertEquals(Collections.singletonList(requester), groupsMembersDaoFacade.selectRequests(group.getId()));
    }

    @Test
    public void testCreateExistingRequest() {
        assertTrue(groupsMembersDaoFacade.createRequest(requester.getId(), group.getId()));
        assertFalse(groupsMembersDaoFacade.createRequest(requester.getId(), group.getId()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateRequestToNonExistingAccount() {
        groupsMembersDaoFacade.createRequest(0, group.getId());
    }

    @Test
    public void testSelectRequests() {
        assertTrue(groupsMembersDaoFacade.createRequest(requester.getId(), group.getId()));
        assertEquals(Collections.singletonList(requester), groupsMembersDaoFacade.selectRequests(group.getId()));
    }
}
