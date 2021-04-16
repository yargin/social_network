package com.getjavajob.training.yarginy.socialnetwork.dao.relationsdao.onetomany;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.Group;
import com.getjavajob.training.yarginy.socialnetwork.dao.models.Dao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.Collection;

import static java.util.Objects.isNull;

@Repository("accountGroupsDao")
public class AccountGroupsDao extends GenericOneToManyDao<Group> {
    private Dao<Group> groupDao;

    @Autowired
    public void setGroupDao(@Qualifier("groupDao") Dao<Group> groupDao) {
        this.groupDao = groupDao;
    }

    @Override
    public Collection<Group> genericSelectMany(EntityManager entityManager, long accountId) {
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
