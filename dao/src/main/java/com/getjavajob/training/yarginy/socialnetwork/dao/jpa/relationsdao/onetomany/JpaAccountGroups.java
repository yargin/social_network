package com.getjavajob.training.yarginy.socialnetwork.dao.jpa.relationsdao.onetomany;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.Group;
import com.getjavajob.training.yarginy.socialnetwork.dao.jpa.JpaGroupDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.Collection;

import static java.util.Objects.isNull;

@Repository
public class JpaAccountGroups implements JpaOneToManyDao<Group> {
    private EntityManagerFactory entityManagerFactory;
    private JpaGroupDao groupDao;

    @Autowired
    public void setEntityManagerFactory(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Autowired
    public void setGroupDao(JpaGroupDao groupDao) {
        this.groupDao = groupDao;
    }

    @Override
    public Collection<Group> selectMany(long accountId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Account account = new Account(accountId);
        TypedQuery<Group> selectMany = entityManager.createQuery("select g from Group g where g.owner = :owner",
                Group.class);
        selectMany.setParameter("owner", account);
        return selectMany.getResultList();
    }

    @Override
    public boolean relationExists(long accountId, long groupId) {
        Group group = groupDao.select(groupId);
        return !isNull(group.getOwner()) && group.getOwner().getId() == accountId;
    }
}
