package com.getjavajob.training.yarginy.socialnetwork.dao.relationsdao.manytomany;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Model;
import com.getjavajob.training.yarginy.socialnetwork.common.models.manytomany.JpaManyToMany;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import java.io.Serializable;
import java.util.Collection;

import static java.util.Objects.isNull;

@Repository
public abstract class GenericManyToManyTransactional<F extends Model, S extends Model> implements Serializable {
    @PersistenceContext
    private transient EntityManager entityManager;

    protected abstract JpaManyToMany<F, S> genericGetReference(EntityManager entityManager, long firstId, long secondId);

    protected abstract JpaManyToMany<F, S> genericFind(EntityManager entityManager, long firstId, long secondId);

    protected abstract JpaManyToMany<F, S> genericCreateObject(EntityManager entityManager, long firstId, long secondId);

    protected abstract Collection<S> genericSelectByFirst(EntityManager entityManager, long firstId);

    protected abstract Collection<F> genericSelectBySecond(EntityManager entityManager, long secondId);

    @Transactional
    public Collection<S> selectByFirstTransactional(long firstId) {
        checkId(firstId);
        return genericSelectByFirst(entityManager, firstId);
    }

    @Transactional
    public Collection<F> selectBySecondTransactional(long secondId) {
        checkId(secondId);
        return genericSelectBySecond(entityManager, secondId);
    }

    @Transactional
    public boolean createTransactional(long firstId, long secondId) {
        checkId(firstId, secondId);
        JpaManyToMany<F, S> manyToMany = genericCreateObject(entityManager, firstId, secondId);
        try {
            entityManager.persist(manyToMany);
            entityManager.flush();
            return true;
        } catch (EntityNotFoundException e) {
            throw new IllegalArgumentException(e);
        } catch (PersistenceException | IllegalArgumentException e) {
            throw new IllegalStateException();
        }
    }

    @Transactional
    public boolean relationExistsTransactional(long firstId, long secondId) {
        JpaManyToMany<F, S> manyToMany = genericFind(entityManager, firstId, secondId);
        return !isNull(manyToMany);
    }

    @Transactional
    public boolean deleteTransactional(long firstId, long secondId) {
        checkId(firstId, secondId);
        try {
            JpaManyToMany<F, S> manyToMany = genericGetReference(entityManager, firstId, secondId);
            entityManager.remove(manyToMany);
            return true;
        } catch (PersistenceException | IllegalArgumentException e) {
            throw new IllegalStateException();
        }
    }

    private void checkId(long... ids) {
        for (long id : ids) {
            if (id < 1) { throw new IllegalArgumentException(); }
        }
    }
}
