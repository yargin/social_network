package com.getjavajob.training.yarginy.socialnetwork.dao.relationsdao.manytomany;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Model;
import com.getjavajob.training.yarginy.socialnetwork.common.models.manytomany.JpaManyToMany;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;

import java.util.Collection;

import static java.util.Objects.isNull;

@Repository
public abstract class GenericManyToManyDao<F extends Model, S extends Model> implements ManyToManyDao<F, S> {
    private transient EntityManagerFactory entityManagerFactory;

    @Autowired
    public void setEntityManagerFactory(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    protected abstract JpaManyToMany<F, S> genericGetReference(EntityManager entityManager, long firstId, long secondId);

    protected abstract JpaManyToMany<F, S> genericFind(EntityManager entityManager, long firstId, long secondId);

    protected abstract JpaManyToMany<F, S> genericCreateObject(EntityManager entityManager, long firstId, long secondId);

    protected abstract Collection<S> genericSelectByFirst(EntityManager entityManager, long firstId);

    protected abstract Collection<F> genericSelectBySecond(EntityManager entityManager, long secondId);

    @Override
    public Collection<S> selectByFirst(long firstId) {
        checkId(firstId);
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Collection<S> secondEntities = genericSelectByFirst(entityManager, firstId);
        entityManager.close();
        return secondEntities;
    }

    @Override
    public Collection<F> selectBySecond(long secondId) {
        checkId(secondId);
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Collection<F> firstEntities = genericSelectBySecond(entityManager, secondId);
        entityManager.close();
        return firstEntities;
    }

    @Override
    public boolean create(long firstId, long secondId) {
        checkId(firstId, secondId);
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        JpaManyToMany<F, S> manyToMany = genericCreateObject(entityManager, firstId, secondId);
        try {
            entityManager.persist(manyToMany);
            transaction.commit();
            entityManager.close();
            return true;
        } catch (EntityNotFoundException e) {
            throw new IllegalArgumentException(e);
        } catch (PersistenceException | IllegalArgumentException e) {
            transaction.rollback();
            entityManager.close();
            return false;
        }
    }

    @Override
    public boolean relationExists(long firstId, long secondId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        JpaManyToMany<F, S> manyToMany = genericFind(entityManager, firstId, secondId);
        entityManager.close();
        return !isNull(manyToMany);
    }

    @Override
    public boolean delete(long firstId, long secondId) {
        checkId(firstId, secondId);
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        try {
            JpaManyToMany<F, S> manyToMany = genericGetReference(entityManager, firstId, secondId);
            entityManager.remove(manyToMany);
            transaction.commit();
            entityManager.close();
            return true;
        } catch (PersistenceException | IllegalArgumentException e) {
            transaction.rollback();
            entityManager.close();
            return false;
        }
    }

    private void checkId(long... ids) {
        for (long id : ids) {
            if (id < 1) { throw new IllegalArgumentException(); }
        }
    }
}
