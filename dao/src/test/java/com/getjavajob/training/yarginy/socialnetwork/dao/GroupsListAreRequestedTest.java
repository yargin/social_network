package com.getjavajob.training.yarginy.socialnetwork.dao;

import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.AccountImpl;
import com.getjavajob.training.yarginy.socialnetwork.common.models.group.Group;
import com.getjavajob.training.yarginy.socialnetwork.common.models.group.GroupImpl;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class GroupsListAreRequestedTest {
    static {
        TestDataSourceInitializer.initDataSource();
    }

    private final DataSetsFacade dataSetsFacade = new DataSetsFacadeImpl();
    private final GroupsMembersFacade membersDao = new GroupsMembersFacadeImpl();
    private final AccountFacade accountFacade = new AccountFacadeImpl();
    private final GroupFacade groupFacade = new GroupFacadeImpl();
    private Account account = new AccountImpl("test", "test", "test@test.test");
    private Account owner = new AccountImpl("testOwner", "testOwner", "testOwner@test.test");
    private Group group1 = new GroupImpl("testGroup1", owner);
    private Group group2 = new GroupImpl("testGroup2", owner);
    private Group group3 = new GroupImpl("testGroup3", owner);
    private Group group4 = new GroupImpl("testGroup4", owner);
    private Group group5 = new GroupImpl("testGroup5", owner);

    @Before
    public void initTestValues() {
        accountFacade.create(account);
        account = accountFacade.select(account);
        accountFacade.create(owner);
        owner = accountFacade.select(owner);

        groupFacade.create(group1);
        group1 = groupFacade.select(group1);
        groupFacade.create(group2);
        group2 = groupFacade.select(group2);
        groupFacade.create(group3);
        group3 = groupFacade.select(group3);
        groupFacade.create(group4);
        group4 = groupFacade.select(group4);
        groupFacade.create(group5);
        group5 = groupFacade.select(group5);

        long accountId = account.getId();
        groupFacade.addMember(accountId, group1.getId());
        groupFacade.addMember(accountId, group5.getId());
        membersDao.createRequest(accountId, group2.getId());
        membersDao.createRequest(accountId, group3.getId());
    }

    @After
    public void deleteTestValues() {
        groupFacade.delete(group1);
        groupFacade.delete(group2);
        groupFacade.delete(group3);
        groupFacade.delete(group4);
        groupFacade.delete(group5);
        accountFacade.delete(account);
        accountFacade.delete(owner);
    }

    @Test
    public void testGetAllUnjoinedGroupsAreRequested() {
        Map<Group, Boolean> allGroups = dataSetsFacade.getAllUnjoinedGroupsAreRequested(account.getId());
        assertTrue(allGroups.get(group2));
        assertTrue(allGroups.get(group3));
        assertFalse(allGroups.get(group4));
    }
}
