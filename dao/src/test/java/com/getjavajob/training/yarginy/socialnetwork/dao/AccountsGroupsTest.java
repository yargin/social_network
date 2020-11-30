package com.getjavajob.training.yarginy.socialnetwork.dao;

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

import java.util.Collection;
import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

public class AccountsGroupsTest {
    public static final AccountDao ACCOUNT_DAO = new AccountDaoImpl();
    public static final GroupDao GROUP_DAO = new GroupDaoImpl();
    private Account account;
    private Group firstGroup;
    private Group secondGroup;

    @Before
    public void initTestValues() {
        account = new AccountImpl("test account", "test surname", "test@test.com");
        ACCOUNT_DAO.create(account);
        account = ACCOUNT_DAO.select(account);

        firstGroup = new GroupImpl("first test group", account);
        GROUP_DAO.create(firstGroup);
        firstGroup = GROUP_DAO.select(firstGroup);

        secondGroup = new GroupImpl("second test group", account);
        GROUP_DAO.create(secondGroup);
        secondGroup = GROUP_DAO.select(secondGroup);
    }

    @After
    public void deleteTestValues() {
        ACCOUNT_DAO.delete(account);
        GROUP_DAO.delete(firstGroup);
        GROUP_DAO.delete(secondGroup);
    }

    @Test
    public void testSelectGroups() {
        List<Group> expectedGroups = asList(firstGroup, secondGroup);
        Collection<Group> actualGroups = ACCOUNT_DAO.getOwnedGroups(account.getId());
        assertEquals(expectedGroups, actualGroups);
    }
}
