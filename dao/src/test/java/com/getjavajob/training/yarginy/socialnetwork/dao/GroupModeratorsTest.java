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
    private final GroupsModeratorsDao groupsModeratorsDao = new GroupsModeratorsDaoImpl();
    private final AccountDao accountDao = new AccountDaoImpl();
    private final GroupDao groupDao = new GroupDaoImpl();
    private Account owner = new AccountImpl("firstTest", "test", "first@test.test");
    private Account moderator = new AccountImpl("secondTEst", "test", "second@test.test");
    private Group group = new GroupImpl("testGroup", owner);

    @Before
    public void initTestValues() {
        accountDao.create(owner);
        owner = accountDao.select(owner);
        accountDao.create(moderator);
        moderator = accountDao.select(moderator);
        groupDao.create(group);
        group = groupDao.select(group);
    }

    @After
    public void deleteTestValues() {
        groupsModeratorsDao.deleteGroupModerator(moderator.getId(), group.getId());
        groupDao.delete(group);
        accountDao.delete(moderator);
        accountDao.delete(owner);
    }

    @Test
    public void testCreateModerator() {
        assertTrue(groupsModeratorsDao.addGroupModerator(moderator.getId(), group.getId()));
        assertEquals(singletonList(moderator), groupsModeratorsDao.selectModerators(group.getId()));
    }

    @Test
    public void testDeleteModerator() {
        groupsModeratorsDao.addGroupModerator(moderator.getId(), group.getId());
        assertTrue(groupsModeratorsDao.deleteGroupModerator(moderator.getId(), group.getId()));
        assertEquals(emptyList(), groupsModeratorsDao.selectModerators(group.getId()));
    }

    @Test
    public void testSelectModerators() {
        groupsModeratorsDao.addGroupModerator(moderator.getId(), group.getId());
        assertEquals(Collections.singletonList(moderator), groupsModeratorsDao.selectModerators(group.getId()));
    }
}
