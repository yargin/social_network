package com.getjavajob.training.yarginy.socialnetwork.dao.models;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Model;
import org.hibernate.PropertyValueException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import java.util.Collection;
import java.util.HashSet;

@Repository
public class BatchAbstractTxDelegateDao<E extends Model> extends AbstractTxDelegateDao<E> implements BatchDao<E> {
    private final BatchGenericDaoTransactional<E> batchDaoTransactional;

    public BatchAbstractTxDelegateDao(BatchGenericDaoTransactional<E> daoTransactional) {
        super(daoTransactional);
        this.batchDaoTransactional = daoTransactional;
    }

    @Override
    public boolean create(Collection<E> entities) {
        try {
            return batchDaoTransactional.createTransactional(entities);
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    @Override
    public boolean delete(Collection<E> entities) {
        try {
            return batchDaoTransactional.deleteTransactional(entities);
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    @Override
    public boolean update(Collection<E> newEntities, Collection<E> storedEntities) {
        try {
            batchDaoTransactional.updateTransactional(newEntities, storedEntities);
        } catch (IllegalArgumentException e) {
            return false;
        }
        return true;
    }
}
