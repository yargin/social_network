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

import java.util.ArrayList;
import java.util.Collection;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:daoSpringConfig.xml", "classpath:daoOverrideSpringConfig.xml"})
public class AccountsInGroupsTest {
    @Autowired
    private GroupDaoFacade groupDaoFacade;
    @Autowired
    private GroupsMembersDaoFacade groupsMembersDaoFacade;
    @Autowired
    private AccountDaoFacade accountDaoFacade;
    private Account account = new AccountImpl("test", "test", "test@test.test");
    private Account owner = new AccountImpl("testOwner", "testOwner", "testOwner@test.test");
    private Group group = new GroupImpl("testGroup", owner);

    @Before
    public void testValuesInit() {
        accountDaoFacade.delete(account);
        groupDaoFacade.delete(group);
        accountDaoFacade.delete(owner);
        accountDaoFacade.create(account);
        accountDaoFacade.create(owner);
        account = accountDaoFacade.select(account);
        owner = accountDaoFacade.select(owner);
        group.setOwner(owner);
        groupDaoFacade.create(group);
        group = groupDaoFacade.select(group);
    }

    @After
    public void testValuesDelete() {
        accountDaoFacade.delete(account);
        groupDaoFacade.delete(group);
        accountDaoFacade.delete(owner);
    }

    @Test
    public void testJoinGroup() {
        boolean actual = groupsMembersDaoFacade.joinGroup(account.getId(), group.getId());
        assertTrue(actual);
        groupsMembersDaoFacade.leaveGroup(account.getId(), group.getId());
    }

    @Test
    public void testJoinAlreadyJoinedGroup() {
        groupsMembersDaoFacade.joinGroup(account.getId(), group.getId());
        boolean actual = groupsMembersDaoFacade.joinGroup(account.getId(), group.getId());
        assertFalse(actual);
        groupsMembersDaoFacade.leaveGroup(account.getId(), group.getId());
    }

    @Test
    public void testLeaveGroup() {
        groupsMembersDaoFacade.joinGroup(account.getId(), group.getId());
        boolean actual = groupsMembersDaoFacade.leaveGroup(account.getId(), group.getId());
        assertTrue(actual);
    }

    @Test
    public void selectMembers() {
        groupsMembersDaoFacade.joinGroup(account.getId(), group.getId());
        Collection<Account> expected = new ArrayList<>();
        expected.add(accountDaoFacade.select(account));
        Collection<Account> actual = groupsMembersDaoFacade.selectMembers(group.getId());
        assertEquals(expected, actual);
        groupsMembersDaoFacade.leaveGroup(account.getId(), group.getId());
    }

    @Test
    public void selectGroups() {
        groupsMembersDaoFacade.joinGroup(account.getId(), group.getId());
        Collection<Group> expected = new ArrayList<>();
        expected.add(groupDaoFacade.select(group));
        Collection<Group> actual = groupsMembersDaoFacade.selectAccountGroups(account.getId());
        assertEquals(expected, actual);
        groupsMembersDaoFacade.leaveGroup(account.getId(), group.getId());
    }
}
