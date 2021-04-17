package com.getjavajob.training.yarginy.socialnetwork.dao.models;

import com.getjavajob.training.yarginy.socialnetwork.common.exceptions.IncorrectDataException;
import com.getjavajob.training.yarginy.socialnetwork.common.models.Model;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.io.Serializable;
import java.util.Collection;
import java.util.function.Supplier;

import static com.getjavajob.training.yarginy.socialnetwork.common.exceptions.IncorrectData.WRONG_ID;
import static java.util.Objects.isNull;

public abstract class GenericDaoTransactional<E extends Model> implements Serializable {
    @PersistenceContext
    private transient EntityManager entityManager;

    protected abstract Supplier<TypedQuery<E>> getSelectByAltKey(EntityManager entityManager, E model);

    protected abstract Supplier<E> getSelectByPk(EntityManager entityManager, long id);

    protected abstract Supplier<TypedQuery<E>> getSelectAll(EntityManager entityManager);

    protected abstract Supplier<E> getModelReference(EntityManager entityManager, E model);

    public E select(long id) {
        Supplier<E> selectByPkFunction = getSelectByPk(entityManager, id);
        E model = selectByPkFunction.get();
        return isNull(model) ? getNullModel() : model;
    }

    public E select(E model) {
        Supplier<TypedQuery<E>> selectByAltKeyFunction = getSelectByAltKey(entityManager, model);
        TypedQuery<E> query = selectByAltKeyFunction.get();
        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return getNullModel();
        } catch (NonUniqueResultException e) {
            throw new IllegalArgumentException(e);
        }
    }

    protected abstract E getNullModel();

    public Collection<E> selectAll() {
        Supplier<TypedQuery<E>> selectAll = getSelectAll(entityManager);
        TypedQuery<E> query = selectAll.get();
        return query.getResultList();
    }

    @Transactional
    public boolean createTransactional(E model) {
        if (checkEntity(model)) {
            throw new IllegalArgumentException();
        }
        prepareModelRelations(entityManager, model);
        entityManager.persist(model);
        return true;
    }

    @Transactional
    public boolean updateTransactional(E model) {
        Supplier<E> storedSupplier = getModelReference(entityManager, model);
        E stored = storedSupplier.get();
        if (stored.getId() == 0 || checkEntity(model)) {
            throw new IncorrectDataException(WRONG_ID);
        }
        entityManager.merge(model);
            model.setVersion(model.getVersion() + 1);
            return true;
    }

    @Transactional
    public boolean deleteTransactional(E model) {
        E entityToDelete = getModelReference(entityManager, model).get();
        entityManager.remove(entityToDelete);
        return true;
    }

    protected abstract boolean checkEntity(E model);

    protected abstract void prepareModelRelations(EntityManager entityManager, E model);
}
