package com.getjavajob.training.yarginy.socialnetwork.dao.jpa.relationsdao.selfmanytomany;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Model;
import com.getjavajob.training.yarginy.socialnetwork.common.models.manytomany.JpaSelfManyToMany;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;

import static java.util.Objects.isNull;

@Repository
public abstract class GenericSelfManyToMany<E extends Model> implements JpaSelfManyToManyDao<E> {
    protected transient EntityManagerFactory entityManagerFactory;

    @Autowired
    public void setEntityManagerFactory(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    protected abstract JpaSelfManyToMany<E> genericGetReference(EntityManager entityManager, long greaterId, long lowerId);

    protected abstract JpaSelfManyToMany<E> genericFind(EntityManager entityManager, long greaterId, long lowerId);

    protected abstract JpaSelfManyToMany<E> genericCreateObject(EntityManager entityManager, long greaterId, long lowerId);

    @Override
    public boolean create(long greaterId, long lowerId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        JpaSelfManyToMany<E> manyToMany = genericCreateObject(entityManager, greaterId, lowerId);
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
    public boolean relationExists(long greaterId, long lowerId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        JpaSelfManyToMany<E> manyToMany = genericFind(entityManager, greaterId, lowerId);
        return !isNull(manyToMany);
    }

    @Override
    public boolean delete(long greaterId, long lowerId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        try {
            JpaSelfManyToMany<E> friendship = genericGetReference(entityManager, greaterId, lowerId);
            entityManager.remove(friendship);
            transaction.commit();
            return true;
        } catch (PersistenceException | IllegalArgumentException e) {
            transaction.rollback();
            return false;
        }
    }
}
