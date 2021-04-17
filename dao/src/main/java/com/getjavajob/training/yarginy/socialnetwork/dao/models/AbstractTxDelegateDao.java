package com.getjavajob.training.yarginy.socialnetwork.dao.models;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Model;
import org.hibernate.PropertyValueException;
import org.springframework.dao.DataIntegrityViolationException;

import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceException;

import java.util.Collection;

import static java.util.Objects.isNull;

public class AbstractTxDelegateDao<E extends Model> implements Dao<E> {
    private final GenericDaoTransactional<E> daoTransactional;

    public AbstractTxDelegateDao(GenericDaoTransactional<E> daoTransactional) {
        this.daoTransactional = daoTransactional;
    }

    @Override
    public E select(long id) {
        return daoTransactional.select(id);
    }

    @Override
    public E select(E modelToSelect) {
        return daoTransactional.select(modelToSelect);
    }

    @Override
    public Collection<E> selectAll() {
        return daoTransactional.selectAll();
    }

    @Override
    public E getNullModel() {
        return daoTransactional.getNullModel();
    }

    @Override
    public boolean create(E model) {
        try {
            if (model.getId() != 0) {
                return false;
            }
        } catch (UnsupportedOperationException ignore) {}
        try {
            return daoTransactional.createTransactional(model);
        } catch (DataIntegrityViolationException e) {
            return false;
        } catch (PersistenceException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public boolean update(E model) {
        try {
            return daoTransactional.updateTransactional(model);
        } catch (OptimisticLockException e) {
            throw new IllegalStateException(e);
        } catch (RuntimeException e) {
            return false;
        }
    }

    @Override
    public boolean delete(E model) {
        try {
            return daoTransactional.deleteTransactional(model);
        } catch (RuntimeException e) {
            return false;
        }
    }
}
