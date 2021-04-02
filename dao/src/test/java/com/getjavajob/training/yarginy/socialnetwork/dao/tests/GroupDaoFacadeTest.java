package com.getjavajob.training.yarginy.socialnetwork.dao.tests;

import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.group.Group;
import com.getjavajob.training.yarginy.socialnetwork.dao.jdbctemplates.facades.AccountDaoFacade;
import com.getjavajob.training.yarginy.socialnetwork.dao.jdbctemplates.facades.GroupDaoFacade;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:daoSpringConfig.xml", "classpath:daoTestH2OverrideSpringConfig.xml"})
public class GroupDaoFacadeTest {
    @Autowired
    private AccountDaoFacade accountDaoFacade;
    @Autowired
    private GroupDaoFacade groupDaoFacade;
    private final Group GROUP = new Group();
    private Account account = new Account("test", "test", "test@test.com");

    @Before
    public void testValuesInit() {
        accountDaoFacade.create(account);
        account = accountDaoFacade.select(account);
        GROUP.setOwner(account);
        GROUP.setName("testGroup");
        GROUP.setId(groupDaoFacade.select(GROUP).getId());
        groupDaoFacade.delete(GROUP);
    }

    @After
    public void testValuesDelete() {
        accountDaoFacade.delete(account);
        GROUP.setId(groupDaoFacade.select(GROUP).getId());
        groupDaoFacade.delete(GROUP);
    }

    @Test
    public void testCreateGroup() {
        assertTrue(groupDaoFacade.create(GROUP));
    }

    @Test
    public void testNullFieldCreate() {
        try {
            GROUP.setName(null);
            groupDaoFacade.create(GROUP);
        } catch (IllegalArgumentException e) {
            assertFalse(false);
        }
    }

    @Test
    public void testCreateExistingGroup() {
        groupDaoFacade.create(GROUP);
        assertFalse(groupDaoFacade.create(GROUP));
    }

    @Test
    public void testUpdateOwner() {
        groupDaoFacade.create(GROUP);
        Group storedGroup = groupDaoFacade.select(GROUP);
        Account newOwner = new Account("testOwner", "newOwner", "newOwner@test.test");
        accountDaoFacade.create(newOwner);
        newOwner = accountDaoFacade.select(newOwner);
        GROUP.setOwner(newOwner);
        assertTrue(groupDaoFacade.update(GROUP, storedGroup));
    }

    @Test
    public void testSelectGroup() {
        groupDaoFacade.create(GROUP);
        Group actual = groupDaoFacade.select(GROUP);
        assertEquals(GROUP, actual);
        actual = groupDaoFacade.select(actual.getId());
        assertEquals(GROUP, actual);
    }

    @Test
    public void testSelectNonExistingGroup() {
        Group actual = groupDaoFacade.select(GROUP);
        assertEquals(groupDaoFacade.getNullModel(), actual);
    }

    @Test
    public void testUpdateGroup() {
        groupDaoFacade.create(GROUP);
        String newDescription = "new Description";
        GROUP.setDescription(newDescription);
        Group storedGroup = groupDaoFacade.select(GROUP);
        assertTrue(groupDaoFacade.update(GROUP, storedGroup));
        storedGroup = groupDaoFacade.select(GROUP);
        assertEquals(newDescription, storedGroup.getDescription());
    }

    @Test
    public void testUpdateNonExistingGroup() {
        Group nonExisting = new Group();
        nonExisting.setName("non existing group");
        Group anotherNonExisting = new Group();
        anotherNonExisting.setName("another non existing group");
        assertFalse(groupDaoFacade.update(anotherNonExisting, nonExisting));
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
        groupDaoFacade.create(GROUP);
        GROUP.setId(groupDaoFacade.select(GROUP).getId());
        assertTrue(groupDaoFacade.delete(GROUP));
        assertEquals(groupDaoFacade.getNullModel(), groupDaoFacade.select(GROUP));
    }

    @Test
    public void testOwner() {
        groupDaoFacade.create(GROUP);
        Account actualOwner = accountDaoFacade.select(account);
        Group group = groupDaoFacade.select(GROUP);
        assertTrue(groupDaoFacade.isOwner(actualOwner.getId(), group.getId()));
        groupDaoFacade.delete(GROUP);
    }
}
