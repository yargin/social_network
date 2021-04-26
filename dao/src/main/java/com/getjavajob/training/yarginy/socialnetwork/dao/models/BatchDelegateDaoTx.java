package com.getjavajob.training.yarginy.socialnetwork.dao.models;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Model;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public class BatchDelegateDaoTx<E extends Model> extends DelegateDaoTx<E> implements BatchDao<E> {
    private final BatchGenericDaoTransactional<E> batchDaoTransactional;

    public BatchDelegateDaoTx(BatchGenericDaoTransactional<E> daoTransactional) {
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
