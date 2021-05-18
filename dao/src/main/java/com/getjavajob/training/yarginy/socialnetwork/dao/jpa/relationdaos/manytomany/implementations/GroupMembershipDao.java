package com.getjavajob.training.yarginy.socialnetwork.dao.jpa.relationdaos.manytomany.implementations;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.Group;
import com.getjavajob.training.yarginy.socialnetwork.common.models.manytomany.GroupMembership;
import com.getjavajob.training.yarginy.socialnetwork.common.models.manytomany.ManyToMany;
import com.getjavajob.training.yarginy.socialnetwork.dao.jpa.relationdaos.manytomany.GenericManyToMany;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.math.BigInteger;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.getjavajob.training.yarginy.socialnetwork.common.models.manytomany.GroupMembership.createGroupMembershipKey;
import static java.util.Objects.isNull;

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
    protected ManyToMany<Account, Group> genericCreateObject(EntityManager entityManager, long accountId, long groupId) {
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

    @Transactional
    public Map<Group, Boolean> selectUnJoinedGroupsAreRequested(long accountId) {
        Query query = entityManager.createNativeQuery("SELECT g.id, g.name, gmr.group_id requested" +
                " FROM `Groups` g LEFT JOIN " +
                " (SELECT group_id FROM groups_memberships_requests" +
                " WHERE account_id = ?) gmr " +
                " ON gmr.group_id = g.id " +
                " WHERE g.id NOT IN " +
                " (SELECT group_id FROM groups_members" +
                " WHERE account_id = ?)");
        query.setParameter(1, accountId);
        query.setParameter(2, accountId);
        List<Object[]> result = query.getResultList();
        Map<Group, Boolean> unJoinedGroupAreRequested = new HashMap<>();
        for (Object[] raw : result) {
            Group group = new Group(((BigInteger) raw[0]).longValue());
            group.setName((String) raw[1]);
            unJoinedGroupAreRequested.put(group, !isNull(raw[2]));
        }
        return unJoinedGroupAreRequested;
    }
}
