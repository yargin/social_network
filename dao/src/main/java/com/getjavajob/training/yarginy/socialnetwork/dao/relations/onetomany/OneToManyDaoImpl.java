package com.getjavajob.training.yarginy.socialnetwork.dao.relations.onetomany;

import com.getjavajob.training.yarginy.socialnetwork.common.entities.Entity;
import com.getjavajob.training.yarginy.socialnetwork.dao.entities.Dao;
import com.getjavajob.training.yarginy.socialnetwork.dao.factories.connector.DbConnector;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;

public class OneToManyDaoImpl<O extends Entity, M extends Entity> implements OneToManyDao<O, M> {
    private final DbConnector dbConnector;
    private final OneToManyDml<M> oneToManyDml;
    private final Dao<O> oneDao;

    public OneToManyDaoImpl(DbConnector dbConnector, OneToManyDml<M> oneToManyDml, Dao<O> oneDao) {
        this.dbConnector = dbConnector;
        this.oneToManyDml = oneToManyDml;
        this.oneDao = oneDao;
    }

    @Override
    public Collection<M> selectMany(O entity) {
        oneDao.checkEntity(entity);
        try (Connection connection = dbConnector.getConnection()) {
            return oneToManyDml.selectByOne(connection, entity.getId());
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }
}
