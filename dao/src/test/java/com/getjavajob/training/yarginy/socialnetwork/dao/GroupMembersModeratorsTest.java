package com.getjavajob.training.yarginy.socialnetwork.dao;

import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.AccountImpl;
import com.getjavajob.training.yarginy.socialnetwork.common.models.group.Group;
import com.getjavajob.training.yarginy.socialnetwork.common.models.group.GroupImpl;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.AccountDaoFacade;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.DataSetsDaoFacade;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.GroupDaoFacade;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.GroupsModeratorsDaoFacade;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Map;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:daoSpringConfig.xml", "classpath:daoOverrideSpringConfig.xml"})
public class GroupMembersModeratorsTest {
    @Autowired
    private DataSetsDaoFacade dataSetsDaoFacade;
    @Autowired
    private GroupDaoFacade groupDaoFacade;
    @Autowired
    private GroupsModeratorsDaoFacade moderatorsDao;
    @Autowired
    private AccountDaoFacade accountDaoFacade;
    private Account account1 = new AccountImpl("test1", "test1", "test1@test.test");
    private Account account2 = new AccountImpl("test2", "test2", "test2@test.test");
    private Account account3 = new AccountImpl("test3", "test3", "test3@test.test");
    private Account account4 = new AccountImpl("test4", "test4", "test4@test.test");
    private Group group = new GroupImpl("testGroup", account1);

    @Before
    public void testValuesInit() {
        accountDaoFacade.create(account1);
        account1 = accountDaoFacade.select(account1);
        accountDaoFacade.create(account2);
        account2 = accountDaoFacade.select(account2);
        accountDaoFacade.create(account3);
        account3 = accountDaoFacade.select(account3);
        accountDaoFacade.create(account4);
        account4 = accountDaoFacade.select(account4);
        groupDaoFacade.create(group);
        group = groupDaoFacade.select(group);
        groupDaoFacade.addMember(account1.getId(), group.getId());
        groupDaoFacade.addMember(account2.getId(), group.getId());
        groupDaoFacade.addMember(account3.getId(), group.getId());
        groupDaoFacade.addMember(account4.getId(), group.getId());
        moderatorsDao.addGroupModerator(account1.getId(), group.getId());
        moderatorsDao.addGroupModerator(account2.getId(), group.getId());
    }

    @After
    public void deleteTestValues() {
        accountDaoFacade.delete(account1);
        accountDaoFacade.delete(account2);
        accountDaoFacade.delete(account3);
        accountDaoFacade.delete(account4);
        groupDaoFacade.delete(group);
    }

    @Test
    public void testGetMembersModerators() {
        Map<Account, Boolean> membersModerators = dataSetsDaoFacade.getGroupMembersAreModerators(group.getId());
        assertTrue(membersModerators.get(account1));
        assertTrue(membersModerators.get(account2));
        assertFalse(membersModerators.get(account3));
        assertFalse(membersModerators.get(account4));
        assertEquals(4, membersModerators.size());
    }
}
