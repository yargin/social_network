package com.getjavajob.training.yarginy.socialnetwork.dao.jpa.relationsdao.manytomany;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Model;
import com.getjavajob.training.yarginy.socialnetwork.common.models.manytomany.JpaManyToMany;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;

import static java.util.Objects.isNull;

@Repository
public abstract class GenericManyToManyDao<F extends Model, S extends Model> implements JpaManyToManyDao<F, S> {
    protected transient EntityManagerFactory entityManagerFactory;

    @Autowired
    public void setEntityManagerFactory(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    protected abstract JpaManyToMany<F, S> genericGetReference(EntityManager entityManager, long firstId, long secondId);

    protected abstract JpaManyToMany<F, S> genericFind(EntityManager entityManager, long firstId, long secondId);

    protected abstract JpaManyToMany<F, S> genericCreateObject(EntityManager entityManager, long firstId, long secondId);

    @Override
    public boolean create(long firstId, long secondId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        JpaManyToMany<F, S> manyToMany = genericCreateObject(entityManager, firstId, secondId);
        try {
            entityManager.persist(manyToMany);
            transaction.commit();
            return true;
        } catch (PersistenceException | IllegalArgumentException e) {
            transaction.rollback();
            return false;
        }
    }

    @Override
    public boolean relationExists(long firstId, long secondId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        JpaManyToMany<F, S> manyToMany = genericFind(entityManager, firstId, secondId);
        return !isNull(manyToMany);
    }

    @Override
    public boolean delete(long firstId, long secondId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        try {
            JpaManyToMany<F, S> manyToMany = genericGetReference(entityManager, firstId, secondId);
            entityManager.remove(manyToMany);
            transaction.commit();
            return true;
        } catch (PersistenceException | IllegalArgumentException e) {
            transaction.rollback();
            return false;
        }
    }
}
