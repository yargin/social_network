package com.getjavajob.training.yarginy.socialnetwork.dao.models;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Model;
import org.hibernate.PropertyValueException;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import java.util.Collection;
import java.util.HashSet;

public abstract class BatchGenericDao<E extends Model> extends GenericDaoTransactional<E> implements BatchDao<E> {
    @Override
    public boolean create(Collection<E> entities) {
        try {
            return createTransactional(entities);
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    protected abstract boolean createTransactional(Collection<E> entities);

    @Override
    public boolean delete(Collection<E> entities) {
        try {
            return deleteTransactional(entities);
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    protected abstract boolean deleteTransactional(Collection<E> entities);

    @Override
    public boolean update(Collection<E> newEntities, Collection<E> storedEntities) {
        try {
            updateTransactional(newEntities, storedEntities);
        } catch (IllegalArgumentException e) {
            return false;
        }
        return true;
    }

    protected abstract void updateTransactional(Collection<E> newEntities, Collection<E> storedEntities);
}
