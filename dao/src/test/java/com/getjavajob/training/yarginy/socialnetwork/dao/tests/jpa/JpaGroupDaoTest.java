package com.getjavajob.training.yarginy.socialnetwork.dao.tests.jpa;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.Group;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.AccountDaoFacadeImpl;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.GroupDaoFacadeImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Collection;

import static java.sql.Date.*;
import static java.time.LocalDate.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

//todo
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:daoSpringConfig.xml", "classpath:daoTestOverrideSpringConfig.xml"})
public class JpaGroupDaoTest {
    private final Account owner= new Account("testOwnerName", "testOwnerSurname", "testOwnerEmail");
    private Group group = new Group("testName", owner);
    @Autowired
    private GroupDaoFacadeImpl groupDao;
    @Autowired
    private AccountDaoFacadeImpl accountDao;

    @Before
    public void initValues() {
        accountDao.delete(owner);
        assertTrue(accountDao.create(owner));
        group.setOwner(accountDao.select(owner));
        group.setCreationDate(valueOf(of(2020, 2, 2)));
        groupDao.create(group);
    }

    @Test
    public void testGetGroupByIdentifier() {
        assertEquals(group, groupDao.select(group));
        assertEquals(groupDao.getNullModel(), groupDao.select(new Group("newTestGroup", owner)));
    }

    @After
    public void deleteValues() {
        group = groupDao.select(group);
        groupDao.delete(group);
        accountDao.delete(owner);
    }

    @Test
    public void testSelectAll() {
        Collection<Group> groups = groupDao.selectAll();
        assertTrue(groups.size() > 0);
    }

    @Test
    public void testGetGroupById() {
        Group selectedGroup = groupDao.select(group.getId());
        assertEquals(group, selectedGroup);
        selectedGroup = groupDao.select(22);
        assertEquals(groupDao.getNullModel(), selectedGroup);
    }

    @Test
    public void testDeleteGroup() {
        assertTrue(groupDao.delete(group));
        Collection<Group> groups = groupDao.selectAll();
        assertEquals(0, groups.size());
        assertFalse(groupDao.delete(group));
    }

    @Test
    public void testCreateGroup() {
        assertFalse(groupDao.create(group));
        Group newGroup = new Group("newGroup", owner);
        newGroup.setCreationDate(valueOf(of(2020, 2, 2)));
        assertTrue(groupDao.create(newGroup));
        assertEquals(newGroup, groupDao.select(newGroup));
        assertFalse(groupDao.create(newGroup));
        newGroup = new Group("newGroup", owner);
        newGroup.setCreationDate(valueOf(of(2020, 2, 2)));
        assertFalse(groupDao.create(newGroup));
        groupDao.delete(newGroup);
    }

    @Test
    public void testUpdateGroup() {
        String description = "new Description";
        group.setDescription(description);
        assertTrue(groupDao.update(group));
        assertEquals(description, groupDao.select(group).getDescription());
        Account newOwner = new Account("newOwner", "newOwner", "newOwner");
        accountDao.create(newOwner);
        group = groupDao.select(group);
        group.setOwner(newOwner);
        assertTrue(groupDao.update(group));
        assertEquals(newOwner, groupDao.selectFullInfo(group.getId()).getOwner());
    }
}
