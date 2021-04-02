package com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.jpa;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import java.util.Collection;
import java.util.function.Function;

import static java.util.Objects.isNull;

@Repository
public abstract class GenericDao<E extends Model> implements JpaDao<E> {
    private transient EntityManagerFactory entityManagerFactory;

    @Autowired
    public void setEntityManagerFactory(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    //todo maybe change to producer for better readability
    protected abstract Function<EntityManager, TypedQuery<E>> getSelectByAltKeyFunction(E model);

    protected abstract Function<EntityManager, E> getSelectByPkFunction(long id);

    protected abstract Function<EntityManager, TypedQuery<E>> getSelectAllFunction();

    @Override
    public E select(long id) {
        Function<EntityManager, E> selectByPkFunction = getSelectByPkFunction(id);
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        E model = selectByPkFunction.apply(entityManager);
        return isNull(model) ? getNullModel() : model;
    }

    @Override
    public E select(E model) {
        Function<EntityManager, TypedQuery<E>> selectByAltKeyFunction = getSelectByAltKeyFunction(model);
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        TypedQuery<E> query = selectByAltKeyFunction.apply(entityManager);
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
        Function<EntityManager, TypedQuery<E>> selectAllFunction = getSelectAllFunction();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        TypedQuery<E> query = selectAllFunction.apply(entityManager);
        return query.getResultList();
    }

    @Override
    public boolean create(E model) {
        return createGeneric(model, getSelectByAltKeyFunction(model));
    }

    private boolean createGeneric(E model, Function<EntityManager, TypedQuery<E>> function) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        //check for persistence context or mistake
        if (model.getId() != 0) {
            return false;
        }
        //check if exists by alt key
        TypedQuery<E> query = function.apply(entityManager);
        if (!query.getResultList().isEmpty()) {
            return false;
        }
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        try {
            entityManager.persist(model);
            transaction.commit();
            return true;
        } catch (PersistenceException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public boolean update(E model) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        try {
            entityManager.merge(model);
        } catch (OptimisticLockException e) {
            transaction.rollback();
            return false;
        }
        transaction.commit();
        return true;
    }

    /*
    when remove passed as parameter - detached entity may be removed - need to select from context
     */
    @Override
    public boolean delete(E model) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        E modelToDelete = getSelectByPkFunction(model.getId()).apply(entityManager);
        if (getNullModel().equals(modelToDelete)) {
            transaction.rollback();
            return false;
        }
        try {
            entityManager.remove(modelToDelete);
        } catch (OptimisticLockException | IllegalArgumentException e) {
            transaction.rollback();
            return false;
        }
        transaction.commit();
        return true;
    }
}
