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
    private final DataSetsDao dataSetsDao = new DataSetsDaoImpl();
    private final GroupsMembersDao membersDao = new GroupsMembersDaoImpl();
    private final AccountDao accountDao = new AccountDaoImpl();
    private final GroupDao groupDao = new GroupDaoImpl();
    private Account account = new AccountImpl("test", "test", "test@test.test");
    private Account owner = new AccountImpl("testOwner", "testOwner", "testOwner@test.test");
    private Group group1 = new GroupImpl("testGroup1", owner);
    private Group group2 = new GroupImpl("testGroup2", owner);
    private Group group3 = new GroupImpl("testGroup3", owner);
    private Group group4 = new GroupImpl("testGroup4", owner);
    private Group group5 = new GroupImpl("testGroup5", owner);

    @Before
    public void testValuesInit() {
        accountDao.create(account);
        account = accountDao.select(account);
        accountDao.create(owner);
        owner = accountDao.select(owner);

        groupDao.create(group1);
        group1 = groupDao.select(group1);
        groupDao.create(group2);
        group2 = groupDao.select(group2);
        groupDao.create(group3);
        group3 = groupDao.select(group3);
        groupDao.create(group4);
        group4 = groupDao.select(group4);
        groupDao.create(group5);
        group5 = groupDao.select(group5);

        long accountId = account.getId();
        groupDao.addMember(accountId, group1.getId());
        groupDao.addMember(accountId, group5.getId());
        membersDao.createRequest(accountId, group2.getId());
        membersDao.createRequest(accountId, group3.getId());
    }

    @After
    public void deleteTestValues() {
        groupDao.delete(group1);
        groupDao.delete(group2);
        groupDao.delete(group3);
        groupDao.delete(group4);
        groupDao.delete(group5);
        accountDao.delete(account);
        accountDao.delete(owner);
    }

    @Test
    public void testGetAllUnjoinedGroupsAreRequested() {
        Map<Group, Boolean> allGroups = dataSetsDao.getAllUnjoinedGroupsAreRequested(account.getId());
        assertTrue(allGroups.get(group2));
        assertTrue(allGroups.get(group3));
        assertFalse(allGroups.get(group4));
    }
}
