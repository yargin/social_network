package com.getjavajob.training.yarginy.socialnetwork.dao.relationdaos.manytomany.implementations;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.Group;
import com.getjavajob.training.yarginy.socialnetwork.common.models.manytomany.GroupModerator;
import com.getjavajob.training.yarginy.socialnetwork.common.models.manytomany.JpaManyToMany;
import com.getjavajob.training.yarginy.socialnetwork.dao.relationdaos.manytomany.GenericManyToMany;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.Collection;

import static com.getjavajob.training.yarginy.socialnetwork.common.models.manytomany.GroupModerator.createGroupModeratorKey;

@Repository
public class GroupModeratorsDao extends GenericManyToMany<Account, Group> {
    @Override
    protected JpaManyToMany<com.getjavajob.training.yarginy.socialnetwork.common.models.Account, com.getjavajob.training.yarginy.socialnetwork.common.models.Group> genericGetReference(EntityManager entityManager, long accountId,
                                                                                                                                                                                        long groupId) {
        return entityManager.getReference(GroupModerator.class, createGroupModeratorKey(accountId, groupId));
    }

    @Override
    protected JpaManyToMany<com.getjavajob.training.yarginy.socialnetwork.common.models.Account, com.getjavajob.training.yarginy.socialnetwork.common.models.Group> genericFind(EntityManager entityManager, long accountId, long groupId) {
        return entityManager.find(GroupModerator.class, createGroupModeratorKey(accountId, groupId));
    }

    @Override
    protected JpaManyToMany<com.getjavajob.training.yarginy.socialnetwork.common.models.Account, com.getjavajob.training.yarginy.socialnetwork.common.models.Group> genericCreateObject(EntityManager entityManager, long accountId,
                                                                                                                                                                                        long groupId) {
        com.getjavajob.training.yarginy.socialnetwork.common.models.Account account = entityManager.getReference(com.getjavajob.training.yarginy.socialnetwork.common.models.Account.class, accountId);
        com.getjavajob.training.yarginy.socialnetwork.common.models.Group group = entityManager.getReference(com.getjavajob.training.yarginy.socialnetwork.common.models.Group.class, groupId);
        return new GroupModerator(account, group);
    }

    @Override
    public Collection<com.getjavajob.training.yarginy.socialnetwork.common.models.Group> genericSelectByFirst(EntityManager entityManager, long accountId) {
        com.getjavajob.training.yarginy.socialnetwork.common.models.Account account = new com.getjavajob.training.yarginy.socialnetwork.common.models.Account(accountId);
        TypedQuery<com.getjavajob.training.yarginy.socialnetwork.common.models.Group> query = entityManager.createQuery("select g.group from GroupModerator g " +
                "where g.account = :account", com.getjavajob.training.yarginy.socialnetwork.common.models.Group.class);
        query.setParameter("account", account);
        return query.getResultList();
    }

    @Override
    public Collection<com.getjavajob.training.yarginy.socialnetwork.common.models.Account> genericSelectBySecond(EntityManager entityManager, long groupId) {
        com.getjavajob.training.yarginy.socialnetwork.common.models.Group group = new com.getjavajob.training.yarginy.socialnetwork.common.models.Group(groupId);
        TypedQuery<com.getjavajob.training.yarginy.socialnetwork.common.models.Account> query = entityManager.createQuery("select g.account from GroupModerator g " +
                "where g.group = :group", com.getjavajob.training.yarginy.socialnetwork.common.models.Account.class);
        query.setParameter("group", group);
        return query.getResultList();
    }
}
