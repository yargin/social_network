package com.getjavajob.training.yarginy.socialnetwork.dao.relationsdao.onetomany;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Entity;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;

public class OneToManyDaoImpl<M extends Entity> implements OneToManyDao<M> {
    private final DataSource dataSource;
    private final OneToManyDml<M> oneToManyDml;

    public OneToManyDaoImpl(DataSource dataSource, OneToManyDml<M> oneToManyDml) {
        this.dataSource = dataSource;
        this.oneToManyDml = oneToManyDml;
    }

    @Override
    public Collection<M> selectMany(long oneId) {
        try (Connection connection = dataSource.getConnection()) {
            return oneToManyDml.retrieveManyByOne(connection, oneId);
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public boolean relationExists(long manyId, long oneId) {
        try (Connection connection = dataSource.getConnection()) {
            return oneToManyDml.retrieveByBoth(connection, oneId, manyId);
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }
}
