package com.getjavajob.training.yarginy.socialnetwork.dao;

import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.AccountImpl;
import com.getjavajob.training.yarginy.socialnetwork.common.models.group.Group;
import com.getjavajob.training.yarginy.socialnetwork.common.models.group.GroupImpl;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.AccountDaoFacade;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.DataSetsDaoFacade;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.GroupDaoFacade;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.GroupsMembersDaoFacade;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Map;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:daoSpringConfig.xml")
public class GroupsListAreRequestedTest {
    @Autowired
    private DataSetsDaoFacade dataSetsDaoFacade;
    @Autowired
    private GroupsMembersDaoFacade membersDao;
    @Autowired
    private GroupDaoFacade groupDaoFacade;
    @Autowired
    private AccountDaoFacade accountDaoFacade;
    private Account account = new AccountImpl("test", "test", "test@test.test");
    private Account owner = new AccountImpl("testOwner", "testOwner", "testOwner@test.test");
    private Group group1 = new GroupImpl("testGroup1", owner);
    private Group group2 = new GroupImpl("testGroup2", owner);
    private Group group3 = new GroupImpl("testGroup3", owner);
    private Group group4 = new GroupImpl("testGroup4", owner);
    private Group group5 = new GroupImpl("testGroup5", owner);

    @Before
    public void initTestValues() {
        accountDaoFacade.create(account);
        account = accountDaoFacade.select(account);
        accountDaoFacade.create(owner);
        owner = accountDaoFacade.select(owner);

        groupDaoFacade.create(group1);
        group1 = groupDaoFacade.select(group1);
        groupDaoFacade.create(group2);
        group2 = groupDaoFacade.select(group2);
        groupDaoFacade.create(group3);
        group3 = groupDaoFacade.select(group3);
        groupDaoFacade.create(group4);
        group4 = groupDaoFacade.select(group4);
        groupDaoFacade.create(group5);
        group5 = groupDaoFacade.select(group5);

        long accountId = account.getId();
        groupDaoFacade.addMember(accountId, group1.getId());
        groupDaoFacade.addMember(accountId, group5.getId());
        membersDao.createRequest(accountId, group2.getId());
        membersDao.createRequest(accountId, group3.getId());
    }

    @After
    public void deleteTestValues() {
        groupDaoFacade.delete(group1);
        groupDaoFacade.delete(group2);
        groupDaoFacade.delete(group3);
        groupDaoFacade.delete(group4);
        groupDaoFacade.delete(group5);
        accountDaoFacade.delete(account);
        accountDaoFacade.delete(owner);
    }

    @Test
    public void testGetAllUnjoinedGroupsAreRequested() {
        Map<Group, Boolean> allGroups = dataSetsDaoFacade.getAllUnjoinedGroupsAreRequested(account.getId());
        assertTrue(allGroups.get(group2));
        assertTrue(allGroups.get(group3));
        assertFalse(allGroups.get(group4));
    }
}
