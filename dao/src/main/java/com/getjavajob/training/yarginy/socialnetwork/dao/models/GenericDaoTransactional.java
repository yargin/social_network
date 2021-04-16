package com.getjavajob.training.yarginy.socialnetwork.dao.models;

import com.getjavajob.training.yarginy.socialnetwork.common.exceptions.IncorrectDataException;
import com.getjavajob.training.yarginy.socialnetwork.common.models.Model;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import javax.transaction.Transaction;
import java.util.Collection;
import java.util.function.Supplier;

import static com.getjavajob.training.yarginy.socialnetwork.common.exceptions.IncorrectData.WRONG_ID;
import static java.util.Objects.isNull;

public abstract class GenericDaoTransactional<E extends Model> extends GenericDao<E> {
    @PersistenceContext
    private transient EntityManager entityManager;

    protected abstract Supplier<TypedQuery<E>> getSelectByAltKey(EntityManager entityManager, E model);

    protected abstract Supplier<E> getSelectByPk(EntityManager entityManager, long id);

    protected abstract Supplier<TypedQuery<E>> getSelectAll(EntityManager entityManager);

    protected abstract Supplier<E> getModelReference(EntityManager entityManager, E model);

    @Override
    public E select(long id) {
        Supplier<E> selectByPkFunction = getSelectByPk(entityManager, id);
        E model = selectByPkFunction.get();
        return isNull(model) ? getNullModel() : model;
    }

    @Override
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

    @Override
    public Collection<E> selectAll() {
        Supplier<TypedQuery<E>> selectAll = getSelectAll(entityManager);
        TypedQuery<E> query = selectAll.get();
        return query.getResultList();
    }

    @Override
    @Transactional
    public boolean createTransactional(E model) {
        if (!checkEntity(model)) {
            throw new IllegalArgumentException();
        }
        prepareModelRelations(entityManager, model);
        entityManager.persist(model);
        //todo watch
//            entityManager.refresh(model);
//            transaction.commit();
        return true;
    }

    @Override
    @Transactional
    public boolean updateTransactional(E model) {
        if (!checkEntity(model)) {
            throw new IncorrectDataException(WRONG_ID);
        }
        entityManager.merge(model);
        model.setVersion(getModelReference(entityManager, model).get().getVersion());
        return true;
    }

    @Override
    @Transactional
    public boolean deleteTransactional(E model) {
        E entityToDelete = getModelReference(entityManager, model).get();
        entityManager.remove(entityToDelete);
        return true;
    }

    protected abstract boolean checkEntity(E model);

    protected abstract void prepareModelRelations(EntityManager entityManager, E model);
}
