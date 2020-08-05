package com.getjavajob.training.yarginy.socialnetwork.dao;

import com.getjavajob.training.yarginy.socialnetwork.dao.factories.DbFactory;
import com.getjavajob.training.yarginy.socialnetwork.dao.factory.DatabaseFactory;
import com.getjavajob.training.yarginy.socialnetwork.dao.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.dao.models.account.AccountDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.models.group.Group;
import com.getjavajob.training.yarginy.socialnetwork.dao.models.group.GroupDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.models.group.GroupImpl;
import org.junit.Test;

import static com.getjavajob.training.yarginy.socialnetwork.dao.utils.ResultPrinter.printPassed;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

public class GroupDaoTest {
    private static final DbFactory dbFactory = DatabaseFactory.getDbFactory();
    private static final String CLASS = "GroupDaoTest";
    private static final Group group = new GroupImpl();
    private static final GroupDao groupDao = dbFactory.getGroupDao();

    public GroupDaoTest() {
        AccountDao accountDao = dbFactory.getAccountDao();
        Account account = accountDao.select("robot@power.com");
        group.setName("new Group");
        group.setOwner(account);
    }

    @Test
    public void testCreateGroup() {
        groupDao.delete(group);
        boolean actual = groupDao.create(group);
        assertSame(true, actual);
        printPassed(CLASS, "testCreateGroup");
    }

    @Test(expected = IllegalStateException.class)
    public void testNullFieldCreate() {
        groupDao.delete(group);
        group.setName(null);
        groupDao.create(group);
        printPassed(CLASS, "testNullFieldCreate");
    }

    @Test
    public void testCreateExistingGroup() {
        groupDao.create(group);
        boolean actual = groupDao.create(group);
        assertSame(false, actual);
        printPassed(CLASS, "testCreateExistingGroup");
    }

    @Test
    public void testSelectGroup() {
        groupDao.create(group);
        Group actual = groupDao.select(group.getIdentifier());
        assertEquals(group, actual);
        actual = groupDao.select(actual.getId());
        assertEquals(group, actual);
        printPassed(CLASS, "testSelectGroup");
    }

    @Test
    public void testSelectNonExistingGroup() {
        groupDao.delete(group);
        Group actual = groupDao.select("non existing email");
        assertEquals(groupDao.getNullEntity(), actual);
        actual = groupDao.select(123);
        assertEquals(groupDao.getNullEntity(), actual);
        printPassed(CLASS, "testSelectNonExistingGroup");
    }

    @Test
    public void testUpdateGroup() {
        groupDao.create(group);
        String newPatronymic = "new Description";
        group.setDescription(newPatronymic);
        boolean actual = groupDao.update(group);
        assertSame(true, actual);
        Group storageGroup = groupDao.select(group.getIdentifier());
        assertEquals(newPatronymic, storageGroup.getDescription());
        printPassed(CLASS, "testUpdateGroup");
    }

    @Test
    public void testUpdateNonExistingGroup() {
        Group nonExisting = new GroupImpl();
        boolean actual = groupDao.update(nonExisting);
        assertSame(false, actual);
        printPassed(CLASS, "testUpdateNonExistingGroup");
    }

    @Test
    public void testDeleteNonExisting() {
        Group nonExisting = new GroupImpl();
        boolean actual = groupDao.delete(nonExisting);
        assertSame(false, actual);
        printPassed(CLASS, "testDeleteNonExisting");
    }

    @Test
    public void testDeleteGroup() {
        groupDao.create(group);
        boolean actual = groupDao.delete(group);
        assertSame(true, actual);
        Group nonExisting = groupDao.getNullEntity();
        assertEquals(nonExisting, groupDao.select(group.getIdentifier()));
        printPassed(CLASS, "testDeleteGroup");
    }
}
