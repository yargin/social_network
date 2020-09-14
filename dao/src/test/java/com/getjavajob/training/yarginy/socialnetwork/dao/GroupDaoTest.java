package com.getjavajob.training.yarginy.socialnetwork.dao;

import com.getjavajob.training.yarginy.socialnetwork.common.exceptions.IncorrectDataException;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.group.Group;
import com.getjavajob.training.yarginy.socialnetwork.common.models.group.GroupImpl;
import com.getjavajob.training.yarginy.socialnetwork.dao.factories.AbstractDbFactory;
import com.getjavajob.training.yarginy.socialnetwork.dao.factories.DbFactory;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.Dao;
import org.junit.Test;

import static com.getjavajob.training.yarginy.socialnetwork.dao.utils.TestResultPrinter.printPassed;
import static org.junit.Assert.*;

public class GroupDaoTest {
    private static final DbFactory DB_FACTORY = AbstractDbFactory.getDbFactory();
    private static final String CLASS = "GroupDaoTest";
    private static final Group GROUP = new GroupImpl();
    private static final Dao<Group> GROUP_DAO = DB_FACTORY.getGroupDao();

    public GroupDaoTest() {
        Dao<Account> accountDao = DB_FACTORY.getAccountDao();
        Account account = accountDao.select(3);
        GROUP.setName("new Group");
        GROUP.setOwner(account);
    }

    @Test
    public void testCreateGroup() {
        GROUP_DAO.delete(GROUP);
        assertTrue(GROUP_DAO.create(GROUP));
        printPassed(CLASS, "testCreateGroup");
    }

    @Test
    public void testNullFieldCreate() {
        GROUP_DAO.delete(GROUP);
        try {
            GROUP.setName(null);
            GROUP_DAO.create(GROUP);
        } catch (IncorrectDataException e) {
            assertFalse(false);
            printPassed(CLASS, "testNullFieldCreate");
        }
    }

    @Test
    public void testCreateExistingGroup() {
        GROUP_DAO.create(GROUP);
        assertFalse(GROUP_DAO.create(GROUP));
        printPassed(CLASS, "testCreateExistingGroup");
    }

    @Test
    public void testSelectGroup() {
        GROUP_DAO.create(GROUP);
        Group actual = GROUP_DAO.select(GROUP);
        assertEquals(GROUP, actual);
        actual = GROUP_DAO.select(actual.getId());
        assertEquals(GROUP, actual);
        printPassed(CLASS, "testSelectGroup");
    }

    @Test
    public void testSelectNonExistingGroup() {
        GROUP_DAO.delete(GROUP);
        Group actual = GROUP_DAO.select(GROUP_DAO.getNullEntity());
        assertEquals(GROUP_DAO.getNullEntity(), actual);
        actual = GROUP_DAO.select(123);
        assertEquals(GROUP_DAO.getNullEntity(), actual);
        printPassed(CLASS, "testSelectNonExistingGroup");
    }

    @Test
    public void testUpdateGroup() {
        GROUP_DAO.create(GROUP);
        String newDescription = "new Description";
        GROUP.setDescription(newDescription);
        boolean actual = GROUP_DAO.update(GROUP);
        assertSame(true, actual);
        Group storageGroup = GROUP_DAO.select(GROUP);
        assertEquals(newDescription, storageGroup.getDescription());
        printPassed(CLASS, "testUpdateGroup");
    }

    @Test
    public void testUpdateNonExistingGroup() {
        Group nonExisting = new GroupImpl();
        nonExisting.setName("non existing group");
        boolean actual = GROUP_DAO.update(nonExisting);
        assertSame(false, actual);
        printPassed(CLASS, "testUpdateNonExistingGroup");
    }

    @Test
    public void testDeleteNonExisting() {
        Group nonExisting = new GroupImpl();
        nonExisting.setName("non existing group");
        boolean actual = GROUP_DAO.delete(nonExisting);
        assertSame(false, actual);
        printPassed(CLASS, "testDeleteNonExisting");
    }

    @Test
    public void testDeleteGroup() {
        GROUP_DAO.create(GROUP);
        boolean actual = GROUP_DAO.delete(GROUP);
        assertSame(true, actual);
        assertEquals(GROUP_DAO.getNullEntity(), GROUP_DAO.select(GROUP));
        printPassed(CLASS, "testDeleteGroup");
    }
}
