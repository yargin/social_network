package com.getjavajob.training.yarginy.socialnetwork.dao.relationsdao.onetomany;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.Collection;

@Repository
public abstract class GenericOneToManyDao<M extends Model> implements OneToManyDao<M> {
    private transient EntityManagerFactory entityManagerFactory;

    @Autowired
    public void setEntityManagerFactory(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    protected abstract Collection<M> genericSelectMany(EntityManager entityManager, long accountId);

    @Override
    public Collection<M> selectMany(long oneId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Collection<M> result = genericSelectMany(entityManager, oneId);
        entityManager.close();
        return result;
    }
}
