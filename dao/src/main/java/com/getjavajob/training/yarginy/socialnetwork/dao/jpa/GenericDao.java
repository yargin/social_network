package com.getjavajob.training.yarginy.socialnetwork.dao.jpa;

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
import java.util.function.Supplier;

import static java.util.Objects.isNull;

@Repository
public abstract class GenericDao<E extends Model> implements JpaDao<E> {
    private transient EntityManagerFactory entityManagerFactory;

    @Autowired
    public void setEntityManagerFactory(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    protected abstract Supplier<TypedQuery<E>> getSelectByAltKey(EntityManager entityManager, E model);

    protected abstract Supplier<E> getSelectByPk(EntityManager entityManager, long id);

    protected abstract Supplier<TypedQuery<E>> getSelectAll(EntityManager entityManager);

    @Override
    public E select(long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Supplier<E> selectByPkFunction = getSelectByPk(entityManager, id);
        E model = selectByPkFunction.get();
        return isNull(model) ? getNullModel() : model;
    }

    @Override
    public E select(E model) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
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
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Supplier<TypedQuery<E>> selectAll = getSelectAll(entityManager);
        TypedQuery<E> query = selectAll.get();
        return query.getResultList();
    }

    @Override
    public boolean create(E model) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        if (model.getId() != 0) {
            return false;
        }
        //check if exists by alt key
        TypedQuery<E> query = getSelectByAltKey(entityManager, model).get();
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
        E modelToDelete = getSelectByPk(entityManager, model.getId()).get();
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
