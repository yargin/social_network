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

import static org.junit.Assert.*;

public class GroupMembersModeratorsTest {
    private final DataSetsDao dataSetsDao = new DataSetsDaoImpl();
    private final AccountDao accountDao = new AccountDaoImpl();
    private final GroupDao groupDao = new GroupDaoImpl();
    private final GroupsModeratorsDao moderatorsDao = new GroupsModeratorsDaoImpl();
    private Account account1 = new AccountImpl("test1", "test1", "test1@test.test");
    private Account account2 = new AccountImpl("test2", "test2", "test2@test.test");
    private Account account3 = new AccountImpl("test3", "test3", "test3@test.test");
    private Account account4 = new AccountImpl("test4", "test4", "test4@test.test");
    private Group group = new GroupImpl("testGroup", account1);

    @Before
    public void testValuesInit() {
        accountDao.create(account1);
        account1 = accountDao.select(account1);
        accountDao.create(account2);
        account2 = accountDao.select(account2);
        accountDao.create(account3);
        account3 = accountDao.select(account3);
        accountDao.create(account4);
        account4 = accountDao.select(account4);
        groupDao.create(group);
        group = groupDao.select(group);
        groupDao.addMember(account1.getId(), group.getId());
        groupDao.addMember(account2.getId(), group.getId());
        groupDao.addMember(account3.getId(), group.getId());
        groupDao.addMember(account4.getId(), group.getId());
        moderatorsDao.addGroupModerator(account1.getId(), group.getId());
        moderatorsDao.addGroupModerator(account2.getId(), group.getId());
    }

    @After
    public void deleteTestValues() {
        accountDao.delete(account1);
        accountDao.delete(account2);
        accountDao.delete(account3);
        accountDao.delete(account4);
        groupDao.delete(group);
    }

    @Test
    public void testGetMembersModerators() {
        Map<Account, Boolean> membersModerators = dataSetsDao.getGroupMembersAreModerators(group.getId());
        assertTrue(membersModerators.get(account1));
        assertTrue(membersModerators.get(account2));
        assertFalse(membersModerators.get(account3));
        assertFalse(membersModerators.get(account4));
        assertEquals(4, membersModerators.size());
    }
}
