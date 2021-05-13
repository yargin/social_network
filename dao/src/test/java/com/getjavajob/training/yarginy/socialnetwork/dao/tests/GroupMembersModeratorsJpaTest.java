package com.getjavajob.training.yarginy.socialnetwork.dao.tests;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.Group;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.AccountDaoFacadeImpl;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.GroupDaoFacadeImpl;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.GroupsMembersDaoFacade;
import com.getjavajob.training.yarginy.socialnetwork.dao.jpa.relationdaos.manytomany.AdditionalManyToManyDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.jpa.relationdaos.manytomany.implementations.GroupModeratorsDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.jpa.relationdaos.manytomany.implementations.GroupRequestsDao;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.Assert.assertSame;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:daoSpringConfig.xml", "classpath:daoTestOverrideSpringConfig.xml"})
public class GroupMembersModeratorsJpaTest {
    private static final Logger logger = LoggerFactory.getLogger(GroupMembersModeratorsJpaTest.class);
    @Autowired
    private GroupDaoFacadeImpl groupDao;
    @Autowired
    private AccountDaoFacadeImpl accountDaoFacade;
    @Autowired
    private GroupsMembersDaoFacade groupMembershipDao;
    @Autowired
    private GroupModeratorsDao groupModeratorsDao;
    @Autowired
    private AdditionalManyToManyDao additionalManyToManyDao;

    @Autowired
    private GroupRequestsDao groupRequestsDao;
    private Account firstAccount = new Account("firstAccount", "firstAccount", "firstAccount");
    private Account secondAccount = new Account("secondAccount", "secondAccount", "secondAccount");
    private Account thirdAccount = new Account("thirdAccount", "thirdAccount", "thirdAccount");
    private Account forthAccount = new Account("forthAccount", "forthAccount", "forthAccount");
    private Group firstGroup = new Group("firstGroup", firstAccount);
    private Group secondGroup = new Group("secondGroup", firstAccount);
    private Group thirdGroup = new Group("thirdGroup", firstAccount);
    private Group forthGroup = new Group("forthGroup", firstAccount);
    private Group group;

    @Before
    public void initValues() {
        //accounts
        accountDaoFacade.create(firstAccount);
        firstAccount = accountDaoFacade.select(firstAccount);
        accountDaoFacade.create(secondAccount);
        secondAccount = accountDaoFacade.select(secondAccount);
        accountDaoFacade.create(thirdAccount);
        thirdAccount = accountDaoFacade.select(thirdAccount);
        accountDaoFacade.create(forthAccount);
        forthAccount = accountDaoFacade.select(forthAccount);

        //group
        firstGroup.setCreationDate(Date.valueOf(LocalDate.of(2020, 2, 2)));
        groupDao.create(firstGroup);
        firstGroup = groupDao.select(firstGroup);
        secondGroup.setCreationDate(Date.valueOf(LocalDate.of(2020, 2, 2)));
        groupDao.create(secondGroup);
        secondGroup = groupDao.select(secondGroup);
        thirdGroup.setCreationDate(Date.valueOf(LocalDate.of(2020, 2, 2)));
        groupDao.create(thirdGroup);
        thirdGroup = groupDao.select(thirdGroup);
        forthGroup.setCreationDate(Date.valueOf(LocalDate.of(2020, 2, 2)));
        groupDao.create(forthGroup);
        forthGroup = groupDao.select(forthGroup);
    }

    @After
    public void deleteTestValues() {
        accountDaoFacade.delete(firstAccount);
        accountDaoFacade.delete(secondAccount);
        accountDaoFacade.delete(thirdAccount);
        accountDaoFacade.delete(firstAccount);
        groupDao.delete(firstGroup);
        groupDao.delete(secondGroup);
        groupDao.delete(thirdGroup);
        groupDao.delete(forthGroup);
    }

    @Test
    public void testCreateSelectMembersAndModerators() {
        groupMembershipDao.joinGroup(firstAccount.getId(), firstGroup.getId());
        groupMembershipDao.joinGroup(secondAccount.getId(), firstGroup.getId());
        groupMembershipDao.joinGroup(thirdAccount.getId(), firstGroup.getId());
        groupMembershipDao.joinGroup(forthAccount.getId(), firstGroup.getId());
        groupModeratorsDao.create(firstAccount.getId(), firstGroup.getId());
        groupModeratorsDao.create(secondAccount.getId(), firstGroup.getId());

        logger.info("==================================================");
        Map<Account, Boolean> groupMembersAreModerators = additionalManyToManyDao.getGroupMembersAreModerators(
                firstGroup.getId());
        logger.info("==================================================");

        firstAccount = accountDaoFacade.select(firstAccount);
        assertSame(4, groupMembersAreModerators.size());
        assertSame(2L, groupMembersAreModerators.entrySet().stream().filter(Map.Entry::getValue).count());
    }

    @Test
    public void testGetUnJoinedGroupsNotRequested() {
        groupRequestsDao.create(firstAccount.getId(), firstGroup.getId());
        groupRequestsDao.create(firstAccount.getId(), secondGroup.getId());
        groupMembershipDao.joinGroup(firstAccount.getId(), thirdGroup.getId());

        Map<Group, Boolean> unJoinedGroups = additionalManyToManyDao.selectUnJoinedGroupsAreRequested(firstAccount.getId());
        assertSame(3, unJoinedGroups.size());
        int newSize = unJoinedGroups.entrySet().stream().filter(Map.Entry::getValue).collect(Collectors.toMap(
                Map.Entry::getKey, (e) -> true)).size();
        //todo check
//        assertSame(2L, unJoinedGroups.stream().filter(UnJoinedGroups::isRequestSent).count());
        assertSame(2, newSize);
    }
}
