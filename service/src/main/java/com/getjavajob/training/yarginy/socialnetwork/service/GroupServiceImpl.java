package com.getjavajob.training.yarginy.socialnetwork.service;

import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.group.Group;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.*;

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
    public Collection<Group> getAccountGroups(Account account) {
        return membersDao.selectAccountGroups(account);
    }

    @Override
    public Collection<Group> getAllGroups(Account account) {
        return groupDao.selectAll();
    }

    @Override
    public Collection<Group> getNonJoinedGroups(Account account) {
        Collection<Group> groups = getAllGroups(account);
        groups.removeAll(getAccountGroups(account));
        return groups;
    }

    @Override
    public boolean joinGroup(Account account, Group group) {
        return groupDao.addMember(group, account);
    }

    @Override
    public boolean leaveGroup(Account account, Group group) {
        return groupDao.removeMember(group, account);
    }

    @Override
    public boolean sendGroupRequest(Account account, Group group) {
        return membersDao.createRequest(account, group);
    }

    @Override
    public Collection<Account> getGroupRequests(Group group) {
        return membersDao.selectRequests(group);
    }

    @Override
    public Collection<Account> getModerators(Group group) {
        return moderatorsDao.selectModerators(group);
    }

    @Override
    public boolean addModerator(Account account, Group group) {
        return moderatorsDao.addGroupModerator(account, group);
    }

    @Override
    public boolean removeModerator(Account account, Group group) {
        return moderatorsDao.deleteGroupModerator(account, group);
    }

    @Override
    public boolean createGroup(Group group) {
        return groupDao.create(group);
    }

    @Override
    public boolean removeGroup(Group group) {
        return groupDao.delete(group);
    }
}
