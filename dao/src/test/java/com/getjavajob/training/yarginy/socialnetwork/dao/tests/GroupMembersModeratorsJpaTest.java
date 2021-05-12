package com.getjavajob.training.yarginy.socialnetwork.dao.tests;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.Group;
import com.getjavajob.training.yarginy.socialnetwork.common.models.manytomany.GroupMembersModerators;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.AccountDaoFacadeImpl;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.GroupDaoFacadeImpl;
import com.getjavajob.training.yarginy.socialnetwork.dao.jpa.relationdaos.manytomany.implementations.GroupMembershipDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.jpa.relationdaos.manytomany.implementations.GroupModeratorsDao;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Collection;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:daoSpringConfig.xml", "classpath:daoTestOverrideSpringConfig.xml"})
public class GroupMembersModeratorsJpaTest {
    @Autowired
    private GroupDaoFacadeImpl groupDao;
    @Autowired
    private AccountDaoFacadeImpl accountDaoFacade;
    @Autowired
    private GroupMembershipDao groupMembershipDao;
    @Autowired
    private GroupModeratorsDao groupModeratorsDao;
    private Group group;

    @Before
    public void initValues() {
        Account first = new Account("first", "first", "first");
        accountDaoFacade.create(first);
        first = accountDaoFacade.select(first);
        Account second = new Account("second", "second", "second");
        accountDaoFacade.create(second);
        second = accountDaoFacade.select(second);
        Account third = new Account("third", "third", "third");
        accountDaoFacade.create(third);
        third = accountDaoFacade.select(third);
        Account forth = new Account("forth", "forth", "forth");
        accountDaoFacade.create(forth);
        forth = accountDaoFacade.select(forth);
        group = new Group("test", first);
        group.setCreationDate(Date.valueOf(LocalDate.of(2020, 2, 2)));
        groupDao.create(group);
        group = groupDao.select(group);
        groupMembershipDao.create(first.getId(), group.getId());
        groupMembershipDao.create(second.getId(), group.getId());
        groupMembershipDao.create(third.getId(), group.getId());
        groupMembershipDao.create(forth.getId(), group.getId());
        groupModeratorsDao.create(first.getId(), group.getId());
        groupModeratorsDao.create(second.getId(), group.getId());

    }
    @Test
    public void testCreateSelectMembersAndModerators() {
        group = groupDao.select(group);
        System.out.println("====================================");
        Collection<GroupMembersModerators> groupMembersModerators = groupMembershipDao.getMembers(group);
        System.out.println("====================================");
    }
}
