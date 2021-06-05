package com.getjavajob.training.yarginy.socialnetwork.dao.jpa.modeldaos;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Model;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.PersistenceException;
import javax.persistence.Query;
import java.util.Collection;
import java.util.HashSet;

public abstract class GenericBatchDao<E extends Model> extends GenericDao<E> implements BatchDao<E> {
    @Override
    @Transactional
    public void create(Collection<E> entities) {
        try {
            entities.forEach(e -> entityManager.persist(e));
            entityManager.flush();
        } catch (DataIntegrityViolationException | PersistenceException e) {
            e.printStackTrace();
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    @Transactional
    public void delete(Collection<E> entities) {
        try {
            entities.forEach(e -> getDeleteByAltKeyQuery(e).executeUpdate());
        } catch (PersistenceException e) {
            e.printStackTrace();
            throw new IllegalArgumentException(e);
        }
    }

    protected abstract Query getDeleteByAltKeyQuery(E model);

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
