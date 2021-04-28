package com.getjavajob.training.yarginy.socialnetwork.dao.relationdaos.onetomany.implementations;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.Group;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldaos.implementations.GroupDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.relationdaos.onetomany.OneToManyDao;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Collection;

import static java.util.Objects.isNull;

@Repository
public class OwnerGroupsDao implements OneToManyDao<Group> {
    @PersistenceContext
    private transient EntityManager entityManager;
    private final GroupDao groupDao;

    public OwnerGroupsDao(GroupDao groupDao) {
        this.groupDao = groupDao;
    }

    @Override
    @Transactional
    public Collection<Group> selectMany(long accountId) {
        Account account = new Account(accountId);
        TypedQuery<Group> selectMany = entityManager.createQuery("select g from Group g join fetch g.owner o" +
                " where g.owner = :owner", Group.class);
        selectMany.setParameter("owner", account);
        return selectMany.getResultList();
    }

    @Override
    public boolean relationExists(long accountId, long groupId) {
        Group group = groupDao.select(groupId);
        return !isNull(group.getOwner()) && group.getOwner().getId() == accountId;
    }
}
