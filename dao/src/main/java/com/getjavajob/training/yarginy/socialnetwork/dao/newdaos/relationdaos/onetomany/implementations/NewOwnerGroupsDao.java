package com.getjavajob.training.yarginy.socialnetwork.dao.newdaos.relationdaos.onetomany.implementations;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.Group;
import com.getjavajob.training.yarginy.socialnetwork.dao.newdaos.modeldaos.implementations.NewGroupDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.newdaos.relationdaos.onetomany.NewOneToManyDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Collection;

import static java.util.Objects.isNull;

@Repository
public class NewOwnerGroupsDao implements NewOneToManyDao<Group> {
    @PersistenceContext
    private EntityManager entityManager;
    private NewGroupDao groupDao;

    @Autowired
    public void setGroupDao(NewGroupDao groupDao) {
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
