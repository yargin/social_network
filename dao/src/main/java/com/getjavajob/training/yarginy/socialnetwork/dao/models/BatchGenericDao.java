package com.getjavajob.training.yarginy.socialnetwork.dao.models;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Model;
import org.hibernate.PropertyValueException;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
import java.util.Collection;
import java.util.HashSet;

public abstract class BatchGenericDao<E extends Model> extends GenericDao<E> implements BatchDao<E> {
    @Override
    public boolean create(Collection<E> entities) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        if (!createEntities(entities, entityManager, transaction)) return false;
        transaction.commit();
        entityManager.close();
        return true;
    }

    private boolean createEntities(Collection<E> entities, EntityManager entityManager, EntityTransaction transaction) {
        for (E entity : entities) {
            try {
                entityManager.persist(entity);
            } catch (PersistenceException | IllegalArgumentException e) {
                if (e.getCause().getClass() == PropertyValueException.class) {
                    throw new IllegalArgumentException(e);
                }
                transaction.rollback();
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean delete(Collection<E> entities) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        if (!deleteEntities(entities, entityManager, transaction)) return false;
        transaction.commit();
        entityManager.close();
        return true;
    }

    private boolean deleteEntities(Collection<E> entities, EntityManager entityManager, EntityTransaction transaction) {
        for (E entity : entities) {
            try {
                E entityToDelete = getModelReference(entityManager, entity).get();
                entityManager.remove(entityToDelete);
            } catch (PersistenceException | IllegalArgumentException e) {
                transaction.rollback();
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean update(Collection<E> newEntities, Collection<E> storedEntities) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        Collection<E> entitiesToDelete = new HashSet<>(storedEntities);
        entitiesToDelete.removeAll(newEntities);
        deleteEntities(entitiesToDelete, entityManager, transaction);
        Collection<E> entitiesToCreate = new HashSet<>(newEntities);
        entitiesToCreate.removeAll(storedEntities);
        createEntities(entitiesToCreate, entityManager, transaction);
        transaction.commit();
        entityManager.close();
        return true;
    }
}
