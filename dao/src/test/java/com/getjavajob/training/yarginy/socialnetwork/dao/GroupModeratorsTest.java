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
    private final Account owner = new AccountImpl("firstTest", "test", "first@test.test");
    private final Account moderator = new AccountImpl("secondTEst", "test", "second@test.test");
    private final Group group = new GroupImpl("testGroup", owner);

    @Before
    public void initTestValues() {
        accountDao.create(owner);
        accountDao.create(moderator);
        groupDao.create(group);
    }

    @After
    public void deleteTestValues() {
        groupsModeratorsDao.deleteGroupModerator(moderator, group);
        groupDao.delete(group);
        accountDao.delete(moderator);
        accountDao.delete(owner);
    }

    @Test
    public void testCreateModerator() {
        assertTrue(groupsModeratorsDao.addGroupModerator(moderator, group));
        assertEquals(singletonList(moderator), groupsModeratorsDao.selectModerators(group));
    }

    @Test
    public void testDeleteModerator() {
        groupsModeratorsDao.addGroupModerator(moderator, group);
        assertTrue(groupsModeratorsDao.deleteGroupModerator(moderator, group));
        assertEquals(emptyList(), groupsModeratorsDao.selectModerators(group));
    }

    @Test
    public void testSelectModerators() {
        groupsModeratorsDao.addGroupModerator(moderator, group);
        assertEquals(Collections.singletonList(moderator), groupsModeratorsDao.selectModerators(group));
    }
}
