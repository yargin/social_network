package com.getjavajob.training.yarginy.socialnetwork.dao.models;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Model;
import org.hibernate.PropertyValueException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import java.util.Collection;
import java.util.HashSet;

@Repository
public abstract class BatchGenericDaoTransactional<E extends Model> extends GenericDaoTransactional<E> {
    @PersistenceContext
    protected transient EntityManager entityManager;

    @Transactional
    public boolean createTransactional(Collection<E> entities) {
        for (E entity : entities) {
            try {
                entityManager.persist(entity);
            } catch (PersistenceException e) {
                throw new IllegalArgumentException(e);
            }
        }
        return true;
    }

    @Transactional
    public boolean deleteTransactional(Collection<E> entities) {
        for (E entity : entities) {
            try {
                E entityToDelete = getModelReference(entityManager, entity).get();
                entityManager.remove(entityToDelete);
            } catch (PersistenceException e) {
                throw new IllegalArgumentException(e);
            }
        }
        return true;
    }

    @Transactional
    public void updateTransactional(Collection<E> newEntities, Collection<E> storedEntities) {
        Collection<E> entitiesToDelete = new HashSet<>(storedEntities);
        entitiesToDelete.removeAll(newEntities);
        deleteTransactional(entitiesToDelete);
        Collection<E> entitiesToCreate = new HashSet<>(newEntities);
        entitiesToCreate.removeAll(storedEntities);
        createTransactional(entitiesToCreate);
    }
}
