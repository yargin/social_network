package com.getjavajob.training.yarginy.socialnetwork.service;

import com.getjavajob.training.yarginy.socialnetwork.common.exceptions.IncorrectData;
import com.getjavajob.training.yarginy.socialnetwork.common.exceptions.IncorrectDataException;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.group.Group;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.*;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Collection;

public class GroupServiceImpl implements GroupService {
    private final GroupDao groupDao = new GroupDaoImpl();
    private final GroupsMembersDao membersDao = new GroupsMembersDaoImpl();
    private final GroupsModeratorsDao moderatorsDao = new GroupsModeratorsDaoImpl();

    @Override
    public Group selectGroup(Group group) {
        return groupDao.select(group);
    }

    @Override
    public Group selectGroup(long id) {
        return groupDao.select(id);
    }

    @Override
    public Collection<Group> getAccountGroups(long accountId) {
        return membersDao.selectAccountGroups(accountId);
    }

    @Override
    public Collection<Group> getAllGroups() {
        return groupDao.selectAll();
    }

    @Override
    public Collection<Group> getNonJoinedGroups(long accountId) {
        Collection<Group> groups = getAllGroups();
        groups.removeAll(getAccountGroups(accountId));
        return groups;
    }

    @Override
    public boolean isOwner(long accountId, long groupId) {
        return groupDao.isOwner(groupId, accountId);
    }

    @Override
    public boolean joinGroup(long accountId, long groupId) {
        return groupDao.addMember(groupId, accountId);
    }

    @Override
    public boolean leaveGroup(long accountId, long groupId) {
        return groupDao.removeMember(groupId, accountId);
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
        group.setCreationDate(Date.valueOf(LocalDate.now()));
        if (!groupDao.create(group)) {
            throw new IncorrectDataException(IncorrectData.GROUP_DUPLICATE);
        }
        return true;
    }

    @Override
    public boolean removeGroup(Group group) {
        return groupDao.delete(group);
    }

    @Override
    public boolean updateGroup(Group group, Group storedGroup) {
        if (!groupDao.update(group, storedGroup)) {
            throw new IncorrectDataException(IncorrectData.GROUP_DUPLICATE);
        }
        return true;
    }

    @Override
    public Collection<Account> selectMembers(long groupId) {
        return groupDao.selectMembers(groupId);
    }

    @Override
    public boolean isMember(long accountId, long groupId) {
        return membersDao.isMember(accountId, groupId);
    }

    @Override
    public boolean isModerator(long accountId, long groupId) {
        return moderatorsDao.isModerator(accountId, groupId);
    }
}
