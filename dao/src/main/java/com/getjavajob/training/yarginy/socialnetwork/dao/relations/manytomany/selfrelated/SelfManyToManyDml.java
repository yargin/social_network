package com.getjavajob.training.yarginy.socialnetwork.dao.relations.manytomany.selfrelated;

import com.getjavajob.training.yarginy.socialnetwork.common.entities.Entity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

public abstract class SelfManyToManyDml<E extends Entity> {
    public abstract Collection<E> select(Connection connection, int id) throws SQLException;

    public abstract PreparedStatement getSelectStatement(Connection connection, int firstId, int secondId) throws
            SQLException;

    public abstract void updateRow(ResultSet resultSet, int firstId, int secondId) throws SQLException;
}
