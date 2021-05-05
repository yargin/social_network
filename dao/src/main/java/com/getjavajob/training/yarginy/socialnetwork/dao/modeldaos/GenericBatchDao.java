package com.getjavajob.training.yarginy.socialnetwork.dao.modeldaos;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Model;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.PersistenceException;
import java.util.Collection;
import java.util.HashSet;

public abstract class GenericBatchDao<E extends Model> extends GenericDao<E> implements BatchDao<E> {
    @Override
    @Transactional
    public void create(Collection<E> entities) {
        for (E entity : entities) {
            try {
                entityManager.persist(entity);
            } catch (PersistenceException e) {
                throw new IllegalArgumentException(e);
            }
        }
    }

    @Override
    @Transactional
    public void delete(Collection<E> entities) {
        for (E entity : entities) {
            try {
                E entityToDelete = getModelReference(entityManager, entity).get();
                entityManager.remove(entityToDelete);
            } catch (PersistenceException e) {
                throw new IllegalArgumentException(e);
            }
        }
    }

    @Override
    @Transactional
    public void update(Collection<E> newEntities, Collection<E> storedEntities) {
        Collection<E> entitiesToDelete = new HashSet<>(storedEntities);
        entitiesToDelete.removeAll(newEntities);
        delete(entitiesToDelete);
        Collection<E> entitiesToCreate = new HashSet<>(newEntities);
        entitiesToCreate.removeAll(storedEntities);
        create(entitiesToCreate);
    }
}
