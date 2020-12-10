package com.getjavajob.training.yarginy.socialnetwork.dao;

import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.AccountImpl;
import com.getjavajob.training.yarginy.socialnetwork.common.models.group.Group;
import com.getjavajob.training.yarginy.socialnetwork.common.models.group.GroupImpl;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.AccountFacade;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.GroupFacade;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.GroupsMembersFacade;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Collection;

import static org.junit.Assert.*;

public class AccountsInGroupsTest {
    static {
        TestDataSourceInitializer.initDataSource();
    }

    @Autowired
    private GroupFacade groupFacade;
    @Autowired
    private GroupsMembersFacade groupsMembersFacade;
    @Autowired
    private AccountFacade accountFacade;
    private Account account = new AccountImpl("test", "test", "test@test.test");
    private Account owner = new AccountImpl("testOwner", "testOwner", "testOwner@test.test");
    private Group group = new GroupImpl("testGroup", owner);

    @Before
    public void testValuesInit() {
        accountFacade.create(account);
        accountFacade.create(owner);
        account = accountFacade.select(account);
        owner = accountFacade.select(owner);
        groupFacade.create(group);
        group = groupFacade.select(group);
    }

    @After
    public void testValuesDelete() {
        accountFacade.delete(account);
        groupFacade.delete(group);
        accountFacade.delete(owner);
    }

    @Test
    public void testJoinGroup() {
        boolean actual = groupsMembersFacade.joinGroup(account.getId(), group.getId());
        assertTrue(actual);
        groupsMembersFacade.leaveGroup(account.getId(), group.getId());
    }

    @Test
    public void testJoinAlreadyJoinedGroup() {
        groupsMembersFacade.joinGroup(account.getId(), group.getId());
        boolean actual = groupsMembersFacade.joinGroup(account.getId(), group.getId());
        assertFalse(actual);
        groupsMembersFacade.leaveGroup(account.getId(), group.getId());
    }

    @Test
    public void testLeaveGroup() {
        groupsMembersFacade.joinGroup(account.getId(), group.getId());
        boolean actual = groupsMembersFacade.leaveGroup(account.getId(), group.getId());
        assertTrue(actual);
    }

    @Test
    public void selectMembers() {
        groupsMembersFacade.joinGroup(account.getId(), group.getId());
        Collection<Account> expected = new ArrayList<>();
        expected.add(accountFacade.select(account));
        Collection<Account> actual = groupsMembersFacade.selectMembers(group.getId());
        assertEquals(expected, actual);
        groupsMembersFacade.leaveGroup(account.getId(), group.getId());
    }

    @Test
    public void selectGroups() {
        groupsMembersFacade.joinGroup(account.getId(), group.getId());
        Collection<Group> expected = new ArrayList<>();
        expected.add(groupFacade.select(group));
        Collection<Group> actual = groupsMembersFacade.selectAccountGroups(account.getId());
        assertEquals(expected, actual);
        groupsMembersFacade.leaveGroup(account.getId(), group.getId());
    }
}
