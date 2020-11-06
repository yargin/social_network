package com.getjavajob.training.yarginy.socialnetwork.dao;

import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.group.Group;
import com.getjavajob.training.yarginy.socialnetwork.dao.batchmodeldao.BatchDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.factories.AbstractDbFactory;
import com.getjavajob.training.yarginy.socialnetwork.dao.factories.DbFactory;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.Dao;
import com.getjavajob.training.yarginy.socialnetwork.dao.relationsdao.manytomany.variousrelated.ManyToManyDao;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;

import static com.getjavajob.training.yarginy.socialnetwork.dao.utils.TestResultPrinter.printPassed;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

public class AccountsInGroupsTest {
    private static final DbFactory DB_FACTORY = AbstractDbFactory.getDbFactory();
    private static final String CLASS = "AccountDaoTest";
    private static final Dao<Account> ACCOUNT_DAO = DB_FACTORY.getAccountDao();
    private static final BatchDao<Group> GROUP_DAO = DB_FACTORY.getGroupDao();
    private static final ManyToManyDao<Account, Group> MEMBERSHIP_DAO = DB_FACTORY.getGroupMembershipDao();
    private static Account account;
    private static Group group;

    @Test
    public void testJoinGroup() {
        account = ACCOUNT_DAO.select(3);
        group = GROUP_DAO.select(1);
        boolean actual = MEMBERSHIP_DAO.create(account.getId(), group.getId());
        assertSame(true, actual);
        MEMBERSHIP_DAO.delete(account.getId(), group.getId());
        printPassed(CLASS, "testJoinGroup");
    }

    @Test
    public void testJoinAlreadyJoinedGroup() {
        account = ACCOUNT_DAO.select(1);
        group = GROUP_DAO.select(2);
        boolean actual = MEMBERSHIP_DAO.create(account.getId(), group.getId());
        assertSame(false, actual);
        printPassed(CLASS, "testJoinAlreadyJoinedGroup");
    }

    @Test
    public void testLeaveGroup() {
        account = ACCOUNT_DAO.select(1);
        group = GROUP_DAO.select(2);
        boolean actual = MEMBERSHIP_DAO.delete(account.getId(), group.getId());
        assertSame(true, actual);
        printPassed(CLASS, "testLeaveGroup");
        MEMBERSHIP_DAO.create(account.getId(), group.getId());
    }

    @Test
    public void selectMembers() {
        Collection<Account> expected = new ArrayList<>();
        expected.add(ACCOUNT_DAO.select(1));
        expected.add(ACCOUNT_DAO.select(2));
        group = GROUP_DAO.select(1);
        Collection<Account> actual = MEMBERSHIP_DAO.selectBySecond(group.getId());
        assertEquals(expected, actual);
        printPassed(CLASS, "selectMembers");
    }

    @Test
    public void selectGroups() {
        Collection<Group> expected = new ArrayList<>();
        expected.add(GROUP_DAO.select(1));
        expected.add(GROUP_DAO.select(2));
        account = ACCOUNT_DAO.select(1);
        Collection<Group> actual = MEMBERSHIP_DAO.selectByFirst(account.getId());
        assertEquals(expected, actual);
        printPassed(CLASS, "selectGroups");
    }
}
