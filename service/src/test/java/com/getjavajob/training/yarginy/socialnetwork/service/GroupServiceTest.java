package com.getjavajob.training.yarginy.socialnetwork.service;

import com.getjavajob.training.yarginy.socialnetwork.common.exceptions.DataFlowViolationException;
import com.getjavajob.training.yarginy.socialnetwork.common.exceptions.IncorrectDataException;
import com.getjavajob.training.yarginy.socialnetwork.common.models.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.Group;
import com.getjavajob.training.yarginy.socialnetwork.dao.jdbctemplates.facades.GroupDaoFacade;
import com.getjavajob.training.yarginy.socialnetwork.dao.jdbctemplates.facades.GroupsModeratorsDaoFacade;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GroupServiceTest {
    @Mock
    private GroupDaoFacade groupDaoFacade;
    @Mock
    private GroupsModeratorsDaoFacade moderatorsDao;
    @InjectMocks
    private GroupServiceImpl groupService;

    @Test
    public void testCreateGroup() {
        Account owner = new Account("test", "test", "test@email.com");
        owner.setId(1);
        Group group = new Group("testGroup", null);
        group.setId(1);
        assertThrows(DataFlowViolationException.class, () -> groupService.createGroup(group));
        group.setOwner(owner);
        when(groupDaoFacade.create(group)).thenReturn(false);
        assertThrows(IncorrectDataException.class, () -> groupService.createGroup(group));
        when(groupDaoFacade.create(group)).thenReturn(true);
        when(groupDaoFacade.select(group)).thenReturn(group);
        when(groupDaoFacade.addMember(1, 1)).thenReturn(true);
        when(moderatorsDao.addGroupModerator(1, 1)).thenReturn(false);
        assertFalse(groupService.createGroup(group));
        when(moderatorsDao.addGroupModerator(1, 1)).thenReturn(true);
        assertTrue(groupService.createGroup(group));
    }
}
