package com.getjavajob.training.yarginy.socialnetwork.service;

import com.getjavajob.training.yarginy.socialnetwork.common.exceptions.DataFlowViolationException;
import com.getjavajob.training.yarginy.socialnetwork.common.exceptions.IncorrectData;
import com.getjavajob.training.yarginy.socialnetwork.common.exceptions.IncorrectDataException;
import com.getjavajob.training.yarginy.socialnetwork.common.models.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.Group;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.GroupDaoFacade;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.GroupsModeratorsDaoFacade;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDate;

import static java.util.Objects.isNull;

@Component
public class GroupServiceTransactional implements Serializable {
    private final GroupDaoFacade groupDaoFacade;
    private final GroupsModeratorsDaoFacade moderatorsDao;

    public GroupServiceTransactional(GroupDaoFacade groupDaoFacade, GroupsModeratorsDaoFacade moderatorsDao) {
        this.groupDaoFacade = groupDaoFacade;
        this.moderatorsDao = moderatorsDao;
    }

    public void createGroupAndInviteOwner(Group group) {
        Account owner = group.getOwner();
        if (isNull(owner)) {
            throw new DataFlowViolationException("owner can't be null");
        }
        group.setCreationDate(Date.valueOf(LocalDate.now()));
        if (!groupDaoFacade.create(group)) {
            throw new IncorrectDataException(IncorrectData.GROUP_DUPLICATE);
        }
        Group createdGroup = groupDaoFacade.select(group);
        if (!groupDaoFacade.addMember(owner.getId(), createdGroup.getId()) ||
                !moderatorsDao.addGroupModerator(owner.getId(), createdGroup.getId())) {
            throw new IllegalArgumentException();
        }
    }
}
