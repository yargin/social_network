package com.getjavajob.training.yarginy.socialnetwork.dao.tests;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.Group;
import com.getjavajob.training.yarginy.socialnetwork.common.models.searchable.SearchablesDto;
import com.getjavajob.training.yarginy.socialnetwork.dao.configuration.TestDaoOverrideConfig;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.AccountDaoFacade;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.GroupDaoFacade;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.GroupsMembersDaoFacade;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.SearchDaoFacadeImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Collection;
import java.util.Map;

import static java.sql.Date.valueOf;
import static java.time.LocalDate.of;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestDaoOverrideConfig.class})
public class OtherDaoTest {
    private static final Logger logger = LoggerFactory.getLogger(OtherDaoTest.class);
    @Autowired
    private SearchDaoFacadeImpl searchDaoFacade;
    @Autowired
    private GroupsMembersDaoFacade membersDao;
    @Autowired
    private GroupDaoFacade groupDaoFacade;
    @Autowired
    private AccountDaoFacade accountDaoFacade;
    private Account account = new Account("testt", "test", "test@test.test");
    private Account owner = new Account("testtOwner", "testOwner", "testOwner@test.test");
    private Group group1;
    private Group group2;
    private Group group3;
    private Group group4;
    private Group group5;

    @Before
    public void initTestValues() {
        accountDaoFacade.create(account);
        account = accountDaoFacade.select(account);
        accountDaoFacade.create(owner);
        owner = accountDaoFacade.select(owner);
        group1 = new Group("testtGroup1", owner);
        group1.setCreationDate(valueOf(of(2020, 2, 2)));
        group2 = new Group("testtGroup2", owner);
        group2.setCreationDate(valueOf(of(2020, 2, 2)));
        group3 = new Group("testtGroup3", owner);
        group3.setCreationDate(valueOf(of(2020, 2, 2)));
        group4 = new Group("testtGroup4", owner);
        group4.setCreationDate(valueOf(of(2020, 2, 2)));
        group5 = new Group("testtGroup5", owner);
        group5.setCreationDate(valueOf(of(2020, 2, 2)));

        groupDaoFacade.create(group1);
        group1 = groupDaoFacade.select(group1);
        groupDaoFacade.create(group2);
        group2 = groupDaoFacade.select(group2);
        groupDaoFacade.create(group3);
        group3 = groupDaoFacade.select(group3);
        groupDaoFacade.create(group4);
        group4 = groupDaoFacade.select(group4);
        groupDaoFacade.create(group5);
        group5 = groupDaoFacade.select(group5);
        Collection<Group> groups = groupDaoFacade.selectAll();

        long accountId = account.getId();
        groupDaoFacade.addMember(accountId, group1.getId());
        groupDaoFacade.addMember(accountId, group5.getId());
        membersDao.createRequest(accountId, group2.getId());
        membersDao.createRequest(accountId, group3.getId());
    }

    @After
    public void deleteTestValues() {
        groupDaoFacade.delete(group1);
        groupDaoFacade.delete(group2);
        groupDaoFacade.delete(group3);
        groupDaoFacade.delete(group4);
        groupDaoFacade.delete(group5);
        accountDaoFacade.delete(account);
        accountDaoFacade.delete(owner);
    }

    @Test
    public void testGetAllUnjoinedGroupsAreRequested() {
        Map<Group, Boolean> allGroups = membersDao.getAllUnjoinedGroupsAreRequested(account.getId());
        assertTrue(allGroups.get(group2));
        assertTrue(allGroups.get(group3));
        assertFalse(allGroups.get(group4));
    }

    @Test
    public void testFindSearchables() {
        SearchablesDto searchablesDto = searchDaoFacade.searchAccountsGroups("testt", 1, 10);
        assertEquals(7, searchablesDto.getSearchAbles().size());
    }
}
