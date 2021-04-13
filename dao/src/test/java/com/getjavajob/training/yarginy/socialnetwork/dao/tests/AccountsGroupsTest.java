package com.getjavajob.training.yarginy.socialnetwork.dao.tests;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.Group;
import com.getjavajob.training.yarginy.socialnetwork.dao.jdbctemplates.facades.AccountDaoFacade;
import com.getjavajob.training.yarginy.socialnetwork.dao.jdbctemplates.facades.GroupDaoFacade;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import static java.sql.Date.*;
import static java.time.LocalDate.*;
import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:daoSpringConfig.xml", "classpath:daoTestJpaSpringConfig.xml"})
public class AccountsGroupsTest {
    @Autowired
    public AccountDaoFacade accountDaoFacade;
    @Autowired
    public GroupDaoFacade groupDaoFacade;
    private Account account;
    private Group firstGroup;
    private Group secondGroup;

    @Before
    public void initTestValues() {
        account = new Account("test account", "test surname", "test@test.com");
        accountDaoFacade.create(account);
        account = accountDaoFacade.select(account);

        firstGroup = new Group("first test group", account);
        firstGroup.setCreationDate(valueOf(of(2020, 2, 2)));
        groupDaoFacade.create(firstGroup);
        firstGroup = groupDaoFacade.select(firstGroup);

        secondGroup = new Group("second test group", account);
        secondGroup.setCreationDate(valueOf(of(2020, 2, 2)));
        groupDaoFacade.create(secondGroup);
        secondGroup = groupDaoFacade.select(secondGroup);
    }

    @After
    public void deleteTestValues() {
        accountDaoFacade.delete(account);
        groupDaoFacade.delete(firstGroup);
        groupDaoFacade.delete(secondGroup);
    }

    @Test
    public void testSelectGroups() {
        List<Group> expectedGroups = asList(firstGroup, secondGroup);
        Collection<Group> actualGroups = accountDaoFacade.getOwnedGroups(account.getId());
        assertEquals(expectedGroups, actualGroups);
    }
}
