package com.getjavajob.training.yarginy.socialnetwork.service;

import com.getjavajob.training.yarginy.socialnetwork.common.exceptions.DataFlowViolationException;
import com.getjavajob.training.yarginy.socialnetwork.common.exceptions.IncorrectData;
import com.getjavajob.training.yarginy.socialnetwork.common.exceptions.IncorrectDataException;
import com.getjavajob.training.yarginy.socialnetwork.common.models.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.Group;
import com.getjavajob.training.yarginy.socialnetwork.dao.jdbctemplates.facades.DataSetsDaoFacade;
import com.getjavajob.training.yarginy.socialnetwork.dao.jdbctemplates.facades.GroupDaoFacade;
import com.getjavajob.training.yarginy.socialnetwork.dao.jdbctemplates.facades.GroupsMembersDaoFacade;
import com.getjavajob.training.yarginy.socialnetwork.dao.jdbctemplates.facades.GroupsModeratorsDaoFacade;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Map;

import static java.util.Objects.isNull;

@Service
public class GroupServiceImpl implements GroupService {
    private final GroupDaoFacade groupDaoFacade;
    private final GroupsMembersDaoFacade membersDao;
    private final GroupsModeratorsDaoFacade moderatorsDao;
    private final DataSetsDaoFacade dataSetsDaoFacade;

    public GroupServiceImpl(GroupDaoFacade groupDaoFacade, GroupsMembersDaoFacade membersDao,
                            GroupsModeratorsDaoFacade moderatorsDao, DataSetsDaoFacade dataSetsDaoFacade) {
        this.groupDaoFacade = groupDaoFacade;
        this.membersDao = membersDao;
        this.moderatorsDao = moderatorsDao;
        this.dataSetsDaoFacade = dataSetsDaoFacade;
    }

    @Override
    public Group get(Group group) {
        return groupDaoFacade.select(group);
    }

    @Override
    public Group get(long id) {
        return groupDaoFacade.select(id);
    }

    @Override
    public Collection<Group> getAccountGroups(long accountId) {
        return membersDao.selectAccountGroups(accountId);
    }

    @Override
    public Collection<Group> getAllGroups() {
        return groupDaoFacade.selectAll();
    }

    @Override
    public boolean isOwner(long accountId, long groupId) {
        return groupDaoFacade.isOwner(accountId, groupId);
    }

    @Override
    public boolean acceptRequest(long accountId, long groupId) {
        if (!membersDao.joinGroup(accountId, groupId)) {
            return false;
        }
        return membersDao.removeRequest(accountId, groupId);
    }

    @Override
    public boolean declineRequest(long accountId, long groupId) {
        return membersDao.removeRequest(accountId, groupId);
    }

    @Override
    public boolean leaveGroup(long accountId, long groupId) {
        if (groupDaoFacade.isOwner(accountId, groupId)) {
            return false;
        }
        moderatorsDao.deleteGroupModerator(accountId, groupId);
        return groupDaoFacade.removeMember(accountId, groupId);
    }

    @Override
    public boolean sendGroupRequest(long accountId, long groupId) {
        return membersDao.createRequest(accountId, groupId);
    }

    @Override
    public boolean isMembershipRequester(long accountId, long groupId) {
        return membersDao.isRequester(accountId, groupId);
    }

    @Override
    public Collection<Account> getGroupRequests(long groupId) {
        return membersDao.selectRequests(groupId);
    }

    @Override
    public Collection<Account> getModerators(long groupId) {
        return moderatorsDao.selectModerators(groupId);
    }

    @Override
    public boolean addModerator(long accountId, long groupId) {
        return moderatorsDao.addGroupModerator(accountId, groupId);
    }

    @Override
    public boolean removeModerator(long accountId, long groupId) {
        return moderatorsDao.deleteGroupModerator(accountId, groupId);
    }

    @Override
    public boolean createGroup(Group group) {
        try {
            createGroupAndInviteOwner(group);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
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

    @Override
    public boolean removeGroup(Group group) {
        return groupDaoFacade.delete(group);
    }

    @Override
    public boolean updateGroup(Group group, Group storedGroup) {
        if (!groupDaoFacade.update(group, storedGroup)) {
            throw new IncorrectDataException(IncorrectData.GROUP_DUPLICATE);
        }
        return true;
    }

    @Override
    public boolean isMember(long accountId, long groupId) {
        return membersDao.isMember(accountId, groupId);
    }

    @Override
    public boolean isModerator(long accountId, long groupId) {
        return moderatorsDao.isModerator(accountId, groupId);
    }

    @Override
    public Map<Account, Boolean> getGroupMembersModerators(long groupId) {
        return dataSetsDaoFacade.getGroupMembersAreModerators(groupId);
    }

    @Override
    public Map<Group, Boolean> getAllUnjoinedGroupsAreRequested(long accountId) {
        return dataSetsDaoFacade.getAllUnjoinedGroupsAreRequested(accountId);
    }
}
