package com.getjavajob.training.yarginy.socialnetwork.dao.tests;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.Group;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.AccountDaoFacade;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.GroupDaoFacade;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.Date;
import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:daoSpringConfig.xml", "classpath:daoTestJpaSpringConfig.xml"})
public class GroupDaoFacadeTest {
    @Autowired
    private AccountDaoFacade accountDaoFacade;
    @Autowired
    private GroupDaoFacade groupDaoFacade;
    private Group group = new Group();
    private Account account = new Account("test", "test", "test@test.com");
    private Account newOwner = new Account("testOwner", "newOwner", "newOwner@test.test");

    @Before
    public void testValuesInit() {
        accountDaoFacade.create(account);
        account = accountDaoFacade.select(account);
        group.setOwner(account);
        group.setName("testGroup");
        group.setCreationDate(Date.valueOf(LocalDate.of(2020, 2, 2)));
    }

    @After
    public void testValuesDelete() {
        accountDaoFacade.delete(account);
        accountDaoFacade.delete(newOwner);
        group = groupDaoFacade.select(group);
        groupDaoFacade.delete(group);
    }

    @Test
    public void testCreateGroup() {
        assertTrue(groupDaoFacade.create(group));
    }

    @Test
    public void testNullFieldCreate() {
        try {
            group.setName(null);
            groupDaoFacade.create(group);
        } catch (IllegalArgumentException e) {
            assertFalse(false);
        }
    }

    @Test
    public void testCreateExistingGroup() {
        groupDaoFacade.create(group);
        assertFalse(groupDaoFacade.create(group));
    }

    @Test
    public void testUpdateOwner() {
        groupDaoFacade.create(group);
        accountDaoFacade.create(newOwner);
        newOwner = accountDaoFacade.select(newOwner);
        group.setOwner(newOwner);
        assertTrue(groupDaoFacade.update(group));
    }

    @Test
    public void testSelectGroup() {
        groupDaoFacade.create(group);
        Group actual = groupDaoFacade.select(group);
        assertEquals(group, actual);
        actual = groupDaoFacade.select(actual.getId());
        assertEquals(group, actual);
    }

    @Test
    public void testSelectNonExistingGroup() {
        Group actual = groupDaoFacade.select(group);
        assertEquals(groupDaoFacade.getNullModel(), actual);
    }

    @Test
    public void testUpdateGroup() {
        groupDaoFacade.create(group);
        String newDescription = "new Description";
        group.setDescription(newDescription);
        Group storedGroup = groupDaoFacade.select(group);
        assertTrue(groupDaoFacade.update(group));
        storedGroup = groupDaoFacade.select(group);
        assertEquals(newDescription, storedGroup.getDescription());
    }

    @Test
    public void testUpdateNonExistingGroup() {
        Group anotherNonExisting = new Group("another non existing group", account);
        assertFalse(groupDaoFacade.update(anotherNonExisting));
    }

    @Test
    public void testDeleteNonExisting() {
        Group nonExisting = new Group();
        nonExisting.setName("non existing group");
        nonExisting.setId(66666666666L);
        assertFalse(groupDaoFacade.delete(nonExisting));
    }

    @Test
    public void testDeleteGroup() {
        groupDaoFacade.create(group);
        group.setId(groupDaoFacade.select(group).getId());
        assertTrue(groupDaoFacade.delete(group));
        assertEquals(groupDaoFacade.getNullModel(), groupDaoFacade.select(group));
    }

    @Test
    public void testOwner() {
        groupDaoFacade.create(group);
        Account actualOwner = accountDaoFacade.select(account);
        Group group = groupDaoFacade.select(this.group);
        assertTrue(groupDaoFacade.isOwner(actualOwner.getId(), group.getId()));
        groupDaoFacade.delete(this.group);
    }
}
