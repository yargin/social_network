package com.getjavajob.training.yarginy.socialnetwork.dao.relations.onetomany;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Entity;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;

public abstract class OneToManyDml<O extends Entity, M extends Entity> {
    public abstract Collection<M> selectByOne(Connection connection, long oneId) throws SQLException;

    public abstract O selectByOneOfMany(Connection connection, long oneOfManyId) throws SQLException;
}
