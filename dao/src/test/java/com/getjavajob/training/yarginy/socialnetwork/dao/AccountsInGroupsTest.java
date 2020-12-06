package com.getjavajob.training.yarginy.socialnetwork.dao;

import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.AccountImpl;
import com.getjavajob.training.yarginy.socialnetwork.common.models.group.Group;
import com.getjavajob.training.yarginy.socialnetwork.common.models.group.GroupImpl;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;

import static org.junit.Assert.*;

public class AccountsInGroupsTest {
    static {
        TestDataSourceInitializer.initDataSource();
    }

    private static final AccountDao ACCOUNT_DAO = new AccountDaoImpl();
    private static final GroupDao GROUP_DAO = new GroupDaoImpl();
    private static final GroupsMembersDao GROUPS_MEMBERS_DAO = new GroupsMembersDaoImpl();
    private Account account = new AccountImpl("test", "test", "test@test.test");
    private Account owner = new AccountImpl("testOwner", "testOwner", "testOwner@test.test");
    private Group group = new GroupImpl("testGroup", owner);

    @Before
    public void testValuesInit() {
        ACCOUNT_DAO.create(account);
        ACCOUNT_DAO.create(owner);
        account = ACCOUNT_DAO.select(account);
        owner = ACCOUNT_DAO.select(owner);
        GROUP_DAO.create(group);
        group = GROUP_DAO.select(group);
    }

    @After
    public void testValuesDelete() {
        ACCOUNT_DAO.delete(account);
        GROUP_DAO.delete(group);
        ACCOUNT_DAO.delete(owner);
    }

    @Test
    public void testJoinGroup() {
        boolean actual = GROUPS_MEMBERS_DAO.joinGroup(account.getId(), group.getId());
        assertTrue(actual);
        GROUPS_MEMBERS_DAO.leaveGroup(account.getId(), group.getId());
    }

    @Test
    public void testJoinAlreadyJoinedGroup() {
        GROUPS_MEMBERS_DAO.joinGroup(account.getId(), group.getId());
        boolean actual = GROUPS_MEMBERS_DAO.joinGroup(account.getId(), group.getId());
        assertFalse(actual);
        GROUPS_MEMBERS_DAO.leaveGroup(account.getId(), group.getId());
    }

    @Test
    public void testLeaveGroup() {
        GROUPS_MEMBERS_DAO.joinGroup(account.getId(), group.getId());
        boolean actual = GROUPS_MEMBERS_DAO.leaveGroup(account.getId(), group.getId());
        assertTrue(actual);
    }

    @Test
    public void selectMembers() {
        GROUPS_MEMBERS_DAO.joinGroup(account.getId(), group.getId());
        Collection<Account> expected = new ArrayList<>();
        expected.add(ACCOUNT_DAO.select(account));
        Collection<Account> actual = GROUPS_MEMBERS_DAO.selectMembers(group.getId());
        assertEquals(expected, actual);
        GROUPS_MEMBERS_DAO.leaveGroup(account.getId(), group.getId());
    }

    @Test
    public void selectGroups() {
        GROUPS_MEMBERS_DAO.joinGroup(account.getId(), group.getId());
        Collection<Group> expected = new ArrayList<>();
        expected.add(GROUP_DAO.select(group));
        Collection<Group> actual = GROUPS_MEMBERS_DAO.selectAccountGroups(account.getId());
        assertEquals(expected, actual);
        GROUPS_MEMBERS_DAO.leaveGroup(account.getId(), group.getId());
    }
}
