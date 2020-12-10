package com.getjavajob.training.yarginy.socialnetwork.dao;

import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.AccountImpl;
import com.getjavajob.training.yarginy.socialnetwork.common.models.group.Group;
import com.getjavajob.training.yarginy.socialnetwork.common.models.group.GroupImpl;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class GroupModeratorsTest {
    static {
        TestDataSourceInitializer.initDataSource();
    }

    private final GroupsModeratorsFacade groupsModeratorsFacade = new GroupsModeratorsFacadeImpl();
    private final AccountFacade accountFacade = new AccountFacadeImpl();
    private final GroupFacade groupFacade = new GroupFacadeImpl();
    private Account owner = new AccountImpl("firstTest", "test", "first@test.test");
    private Account moderator = new AccountImpl("secondTEst", "test", "second@test.test");
    private Group group = new GroupImpl("testGroup", owner);

    @Before
    public void initTestValues() {
        accountFacade.create(owner);
        owner = accountFacade.select(owner);
        accountFacade.create(moderator);
        moderator = accountFacade.select(moderator);
        groupFacade.create(group);
        group = groupFacade.select(group);
    }

    @After
    public void deleteTestValues() {
        groupsModeratorsFacade.deleteGroupModerator(moderator.getId(), group.getId());
        groupFacade.delete(group);
        accountFacade.delete(moderator);
        accountFacade.delete(owner);
    }

    @Test
    public void testCreateModerator() {
        assertTrue(groupsModeratorsFacade.addGroupModerator(moderator.getId(), group.getId()));
        assertEquals(singletonList(moderator), groupsModeratorsFacade.selectModerators(group.getId()));
    }

    @Test
    public void testDeleteModerator() {
        groupsModeratorsFacade.addGroupModerator(moderator.getId(), group.getId());
        assertTrue(groupsModeratorsFacade.deleteGroupModerator(moderator.getId(), group.getId()));
        assertEquals(emptyList(), groupsModeratorsFacade.selectModerators(group.getId()));
    }

    @Test
    public void testSelectModerators() {
        groupsModeratorsFacade.addGroupModerator(moderator.getId(), group.getId());
        assertEquals(Collections.singletonList(moderator), groupsModeratorsFacade.selectModerators(group.getId()));
    }
}
