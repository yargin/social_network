package com.getjavajob.training.yarginy.socialnetwork.dao.models;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Model;
import org.hibernate.PropertyValueException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceException;

import static java.util.Objects.isNull;

@Repository
public abstract class GenericDao<E extends Model> implements Dao<E> {
    @Override
    public boolean create(E model) {
        try {
            return createTransactional(model);
        } catch (PersistenceException e) {
            if (!isNull(e.getCause()) && e.getCause().getClass() == PropertyValueException.class) {
                throw new IllegalArgumentException(e);
            }
            return false;
        }
    }

    protected abstract boolean createTransactional(E model);

    @Override
    public boolean update(E model) {
        try {
            return updateTransactional(model);
        } catch (OptimisticLockException e) {
            throw new IllegalStateException(e);
        } catch (RuntimeException e) {
            return false;
        }
    }

    protected abstract boolean updateTransactional(E model);

    @Override
    @Transactional
    public boolean delete(E model) {
        try {
            return deleteTransactional(model);
        } catch (RuntimeException e) {
            return false;
        }
    }

    protected abstract boolean deleteTransactional(E model);
}
