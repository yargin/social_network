package com.getjavajob.training.yarginy.socialnetwork.dao.jpa.relationdaos.selfmanytomany;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Model;
import com.getjavajob.training.yarginy.socialnetwork.common.models.manytomany.SelfManyToMany;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import java.util.Collection;

import static java.lang.Math.max;
import static java.lang.Math.min;
import static java.util.Objects.isNull;

public abstract class GenericSelfManyToMany<E extends Model> implements SelfManyToManyDao<E> {
    @PersistenceContext
    private transient EntityManager entityManager;

    protected abstract Collection<E> genericSelect(EntityManager entityManager, long id);

    @Override
    @Transactional
    public Collection<E> select(long id) {
        checkId(id);
        return genericSelect(entityManager, id);
    }

    protected abstract SelfManyToMany<E> genericCreateObject(EntityManager entityManager, long greaterId, long lowerId);

    @Override
    @Transactional
    public void create(long firstId, long secondId) {
        if (firstId == secondId) {
            throw new IllegalArgumentException();
        }
        long greaterId = max(firstId, secondId);
        long lowerId = min(firstId, secondId);
        checkId(greaterId, lowerId);
        SelfManyToMany<E> manyToMany = genericCreateObject(entityManager, greaterId, lowerId);
        try {
            entityManager.persist(manyToMany);
            entityManager.flush();
        } catch (DataIntegrityViolationException | PersistenceException e) {
            throw new IllegalArgumentException(e);
        }
    }

    protected abstract SelfManyToMany<E> genericFind(EntityManager entityManager, long greaterId, long lowerId);

    @Override
    @Transactional
    public boolean relationExists(long firstId, long secondId) {
        if (firstId == secondId) {
            throw new IllegalArgumentException();
        }
        long greaterId = max(firstId, secondId);
        long lowerId = min(firstId, secondId);
        checkId(greaterId, lowerId);
        SelfManyToMany<E> manyToMany = genericFind(entityManager, greaterId, lowerId);
        return !isNull(manyToMany);
    }

    protected abstract SelfManyToMany<E> genericGetReference(EntityManager entityManager, long greaterId, long lowerId);

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
            SelfManyToMany<E> friendship = genericGetReference(entityManager, greaterId, lowerId);
            entityManager.remove(friendship);
        } catch (DataIntegrityViolationException | PersistenceException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private void checkId(long... ids) {
        for (long id : ids) {
            if (id < 1) { throw new IllegalArgumentException(); }
        }
    }
}
