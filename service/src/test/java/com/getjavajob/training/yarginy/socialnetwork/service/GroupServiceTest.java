package com.getjavajob.training.yarginy.socialnetwork.service;

import com.getjavajob.training.yarginy.socialnetwork.common.exceptions.DataFlowViolationException;
import com.getjavajob.training.yarginy.socialnetwork.common.exceptions.IncorrectDataException;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.AccountImpl;
import com.getjavajob.training.yarginy.socialnetwork.common.models.group.Group;
import com.getjavajob.training.yarginy.socialnetwork.common.models.group.GroupImpl;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.GroupDaoFacade;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.GroupsModeratorsDaoFacade;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;
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
        Account owner = new AccountImpl("test", "test", "test@email.com");
        owner.setId(1);
        Group group = new GroupImpl("testGroup", null);
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
