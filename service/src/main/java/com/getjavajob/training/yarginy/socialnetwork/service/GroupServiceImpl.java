package com.getjavajob.training.yarginy.socialnetwork.service;

import com.getjavajob.training.yarginy.socialnetwork.common.exceptions.IncorrectData;
import com.getjavajob.training.yarginy.socialnetwork.common.exceptions.IncorrectDataException;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.group.Group;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.*;
import com.getjavajob.training.yarginy.socialnetwork.dao.factories.connectionpool.Transaction;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Map;

public class GroupServiceImpl implements GroupService {
    private final GroupDao groupDao = new GroupDaoImpl();
    private final GroupsMembersDao membersDao = new GroupsMembersDaoImpl();
    private final GroupsModeratorsDao moderatorsDao = new GroupsModeratorsDaoImpl();
    private final TransactionManager transactionManager = new TransactionManager();
    private final DataSetsDao dataSetsDao = new DataSetsDaoImpl();

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
    public boolean acceptRequest(long accountId, long groupId) {
        try (Transaction transaction = transactionManager.getTransaction()) {
            if (!membersDao.joinGroup(accountId, groupId)) {
                return false;
            }
            if (!membersDao.removeRequest(accountId, groupId)) {
                transaction.rollback();
                return false;
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean declineRequest(long accountId, long groupId) {
        return membersDao.removeRequest(accountId, groupId);
    }

    @Override
    public boolean leaveGroup(long accountId, long groupId) {
        try (Transaction transaction = transactionManager.getTransaction()) {
            moderatorsDao.deleteGroupModerator(accountId, groupId);
            if (!groupDao.removeMember(accountId, groupId)) {
                transaction.rollback();
                return false;
            }
            return true;
        } catch (Exception e) {
            return false;
        }
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
        try (Transaction transaction = transactionManager.getTransaction()) {
            try {
                if (!groupDao.create(group)) {
                    throw new IncorrectDataException(IncorrectData.GROUP_DUPLICATE);
                }
                group = groupDao.select(group);
                if (!groupDao.addMember(group.getOwner().getId(), group.getId()) ||
                        !moderatorsDao.addGroupModerator(group.getOwner().getId(), group.getId())) {
                    throw new IllegalArgumentException();
                }
            } catch (IllegalArgumentException e) {
                transaction.rollback();
                return false;
            }
            return true;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
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

    @Override
    public Map<Account, Boolean> getGroupMembersModerators(long groupId) {
        return dataSetsDao.getGroupMembersAreModerators(groupId);
    }

    @Override
    public Map<Group, Boolean> getAllUnjoinedGroupsAreRequested(long accountId) {
        return dataSetsDao.getAllUnjoinedGroupsAreRequested(accountId);
    }
}
