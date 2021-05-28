package com.getjavajob.training.yarginy.socialnetwork.dao.tests;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.Group;
import com.getjavajob.training.yarginy.socialnetwork.dao.configuration.TestDaoOverrideConfig;
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

import java.sql.Date;
import java.time.LocalDate;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestDaoOverrideConfig.class})
public class GroupModeratorsTest {
    @Autowired
    private GroupsModeratorsDaoFacade groupsModeratorsDaoFacade;
    @Autowired
    private GroupDaoFacade groupDaoFacade;
    @Autowired
    private AccountDaoFacade accountDaoFacade;
    private Account owner = new Account("firstTest", "test", "first@test.test");
    private Account moderator = new Account("secondTEst", "test", "second@test.test");
    private Group group = new Group("testGroup", owner);

    @Before
    public void initTestValues() {
        accountDaoFacade.create(owner);
        owner = accountDaoFacade.select(owner);
        accountDaoFacade.create(moderator);
        moderator = accountDaoFacade.select(moderator);
        group.setOwner(owner);
        group.setCreationDate(Date.valueOf(LocalDate.of(2020, 2, 2)));
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
        assertEquals(singletonList(moderator), groupsModeratorsDaoFacade.selectModerators(group.getId()));
    }
}
