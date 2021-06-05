package com.getjavajob.training.yarginy.socialnetwork.service;

import com.getjavajob.training.yarginy.socialnetwork.common.exceptions.IncorrectData;
import com.getjavajob.training.yarginy.socialnetwork.common.exceptions.IncorrectDataException;
import com.getjavajob.training.yarginy.socialnetwork.common.models.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.Group;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.GroupDaoFacade;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.GroupsMembersDaoFacade;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.GroupsModeratorsDaoFacade;
import com.getjavajob.training.yarginy.socialnetwork.dao.utils.TransactionPerformer;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;

@Service
public class GroupServiceImpl implements GroupService {
    private final GroupDaoFacade groupDaoFacade;
    private final GroupsMembersDaoFacade membersDao;
    private final GroupsModeratorsDaoFacade moderatorsDao;
    private final TransactionPerformer transactionPerformer;
    private final GroupServiceTransactional serviceTransactional;

    public GroupServiceImpl(GroupDaoFacade groupDaoFacade, GroupsMembersDaoFacade membersDao,
                            GroupsModeratorsDaoFacade moderatorsDao, TransactionPerformer transactionPerformer,
                            GroupServiceTransactional serviceTransactional) {
        this.groupDaoFacade = groupDaoFacade;
        this.membersDao = membersDao;
        this.moderatorsDao = moderatorsDao;
        this.transactionPerformer = transactionPerformer;
        this.serviceTransactional = serviceTransactional;
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
    public Group getFullInfo(long id) {
        return groupDaoFacade.selectFullInfo(id);
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
        return transactionPerformer.perform(() -> serviceTransactional.acceptRequestTransactional(accountId, groupId));
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
        boolean isRemoved = groupDaoFacade.removeMember(accountId, groupId);
        if (isRemoved) {
            moderatorsDao.deleteGroupModerator(accountId, groupId);
        }
        return isRemoved;
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
        return transactionPerformer.perform(() -> serviceTransactional.createGroupAndInviteOwner(group));
    }

    @Override
    public boolean removeGroup(Group group) {
        return groupDaoFacade.delete(group);
    }

    @Override
    public boolean updateGroup(Group group) {
        if (!groupDaoFacade.update(group)) {
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
        return moderatorsDao.getGroupMembersAreModerators(groupId);
    }

    @Override
    public Map<Group, Boolean> getAllUnjoinedGroupsAreRequested(long accountId) {
        return membersDao.getAllUnjoinedGroupsAreRequested(accountId);
    }
}
