package com.getjavajob.training.yarginy.socialnetwork.dao.jpa.modeldaos;

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

import static java.util.Objects.isNull;

public abstract class GenericDao<E extends Model> implements Dao<E> {
    @PersistenceContext
    protected transient EntityManager entityManager;

    protected abstract E selectByPk(long id);

    @Override
    @Transactional
    public E select(long id) {
        E model = selectByPk(id);
        return isNull(model) ? getNullModel() : model;
    }

    protected abstract TypedQuery<E> getSelectByAltKey(E model);

    @Override
    @Transactional
    public E select(E modelToSelect) {
        TypedQuery<E> query = getSelectByAltKey(modelToSelect);
        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return getNullModel();
        } catch (NonUniqueResultException e) {
            throw new IllegalArgumentException(e);
        }
    }

    protected abstract boolean checkEntityFail(E model);

    protected abstract void prepareModelRelations(E model);

    @Override
    @Transactional
    public void create(E model) {
        if (model.getId() != 0 || checkEntityFail(model)) {
            throw new IllegalArgumentException();
        }
        try {
            prepareModelRelations(model);
            entityManager.persist(model);
            entityManager.flush();
        } catch (DataIntegrityViolationException | PersistenceException e) {
            throw new IllegalArgumentException(e);
        }
    }

    protected abstract E getModelReference(E model);

    @Override
    @Transactional
    public void update(E model) {
        try {
            E stored = getModelReference(model);
            if (stored.getId() == 0 || checkEntityFail(model)) {
                throw new IllegalArgumentException();
            }
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
        try {
            E entityToDelete = getModelReference(model);
            entityManager.remove(entityToDelete);
        } catch (RuntimeException e) {
            throw new IllegalArgumentException(e);
        }
    }

    protected abstract TypedQuery<E> getSelectAll();

    @Override
    @Transactional
    public Collection<E> selectAll() {
        return getSelectAll().getResultList();
    }
}
