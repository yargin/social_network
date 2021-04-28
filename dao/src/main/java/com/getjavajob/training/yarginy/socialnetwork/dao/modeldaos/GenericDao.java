package com.getjavajob.training.yarginy.socialnetwork.dao.modeldaos;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Model;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import java.util.Collection;
import java.util.function.Supplier;

import static java.util.Objects.isNull;

public abstract class GenericDao<E extends Model> implements Dao<E> {
    @PersistenceContext
    protected transient EntityManager entityManager;

    protected abstract Supplier<E> getSelectByPk(EntityManager entityManager, long id);

    @Override
    @Transactional
    public E select(long id) {
        Supplier<E> selectByPkFunction = getSelectByPk(entityManager, id);
        E model = selectByPkFunction.get();
        return isNull(model) ? getNullModel() : model;
    }

    protected abstract Supplier<TypedQuery<E>> getSelectByAltKey(EntityManager entityManager, E model);

    @Override
    @Transactional
    public E select(E modelToSelect) {
        Supplier<TypedQuery<E>> selectByAltKeyFunction = getSelectByAltKey(entityManager, modelToSelect);
        TypedQuery<E> query = selectByAltKeyFunction.get();
        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return getNullModel();
        } catch (NonUniqueResultException e) {
            throw new IllegalArgumentException(e);
        }
    }

    protected abstract boolean checkEntity(E model);

    protected abstract void prepareModelRelations(EntityManager entityManager, E model);

    @Override
    @Transactional
    public void create(E model) {
        try {
            if (model.getId() != 0) {
                throw new IllegalArgumentException();
            }
        } catch (UnsupportedOperationException ignore) {
        }
        if (checkEntity(model)) {
            throw new IllegalArgumentException();
        }
        try {
            prepareModelRelations(entityManager, model);
            entityManager.persist(model);
            entityManager.flush();
        } catch (DataIntegrityViolationException | PersistenceException e) {
            throw new IllegalArgumentException(e);
        }
    }

    protected abstract Supplier<E> getModelReference(EntityManager entityManager, E model);

    @Override
    @Transactional
    public void update(E model) {
        Supplier<E> storedSupplier = getModelReference(entityManager, model);
        E stored = storedSupplier.get();
        if (stored.getId() == 0 || checkEntity(model)) {
            throw new IllegalArgumentException();
        }
        try {
            entityManager.merge(model);
        } catch (OptimisticLockException e) {
            throw new IllegalStateException(e);
        } catch (RuntimeException e) {
            throw new IllegalArgumentException(e);
        }
        model.setVersion(model.getVersion() + 1);
    }

    @Override
    @Transactional
    public void delete(E model) {
        E entityToDelete = getModelReference(entityManager, model).get();
        try {
            entityManager.remove(entityToDelete);
        } catch (RuntimeException e) {
            throw new IllegalArgumentException(e);
        }
    }

    protected abstract Supplier<TypedQuery<E>> getSelectAll(EntityManager entityManager);

    @Override
    @Transactional
    public Collection<E> selectAll() {
        Supplier<TypedQuery<E>> selectAll = getSelectAll(entityManager);
        TypedQuery<E> query = selectAll.get();
        return query.getResultList();
    }
}
