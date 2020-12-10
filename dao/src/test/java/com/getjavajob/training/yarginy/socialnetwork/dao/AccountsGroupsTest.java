package com.getjavajob.training.yarginy.socialnetwork.dao;

import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.AccountImpl;
import com.getjavajob.training.yarginy.socialnetwork.common.models.group.Group;
import com.getjavajob.training.yarginy.socialnetwork.common.models.group.GroupImpl;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.AccountFacade;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.GroupFacade;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

public class AccountsGroupsTest {
    static {
        TestDataSourceInitializer.initDataSource();
    }

    @Autowired
    public static AccountFacade accountFacade;
    @Autowired
    public GroupFacade groupFacade;
    private Account account;
    private Group firstGroup;
    private Group secondGroup;

    @Before
    public void initTestValues() {
        account = new AccountImpl("test account", "test surname", "test@test.com");
        accountFacade.create(account);
        account = accountFacade.select(account);

        firstGroup = new GroupImpl("first test group", account);
        groupFacade.create(firstGroup);
        firstGroup = groupFacade.select(firstGroup);

        secondGroup = new GroupImpl("second test group", account);
        groupFacade.create(secondGroup);
        secondGroup = groupFacade.select(secondGroup);
    }

    @After
    public void deleteTestValues() {
        accountFacade.delete(account);
        groupFacade.delete(firstGroup);
        groupFacade.delete(secondGroup);
    }

    @Test
    public void testSelectGroups() {
        List<Group> expectedGroups = asList(firstGroup, secondGroup);
        Collection<Group> actualGroups = accountFacade.getOwnedGroups(account.getId());
        assertEquals(expectedGroups, actualGroups);
    }
}
