package com.getjavajob.training.yarginy.socialnetwork.dao;

import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.AccountImpl;
import com.getjavajob.training.yarginy.socialnetwork.common.models.group.Group;
import com.getjavajob.training.yarginy.socialnetwork.common.models.group.GroupImpl;
import com.getjavajob.training.yarginy.socialnetwork.dao.batchmodeldao.BatchDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.factories.AbstractDbFactory;
import com.getjavajob.training.yarginy.socialnetwork.dao.factories.DbFactory;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.Dao;
import com.getjavajob.training.yarginy.socialnetwork.dao.relationsdao.onetomany.OneToManyDao;
import org.junit.Test;

import java.util.Collection;
import java.util.List;

import static com.getjavajob.training.yarginy.socialnetwork.dao.utils.TestResultPrinter.printPassed;
import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

public class AccountsGroupsTest {
    public static final String CLASS = "AccountsGroupsTest";
    public static final DbFactory FACTORY = AbstractDbFactory.getDbFactory();
    public static final Dao<Account> ACCOUNT_DAO = FACTORY.getAccountDao();
    public static final BatchDao<Group> GROUP_DAO = FACTORY.getGroupDao();
    public static final OneToManyDao<Account, Group> ACCOUNTS_GROUPS = FACTORY.getAccountsOwnedGroupsDao(ACCOUNT_DAO);

    @Test
    public void testSelectGroups() {
        Account account = new AccountImpl("test account", "test surname", "test@test.com");
        ACCOUNT_DAO.create(account);
        account = ACCOUNT_DAO.select(account);
        Group firstGroup = new GroupImpl("first test group", account);
        GROUP_DAO.create(firstGroup);
        firstGroup = GROUP_DAO.select(firstGroup);
        Group secondGroup = new GroupImpl("second test group", account);
        GROUP_DAO.create(secondGroup);
        secondGroup = GROUP_DAO.select(secondGroup);
        List<Group> expectedGroups = asList(firstGroup, secondGroup);
        Collection<Group> actualGroups = ACCOUNTS_GROUPS.selectMany(account.getId());
        assertEquals(expectedGroups, actualGroups);
        printPassed(CLASS, "testSelectGroups");
        ACCOUNT_DAO.delete(account);
        GROUP_DAO.delete(firstGroup);
        GROUP_DAO.delete(secondGroup);
    }
}
