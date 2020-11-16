package com.getjavajob.training.yarginy.socialnetwork.dao;

import com.getjavajob.training.yarginy.socialnetwork.common.exceptions.IncorrectDataException;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.AccountImpl;
import com.getjavajob.training.yarginy.socialnetwork.common.models.group.Group;
import com.getjavajob.training.yarginy.socialnetwork.common.models.group.GroupImpl;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.AccountDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.AccountDaoImpl;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.GroupDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.GroupDaoImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static com.getjavajob.training.yarginy.socialnetwork.dao.utils.TestResultPrinter.printPassed;
import static org.junit.Assert.*;

public class GroupDaoTest {
    private static final String CLASS = "GroupDaoTest";
    private final GroupDao groupDao = new GroupDaoImpl();
    ;
    private final AccountDao accountDao = new AccountDaoImpl();
    private final Group GROUP = new GroupImpl();
    private Account account = new AccountImpl("test", "test", "test@test.com");

    @Before
    public void testValuesInit() {
        accountDao.create(account);
        account = accountDao.select(account);
        GROUP.setOwner(account);
        GROUP.setName("testGroup");
    }

    @After
    public void testValuesDelete() {
        accountDao.delete(account);
        groupDao.delete(GROUP);
    }

    @Test
    public void testCreateGroup() {
        assertTrue(groupDao.create(GROUP));
        printPassed(CLASS, "testCreateGroup");
    }

    @Test
    public void testNullFieldCreate() {
        try {
            GROUP.setName(null);
            groupDao.create(GROUP);
        } catch (IncorrectDataException e) {
            assertFalse(false);
            printPassed(CLASS, "testNullFieldCreate");
        }
    }

    @Test
    public void testCreateExistingGroup() {
        groupDao.create(GROUP);
        assertFalse(groupDao.create(GROUP));
        printPassed(CLASS, "testCreateExistingGroup");
    }

    @Test
    public void testUpdateOwner() {
        groupDao.create(GROUP);
        Group storedGroup = groupDao.select(GROUP);
        Account newOwner = new AccountImpl("testOwner", "newOwner", "newOwner@test.test");
        accountDao.create(newOwner);
        GROUP.setOwner(newOwner);
        assertTrue(groupDao.update(GROUP, storedGroup));
    }

    @Test
    public void testSelectGroup() {
        groupDao.create(GROUP);
        Group actual = groupDao.select(GROUP);
        assertEquals(GROUP, actual);
        actual = groupDao.select(actual.getId());
        assertEquals(GROUP, actual);
        printPassed(CLASS, "testSelectGroup");
    }

    @Test
    public void testSelectNonExistingGroup() {
        Group actual = groupDao.select(GROUP);
        assertEquals(groupDao.getNullEntity(), actual);
        printPassed(CLASS, "testSelectNonExistingGroup");
    }

    @Test
    public void testUpdateGroup() {
        groupDao.create(GROUP);
        String newDescription = "new Description";
        GROUP.setDescription(newDescription);
        Group storedGroup = groupDao.select(GROUP);
        assertTrue(groupDao.update(GROUP, storedGroup));
        storedGroup = groupDao.select(GROUP);
        assertEquals(newDescription, storedGroup.getDescription());
        printPassed(CLASS, "testUpdateGroup");
    }

    @Test
    public void testUpdateNonExistingGroup() {
        Group nonExisting = new GroupImpl();
        nonExisting.setName("non existing group");
        assertFalse(groupDao.update(nonExisting, nonExisting));
        printPassed(CLASS, "testUpdateNonExistingGroup");
    }

    @Test
    public void testDeleteNonExisting() {
        Group nonExisting = new GroupImpl();
        nonExisting.setName("non existing group");
        assertFalse(groupDao.delete(nonExisting));
        printPassed(CLASS, "testDeleteNonExisting");
    }

    @Test
    public void testDeleteGroup() {
        groupDao.create(GROUP);
        assertTrue(groupDao.delete(GROUP));
        assertEquals(groupDao.getNullEntity(), groupDao.select(GROUP));
        printPassed(CLASS, "testDeleteGroup");
    }

    @Test
    public void testOwner() {
        groupDao.create(GROUP);
        Account actualOwner = accountDao.select(account);
        Group group = groupDao.select(GROUP);
        assertTrue(groupDao.isOwner(actualOwner.getId(), group.getId()));
        groupDao.delete(GROUP);
    }
}
