 package com.getjavajob.training.yarginy.socialnetwork.dao.jpa.relationdaos.manytomany.implementations;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.Group;
import com.getjavajob.training.yarginy.socialnetwork.common.models.manytomany.GroupModerator;
import com.getjavajob.training.yarginy.socialnetwork.common.models.manytomany.ManyToMany;
import com.getjavajob.training.yarginy.socialnetwork.dao.jpa.relationdaos.manytomany.GenericManyToMany;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.Collection;

import static com.getjavajob.training.yarginy.socialnetwork.common.models.manytomany.GroupModerator.createGroupModeratorKey;

@Repository
public class GroupModeratorsDao extends GenericManyToMany<Account, Group> {
    @Override
    protected ManyToMany<Account, Group> genericGetReference(EntityManager entityManager, long accountId,
                                                             long groupId) {
        return entityManager.getReference(GroupModerator.class, createGroupModeratorKey(accountId, groupId));
    }

    @Override
    protected ManyToMany<Account, Group> genericFind(EntityManager entityManager, long accountId, long groupId) {
        return entityManager.find(GroupModerator.class, createGroupModeratorKey(accountId, groupId));
    }

    @Override
    protected ManyToMany<Account, Group> genericCreateObject(EntityManager entityManager, long accountId,
                                                             long groupId) {
        Account account = entityManager.getReference(Account.class, accountId);
        Group group = entityManager.getReference(Group.class, groupId);
        return new GroupModerator(account, group);
    }

    @Override
    public Collection<Group> genericSelectByFirst(EntityManager entityManager, long accountId) {
        Account account = new Account(accountId);
        TypedQuery<Group> query = entityManager.createQuery("select g.group from GroupModerator g join g.group " +
                "where g.account = :account", Group.class);
        query.setParameter("account", account);
        return query.getResultList();
    }

    @Override
    public Collection<Account> genericSelectBySecond(EntityManager entityManager, long groupId) {
        Group group = new Group(groupId);
        TypedQuery<Account> query = entityManager.createQuery("select g.account from GroupModerator g " +
                "join g.account where g.group = :group", Account.class);
        query.setParameter("group", group);
        return query.getResultList();
    }
}
