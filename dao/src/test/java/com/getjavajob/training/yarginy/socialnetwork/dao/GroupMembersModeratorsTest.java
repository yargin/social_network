package com.getjavajob.training.yarginy.socialnetwork.dao;

import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.AccountImpl;
import com.getjavajob.training.yarginy.socialnetwork.common.models.group.Group;
import com.getjavajob.training.yarginy.socialnetwork.common.models.group.GroupImpl;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.AccountFacade;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.DataSetsFacade;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.GroupFacade;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.GroupsModeratorsFacade;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

import static org.junit.Assert.*;

public class GroupMembersModeratorsTest {
    static {
        TestDataSourceInitializer.initDataSource();
    }

    @Autowired
    private DataSetsFacade dataSetsFacade;
    @Autowired
    private GroupFacade groupFacade;
    @Autowired
    private GroupsModeratorsFacade moderatorsDao;
    @Autowired
    private AccountFacade accountFacade;
    private Account account1 = new AccountImpl("test1", "test1", "test1@test.test");
    private Account account2 = new AccountImpl("test2", "test2", "test2@test.test");
    private Account account3 = new AccountImpl("test3", "test3", "test3@test.test");
    private Account account4 = new AccountImpl("test4", "test4", "test4@test.test");
    private Group group = new GroupImpl("testGroup", account1);

    @Before
    public void testValuesInit() {
        accountFacade.create(account1);
        account1 = accountFacade.select(account1);
        accountFacade.create(account2);
        account2 = accountFacade.select(account2);
        accountFacade.create(account3);
        account3 = accountFacade.select(account3);
        accountFacade.create(account4);
        account4 = accountFacade.select(account4);
        groupFacade.create(group);
        group = groupFacade.select(group);
        groupFacade.addMember(account1.getId(), group.getId());
        groupFacade.addMember(account2.getId(), group.getId());
        groupFacade.addMember(account3.getId(), group.getId());
        groupFacade.addMember(account4.getId(), group.getId());
        moderatorsDao.addGroupModerator(account1.getId(), group.getId());
        moderatorsDao.addGroupModerator(account2.getId(), group.getId());
    }

    @After
    public void deleteTestValues() {
        accountFacade.delete(account1);
        accountFacade.delete(account2);
        accountFacade.delete(account3);
        accountFacade.delete(account4);
        groupFacade.delete(group);
    }

    @Test
    public void testGetMembersModerators() {
        Map<Account, Boolean> membersModerators = dataSetsFacade.getGroupMembersAreModerators(group.getId());
        assertTrue(membersModerators.get(account1));
        assertTrue(membersModerators.get(account2));
        assertFalse(membersModerators.get(account3));
        assertFalse(membersModerators.get(account4));
        assertEquals(4, membersModerators.size());
    }
}
