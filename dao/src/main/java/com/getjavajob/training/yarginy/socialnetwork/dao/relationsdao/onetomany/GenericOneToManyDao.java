package com.getjavajob.training.yarginy.socialnetwork.dao.relationsdao.onetomany;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import java.util.Collection;

@Repository
public abstract class GenericOneToManyDao<M extends Model> implements OneToManyDao<M> {
    @PersistenceContext
    private transient EntityManager entityManager;

    protected abstract Collection<M> genericSelectMany(EntityManager entityManager, long accountId);

    @Override
    @Transactional
    public Collection<M> selectMany(long oneId) {
        return genericSelectMany(entityManager, oneId);
    }
}
