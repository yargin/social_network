package com.getjavajob.training.yarginy.socialnetwork.dao;

import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.AccountImpl;
import com.getjavajob.training.yarginy.socialnetwork.common.models.group.Group;
import com.getjavajob.training.yarginy.socialnetwork.common.models.group.GroupImpl;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.AccountDaoFacade;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.GroupDaoFacade;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.GroupsModeratorsDaoFacade;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Collections;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:daoSpringConfig.xml", "classpath:daoOverrideSpringConfig.xml"})
public class GroupModeratorsTest {
    @Autowired
    private GroupsModeratorsDaoFacade groupsModeratorsDaoFacade;
    @Autowired
    private GroupDaoFacade groupDaoFacade;
    @Autowired
    private AccountDaoFacade accountDaoFacade;
    private Account owner = new AccountImpl("firstTest", "test", "first@test.test");
    private Account moderator = new AccountImpl("secondTEst", "test", "second@test.test");
    private Group group = new GroupImpl("testGroup", owner);

    @Before
    public void initTestValues() {
        accountDaoFacade.create(owner);
        owner = accountDaoFacade.select(owner);
        accountDaoFacade.create(moderator);
        moderator = accountDaoFacade.select(moderator);
        groupDaoFacade.create(group);
        group = groupDaoFacade.select(group);
    }

    @After
    public void deleteTestValues() {
        groupsModeratorsDaoFacade.deleteGroupModerator(moderator.getId(), group.getId());
        groupDaoFacade.delete(group);
        accountDaoFacade.delete(moderator);
        accountDaoFacade.delete(owner);
    }

    @Test
    public void testCreateModerator() {
        assertTrue(groupsModeratorsDaoFacade.addGroupModerator(moderator.getId(), group.getId()));
        assertEquals(singletonList(moderator), groupsModeratorsDaoFacade.selectModerators(group.getId()));
    }

    @Test
    public void testDeleteModerator() {
        groupsModeratorsDaoFacade.addGroupModerator(moderator.getId(), group.getId());
        assertTrue(groupsModeratorsDaoFacade.deleteGroupModerator(moderator.getId(), group.getId()));
        assertEquals(emptyList(), groupsModeratorsDaoFacade.selectModerators(group.getId()));
    }

    @Test
    public void testSelectModerators() {
        groupsModeratorsDaoFacade.addGroupModerator(moderator.getId(), group.getId());
        assertEquals(Collections.singletonList(moderator), groupsModeratorsDaoFacade.selectModerators(group.getId()));
    }
}
