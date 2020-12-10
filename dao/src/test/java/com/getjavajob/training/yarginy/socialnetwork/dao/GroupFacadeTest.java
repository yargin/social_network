package com.getjavajob.training.yarginy.socialnetwork.dao;

import com.getjavajob.training.yarginy.socialnetwork.common.exceptions.IncorrectDataException;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.AccountImpl;
import com.getjavajob.training.yarginy.socialnetwork.common.models.group.Group;
import com.getjavajob.training.yarginy.socialnetwork.common.models.group.GroupImpl;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.AccountFacade;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.AccountFacadeImpl;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.GroupFacade;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.GroupFacadeImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class GroupFacadeTest {
    static {
        TestDataSourceInitializer.initDataSource();
    }

    private final GroupFacade groupFacade = new GroupFacadeImpl();
    private final AccountFacade accountFacade = new AccountFacadeImpl();
    private final Group GROUP = new GroupImpl();
    private Account account = new AccountImpl("test", "test", "test@test.com");

    @Before
    public void testValuesInit() {
        accountFacade.create(account);
        account = accountFacade.select(account);
        GROUP.setOwner(account);
        GROUP.setName("testGroup");
    }

    @After
    public void testValuesDelete() {
        accountFacade.delete(account);
        groupFacade.delete(GROUP);
    }

    @Test
    public void testCreateGroup() {
        assertTrue(groupFacade.create(GROUP));
    }

    @Test
    public void testNullFieldCreate() {
        try {
            GROUP.setName(null);
            groupFacade.create(GROUP);
        } catch (IncorrectDataException e) {
            assertFalse(false);
        }
    }

    @Test
    public void testCreateExistingGroup() {
        groupFacade.create(GROUP);
        assertFalse(groupFacade.create(GROUP));
    }

    @Test
    public void testUpdateOwner() {
        groupFacade.create(GROUP);
        Group storedGroup = groupFacade.select(GROUP);
        Account newOwner = new AccountImpl("testOwner", "newOwner", "newOwner@test.test");
        accountFacade.create(newOwner);
        GROUP.setOwner(newOwner);
        assertTrue(groupFacade.update(GROUP, storedGroup));
    }

    @Test
    public void testSelectGroup() {
        groupFacade.create(GROUP);
        Group actual = groupFacade.select(GROUP);
        assertEquals(GROUP, actual);
        actual = groupFacade.select(actual.getId());
        assertEquals(GROUP, actual);
    }

    @Test
    public void testSelectNonExistingGroup() {
        Group actual = groupFacade.select(GROUP);
        assertEquals(groupFacade.getNullEntity(), actual);
    }

    @Test
    public void testUpdateGroup() {
        groupFacade.create(GROUP);
        String newDescription = "new Description";
        GROUP.setDescription(newDescription);
        Group storedGroup = groupFacade.select(GROUP);
        assertTrue(groupFacade.update(GROUP, storedGroup));
        storedGroup = groupFacade.select(GROUP);
        assertEquals(newDescription, storedGroup.getDescription());
    }

    @Test
    public void testUpdateNonExistingGroup() {
        Group nonExisting = new GroupImpl();
        nonExisting.setName("non existing group");
        assertFalse(groupFacade.update(nonExisting, nonExisting));
    }

    @Test
    public void testDeleteNonExisting() {
        Group nonExisting = new GroupImpl();
        nonExisting.setName("non existing group");
        assertFalse(groupFacade.delete(nonExisting));
    }

    @Test
    public void testDeleteGroup() {
        groupFacade.create(GROUP);
        assertTrue(groupFacade.delete(GROUP));
        assertEquals(groupFacade.getNullEntity(), groupFacade.select(GROUP));
    }

    @Test
    public void testOwner() {
        groupFacade.create(GROUP);
        Account actualOwner = accountFacade.select(account);
        Group group = groupFacade.select(GROUP);
        assertTrue(groupFacade.isOwner(actualOwner.getId(), group.getId()));
        groupFacade.delete(GROUP);
    }
}
