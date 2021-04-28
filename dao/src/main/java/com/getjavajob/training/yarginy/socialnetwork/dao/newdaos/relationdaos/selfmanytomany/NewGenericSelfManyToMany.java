package com.getjavajob.training.yarginy.socialnetwork.dao.newdaos.relationdaos.selfmanytomany;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Model;
import com.getjavajob.training.yarginy.socialnetwork.common.models.manytomany.JpaSelfManyToMany;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import java.util.Collection;

import static java.lang.Math.max;
import static java.lang.Math.min;
import static java.util.Objects.isNull;

public abstract class NewGenericSelfManyToMany<E extends Model> implements NewSelfManyToManyDao<E> {
    @PersistenceContext
    private transient EntityManager entityManager;

    protected abstract Collection<E> genericSelect(EntityManager entityManager, long id);

    @Override
    @Transactional
    public Collection<E> select(long id) {
        checkId(id);
        return genericSelect(entityManager, id);
    }

    protected abstract JpaSelfManyToMany<E> genericCreateObject(EntityManager entityManager, long greaterId, long lowerId);

    @Override
    @Transactional
    public void create(long firstId, long secondId) {
        if (firstId == secondId) {
            throw new IllegalArgumentException();
        }
        long greaterId = max(firstId, secondId);
        long lowerId = min(firstId, secondId);
        checkId(greaterId, lowerId);
        JpaSelfManyToMany<E> manyToMany = genericCreateObject(entityManager, greaterId, lowerId);
        try {
            entityManager.persist(manyToMany);
            entityManager.flush();
        } catch (PersistenceException e) {
            throw new IllegalArgumentException(e);
        }
    }

    protected abstract JpaSelfManyToMany<E> genericFind(EntityManager entityManager, long greaterId, long lowerId);

    @Override
    @Transactional
    public boolean relationExists(long firstId, long secondId) {
        if (firstId == secondId) {
            throw new IllegalArgumentException();
        }
        long greaterId = max(firstId, secondId);
        long lowerId = min(firstId, secondId);
        checkId(greaterId, lowerId);
        JpaSelfManyToMany<E> manyToMany = genericFind(entityManager, greaterId, lowerId);
        return !isNull(manyToMany);
    }

    protected abstract JpaSelfManyToMany<E> genericGetReference(EntityManager entityManager, long greaterId, long lowerId);

    @Override
    @Transactional
    public void delete(long firstId, long secondId) {
        if (firstId == secondId) {
            throw new IllegalArgumentException();
        }
        long greaterId = max(firstId, secondId);
        long lowerId = min(firstId, secondId);
        checkId(greaterId, lowerId);
        try {
            JpaSelfManyToMany<E> friendship = genericGetReference(entityManager, greaterId, lowerId);
            entityManager.remove(friendship);
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
