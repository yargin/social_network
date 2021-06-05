package com.getjavajob.training.yarginy.socialnetwork.dao.facades;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.Group;
import com.getjavajob.training.yarginy.socialnetwork.common.utils.NullModelsFactory;
import com.getjavajob.training.yarginy.socialnetwork.dao.jpa.relationdaos.manytomany.implementations.GroupMembershipDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.repositories.RepoGroupDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.utils.TransactionPerformer;
import org.springframework.stereotype.Component;

import java.util.Collection;

import static java.util.Objects.isNull;

@Component("groupDaoFacade")
public class GroupDaoFacadeImpl implements GroupDaoFacade {
    private final GroupMembershipDao accountsGroupMembershipDao;
    private final TransactionPerformer transactionPerformer;
    private final RepoGroupDao repoGroupDao;

    public GroupDaoFacadeImpl(GroupMembershipDao accountsGroupMembershipDao,
                              TransactionPerformer transactionPerformer, RepoGroupDao repoGroupDao) {
        this.accountsGroupMembershipDao = accountsGroupMembershipDao;
        this.transactionPerformer = transactionPerformer;
        this.repoGroupDao = repoGroupDao;
    }

    @Override
    public Group select(long id) {
        return repoGroupDao.findById(id).orElseGet(this::getNullGroup);
    }

    @Override
    public Group selectFullInfo(long id) {
        return repoGroupDao.getFullInfo(id);
    }

    @Override
    public Group select(Group group) {
        Group selectedGroup = repoGroupDao.findByName(group.getName());
        return isNull(selectedGroup) ? getNullGroup() : selectedGroup;
    }

    @Override
    public Group getNullModel() {
        return NullModelsFactory.getNullGroup();
    }

    @Override
    public boolean create(Group group) {
        return transactionPerformer.repoPerformCreate(group, repoGroupDao);
    }

    @Override
    public boolean update(Group group) {
        return transactionPerformer.repoPerformUpdateOrDelete(group, repoGroupDao, repoGroupDao::save);
    }

    @Override
    public boolean delete(Group group) {
        return transactionPerformer.repoPerformUpdateOrDelete(group, repoGroupDao, repoGroupDao::delete);
    }

    @Override
    public Collection<Group> selectAll() {
        return repoGroupDao.findAll();
    }

    @Override
    public Collection<Group> selectGroupsByOwner(long accountId) {
        return repoGroupDao.findByOwner(new Account(accountId));
    }

    @Override
    public boolean isOwner(long accountId, long groupId) {
        return !isNull(repoGroupDao.findByIdAndOwner(groupId, new Account(accountId)));
    }

    @Override
    public Group getNullGroup() {
        return NullModelsFactory.getNullGroup();
    }

    @Override
    public Collection<Account> selectMembers(long groupId) {
        return accountsGroupMembershipDao.selectBySecond(groupId);
    }

    @Override
    public boolean addMember(long accountId, long groupId) {
        return transactionPerformer.perform(() -> accountsGroupMembershipDao.create(accountId, groupId));
    }

    @Override
    public boolean removeMember(long accountId, long groupId) {
        return transactionPerformer.perform(() -> accountsGroupMembershipDao.delete(accountId, groupId));
    }
}
