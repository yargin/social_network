package com.getjavajob.training.yarginy.socialnetwork.dao.jpa.relationdaos.manytomany.implementations;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.Group;
import com.getjavajob.training.yarginy.socialnetwork.common.models.manytomany.GroupRequest;
import com.getjavajob.training.yarginy.socialnetwork.common.models.manytomany.ManyToMany;
import com.getjavajob.training.yarginy.socialnetwork.common.utils.ModelsFactory;
import com.getjavajob.training.yarginy.socialnetwork.dao.jpa.relationdaos.manytomany.GenericManyToMany;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.Collection;

import static com.getjavajob.training.yarginy.socialnetwork.common.models.manytomany.GroupRequest.createGroupRequestKey;

@Repository
public class GroupRequestsDao extends GenericManyToMany<Account, Group> {
    private final ModelsFactory factory;

    public GroupRequestsDao(ModelsFactory factory) {
        this.factory = factory;
    }

    @Override
    protected ManyToMany<Account, Group> genericGetReference(EntityManager entityManager, long accountId,
                                                             long groupId) {
        return entityManager.getReference(GroupRequest.class, createGroupRequestKey(accountId, groupId));
    }

    @Override
    protected ManyToMany<Account, Group> genericFind(EntityManager entityManager, long accountId, long groupId) {
        return entityManager.find(GroupRequest.class, createGroupRequestKey(accountId, groupId));
    }

    @Override
    protected ManyToMany<Account, Group> genericCreateObject(EntityManager entityManager, long accountId,
                                                             long groupId) {
        Account account = entityManager.getReference(Account.class, accountId);
        Group group = entityManager.getReference(Group.class, groupId);
        return new GroupRequest(account, group);
    }

    @Override
    public Collection<Group> genericSelectByFirst(EntityManager entityManager, long accountId) {
        Account account = factory.getAccount(accountId);
        TypedQuery<Group> query = entityManager.createQuery("select g.group from GroupRequest g join g.group " +
                "where g.account = :account", Group.class);
        query.setParameter("account", account);
        return query.getResultList();
    }

    @Override
    public Collection<Account> genericSelectBySecond(EntityManager entityManager, long groupId) {
        Group group = factory.getGroup(groupId);
        TypedQuery<Account> query = entityManager.createQuery("select g.account from GroupRequest g " +
                "join g.account where g.group = :group", Account.class);
        query.setParameter("group", group);
        return query.getResultList();
    }
}
