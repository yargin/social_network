package com.getjavajob.training.yarginy.socialnetwork.dao.jpa.relationdaos.manytomany.implementations;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.Group;
import com.getjavajob.training.yarginy.socialnetwork.common.models.manytomany.GroupMembership;
import com.getjavajob.training.yarginy.socialnetwork.common.models.manytomany.ManyToMany;
import com.getjavajob.training.yarginy.socialnetwork.dao.jpa.relationdaos.manytomany.GenericManyToMany;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.Collection;

import static com.getjavajob.training.yarginy.socialnetwork.common.models.manytomany.GroupMembership.createGroupMembershipKey;

@Repository
public class GroupMembershipDao extends GenericManyToMany<Account, Group> {
    @Override
    protected ManyToMany<Account, Group> genericGetReference(EntityManager entityManager, long accountId,
                                                             long groupId) {
        return entityManager.getReference(GroupMembership.class, createGroupMembershipKey(accountId, groupId));
    }

    @Override
    protected ManyToMany<Account, Group> genericFind(EntityManager entityManager, long accountId, long groupId) {
        return entityManager.find(GroupMembership.class, createGroupMembershipKey(accountId, groupId));
    }

    @Override
    protected ManyToMany<Account, Group> genericCreateObject(EntityManager entityManager, long accountId,
                                                             long groupId) {
        Account account = entityManager.getReference(Account.class, accountId);
        Group group = entityManager.getReference(Group.class, groupId);
        return new GroupMembership(account, group);
    }

    @Override
    public Collection<Group> genericSelectByFirst(EntityManager entityManager, long accountId) {
        Account account = new Account(accountId);
        TypedQuery<Group> query = entityManager.createQuery("select gm.group from GroupMembership gm " +
                "join gm.group where gm.account = :account", Group.class);
        query.setParameter("account", account);
        return query.getResultList();
    }

    @Override
    public Collection<Account> genericSelectBySecond(EntityManager entityManager, long groupId) {
        Group group = new Group(groupId);
        TypedQuery<Account> query = entityManager.createQuery("select g.account from GroupMembership g " +
                "join g.account where g.group = :group", Account.class);
        query.setParameter("group", group);
        return query.getResultList();
    }
}
