package com.getjavajob.training.yarginy.socialnetwork.dao.relationsdao.selfmanytomany;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Model;

import java.util.Collection;

public class SelfManyToManyDelegateDaoTx<E extends Model> implements SelfManyToManyDao<E> {
    private final GenericSelfManyToManyDao<E> genericSelfManyToManyDao;

    public SelfManyToManyDelegateDaoTx(GenericSelfManyToManyDao<E> genericSelfManyToManyDao) {
        this.genericSelfManyToManyDao = genericSelfManyToManyDao;
    }

    @Override
    public Collection<E> select(long id) {
        return genericSelfManyToManyDao.selectTransactional(id);
    }

    @Override
    public boolean create(long firstId, long secondId) {
        try {
            return genericSelfManyToManyDao.createTransactional(firstId, secondId);
        } catch (IllegalStateException e) {
            return false;
        }
    }

    @Override
    public boolean relationExists(long firstId, long secondId) {
        try {
            return genericSelfManyToManyDao.relationExistsTransactional(firstId, secondId);
        } catch (IllegalStateException e) {
            return false;
        }
    }

    @Override
    public boolean delete(long firstId, long secondId) {try {
        return genericSelfManyToManyDao.deleteTransactional(firstId, secondId);
    } catch (IllegalStateException e) {
        return false;
    }
    }
}
