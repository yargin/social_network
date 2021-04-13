package com.getjavajob.training.yarginy.socialnetwork.dao.jpa;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.Group;
import com.getjavajob.training.yarginy.socialnetwork.dao.jdbctemplates.modeldao.AccountDao;
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

import static java.sql.Date.*;
import static java.time.LocalDate.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:daoSpringConfig.xml", "classpath:daoTestJpaSpringConfig.xml"})
public class JpaGroupDaoTest {
    private final Account owner= new Account("testName", "testSurname", "testEmail");
    private final Group group = new Group("testName", owner);
    @Autowired
    private JpaGroupDao groupDao;
    @Autowired
    private JpaAccountDao accountDao;

    @Before
    public void initValues() {
        accountDao.create(owner);
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
        assertTrue(groupDao.create(newGroup));
        assertEquals(newGroup, groupDao.select(newGroup));
        assertFalse(groupDao.create(newGroup));
        assertFalse(groupDao.create(new Group("newGroup", owner)));
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
        group.setOwner(newOwner);
        assertTrue(groupDao.update(group));
        assertEquals(newOwner, groupDao.select(group).getOwner());
    }
}
