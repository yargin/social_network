package com.getjavajob.training.yarginy.socialnetwork.dao.relations.manytomany.identifying;

import com.getjavajob.training.yarginy.socialnetwork.dao.entities.Entity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

public abstract class ManyToManyDml<F extends Entity, S extends Entity> {
    public abstract Collection<S> selectByFirst(Connection connection, int firstId) throws SQLException;

    public abstract Collection<F> selectBySecond(Connection connection, int secondId) throws SQLException;

    public abstract PreparedStatement getSelectStatement(Connection connection, int firstId, int secondId) throws
            SQLException;

    public abstract void updateRow(ResultSet resultSet, int firstId, int secondId) throws SQLException;
}
