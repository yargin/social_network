package com.getjavajob.training.yarginy.socialnetwork.dao.relationsdao.manytomany;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Model;

import java.util.Collection;

public class ManyToManyDelegateDaoTx<F extends Model, S extends Model> implements ManyToManyDao<F, S> {
    private final GenericManyToManyTransactional<F, S> genericManyToManyDao;

    public ManyToManyDelegateDaoTx(GenericManyToManyTransactional<F, S> genericManyToManyDao) {
        this.genericManyToManyDao = genericManyToManyDao;
    }

    @Override
    public Collection<S> selectByFirst(long firstId) {
        return genericManyToManyDao.selectByFirstTransactional(firstId);
    }

    @Override
    public Collection<F> selectBySecond(long secondId) {
        return genericManyToManyDao.selectBySecondTransactional(secondId);
    }

    @Override
    public boolean relationExists(long firstId, long secondId) {
        return genericManyToManyDao.relationExistsTransactional(firstId, secondId);
    }

    @Override
    public boolean create(long firstId, long secondId) {
        try {
            return genericManyToManyDao.createTransactional(firstId, secondId);
        } catch (IllegalStateException e) {
            return false;
        }
    }

    @Override
    public boolean delete(long firstId, long secondId) {
        try {
            return genericManyToManyDao.deleteTransactional(firstId, secondId);
        } catch (IllegalStateException e) {
            return false;
        }
    }
}
