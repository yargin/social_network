package com.getjavajob.training.yarginy.socialnetwork.dao.relationdaos.manytomany;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Model;
import com.getjavajob.training.yarginy.socialnetwork.common.models.manytomany.JpaManyToMany;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import java.util.Collection;

import static java.util.Objects.isNull;

public abstract class GenericManyToMany<F extends Model, S extends Model> implements ManyToManyDao<F, S> {
    @PersistenceContext
    private transient EntityManager entityManager;

    protected abstract Collection<S> genericSelectByFirst(EntityManager entityManager, long firstId);

    @Override
    @Transactional
    public Collection<S> selectByFirst(long firstId) {
        checkId(firstId);
        return genericSelectByFirst(entityManager, firstId);
    }

    protected abstract Collection<F> genericSelectBySecond(EntityManager entityManager, long secondId);

    @Override
    @Transactional
    public Collection<F> selectBySecond(long secondId) {
        checkId(secondId);
        return genericSelectBySecond(entityManager, secondId);
    }

    protected abstract JpaManyToMany<F, S> genericFind(EntityManager entityManager, long firstId, long secondId);

    @Override
    @Transactional
    public boolean relationExists(long firstId, long secondId) {
        JpaManyToMany<F, S> manyToMany = genericFind(entityManager, firstId, secondId);
        return !isNull(manyToMany);
    }

    protected abstract JpaManyToMany<F, S> genericCreateObject(EntityManager entityManager, long firstId, long secondId);

    @Override
    @Transactional
    public void create(long firstId, long secondId) {
        checkId(firstId, secondId);
        JpaManyToMany<F, S> manyToMany = genericCreateObject(entityManager, firstId, secondId);
        try {
            entityManager.persist(manyToMany);
            entityManager.flush();
        } catch (PersistenceException e) {
            throw new IllegalArgumentException(e);
        }
    }

    protected abstract JpaManyToMany<F, S> genericGetReference(EntityManager entityManager, long firstId, long secondId);

    @Override
    @Transactional
    public void delete(long firstId, long secondId) {
        checkId(firstId, secondId);
        try {
            JpaManyToMany<F, S> manyToMany = genericGetReference(entityManager, firstId, secondId);
            entityManager.remove(manyToMany);
        } catch (PersistenceException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private void checkId(long... ids) {
        for (long id : ids) {
            if (id < 1) { throw new IllegalArgumentException(); }
        }
    }
}
