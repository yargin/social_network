package com.getjavajob.training.yarginy.socialnetwork.dao.relations.manytomany.variousrelated;

import com.getjavajob.training.yarginy.socialnetwork.common.entities.Entity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

public abstract class ManyToManyDml<F extends Entity, S extends Entity> {
    public abstract Collection<S> selectByFirst(Connection connection, long firstId) throws SQLException;

    public abstract Collection<F> selectBySecond(Connection connection, long secondId) throws SQLException;

    public abstract PreparedStatement getSelectStatement(Connection connection, long firstId, long secondId) throws
            SQLException;

    public abstract void updateRow(ResultSet resultSet, long firstId, long secondId) throws SQLException;
}
