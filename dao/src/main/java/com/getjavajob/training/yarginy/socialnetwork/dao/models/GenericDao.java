package com.getjavajob.training.yarginy.socialnetwork.dao.models;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Model;
import org.hibernate.PropertyValueException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;

import static java.util.Objects.isNull;

@Repository
public abstract class GenericDao<E extends Model> implements Dao<E> {
    protected transient EntityManagerFactory entityManagerFactory;

    @Autowired
    public void setEntityManagerFactory(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    protected abstract Supplier<TypedQuery<E>> getSelectByAltKey(EntityManager entityManager, E model);

    protected abstract Supplier<E> getSelectByPk(EntityManager entityManager, long id);

    protected abstract Supplier<TypedQuery<E>> getSelectAll(EntityManager entityManager);

    protected abstract Supplier<E> getModelReference(EntityManager entityManager, E model);

    @Override
    public E select(long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Supplier<E> selectByPkFunction = getSelectByPk(entityManager, id);
        E model = selectByPkFunction.get();
        entityManager.close();
        return isNull(model) ? getNullModel() : model;
    }

    @Override
    public E select(E model) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Supplier<TypedQuery<E>> selectByAltKeyFunction = getSelectByAltKey(entityManager, model);
        TypedQuery<E> query = selectByAltKeyFunction.get();
        try {
            E result = query.getSingleResult();
            entityManager.close();
            return result;
        } catch (NoResultException e) {
            entityManager.close();
            return getNullModel();
        } catch (NonUniqueResultException e) {
            entityManager.close();
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public Collection<E> selectAll() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Supplier<TypedQuery<E>> selectAll = getSelectAll(entityManager);
        TypedQuery<E> query = selectAll.get();
        List<E> resultList = query.getResultList();
        entityManager.close();
        return resultList;
    }

    @Override
    public boolean create(E model) {
        if (!checkEntity(model)) {
            throw new IllegalArgumentException();
        }
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        try {
            prepareModelRelations(entityManager, model);
            entityManager.persist(model);
            //todo watch
//            entityManager.refresh(model);
            transaction.commit();
            entityManager.close();
            return true;
        } catch (PersistenceException | IllegalArgumentException e) {
            if (e.getCause().getClass() == PropertyValueException.class) {
                entityManager.close();
                throw new IllegalArgumentException(e);
            }
            transaction.rollback();
            entityManager.close();
            return false;
        }
    }

    protected abstract boolean checkEntity(E model);

    protected abstract void prepareModelRelations(EntityManager entityManager, E model);

    @Override
    @Transactional
    public boolean update(E model) {
        if (!checkEntity(model)) {
            throw new IllegalArgumentException();
        }
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        try {
            entityManager.merge(model);
            transaction.commit();
            model.setVersion(getModelReference(entityManager, model).get().getVersion());
            entityManager.close();
            return true;
        } catch (OptimisticLockException e) {
            entityManager.close();
            throw new IllegalStateException(e);
        } catch (PersistenceException | IllegalArgumentException e) {
            transaction.rollback();
            entityManager.close();
            return false;
        }
    }

    @Override
    public boolean delete(E model) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        try {
            E entityToDelete = getModelReference(entityManager, model).get();
            entityManager.remove(entityToDelete);
            transaction.commit();
            entityManager.close();
            return true;
        } catch (PersistenceException | IllegalArgumentException e) {
            transaction.rollback();
            entityManager.close();
            return false;
        }
    }

    public EntityManager getEntityManager() {
        return entityManagerFactory.createEntityManager();
    }
}
