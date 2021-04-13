package com.getjavajob.training.yarginy.socialnetwork.dao.jpa.relationsdao.manytomany;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.Group;
import com.getjavajob.training.yarginy.socialnetwork.common.models.manytomany.GroupMembership;
import com.getjavajob.training.yarginy.socialnetwork.common.models.manytomany.JpaManyToMany;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.Collection;

import static com.getjavajob.training.yarginy.socialnetwork.common.models.manytomany.GroupMembership.createGroupMembershipKey;


@Repository("jpaGroupMembershipDao")
public class JpaGroupMembershipDao extends GenericManyToManyDao<Account, Group> {
    @Override
    protected JpaManyToMany<Account, Group> genericGetReference(EntityManager entityManager, long accountId,
                                                                long groupId) {
        return entityManager.getReference(GroupMembership.class, createGroupMembershipKey(accountId, groupId));
    }

    @Override
    protected JpaManyToMany<Account, Group> genericFind(EntityManager entityManager, long accountId, long groupId) {
        return entityManager.find(GroupMembership.class, createGroupMembershipKey(accountId, groupId));
    }

    @Override
    protected JpaManyToMany<Account, Group> genericCreateObject(EntityManager entityManager, long accountId,
                                                                long groupId) {
        Account account = entityManager.getReference(Account.class, accountId);
        Group group = entityManager.getReference(Group.class, groupId);
        return new GroupMembership(account, group);
    }

    @Override
    public Collection<Group> genericSelectByFirst(long accountId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Account account = new Account(accountId);
        TypedQuery<Group> query = entityManager.createQuery("select g.group from GroupMembership g " +
                "where g.account = :account", Group.class);
        query.setParameter("account", account);
        return query.getResultList();
    }

    @Override
    public Collection<Account> genericSelectBySecond(long groupId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Group group = new Group(groupId);
        TypedQuery<Account> query = entityManager.createQuery("select g.account from GroupMembership g " +
                "where g.group = :group", Account.class);
        query.setParameter("group", group);
        return query.getResultList();
    }
}
