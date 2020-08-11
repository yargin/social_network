package com.getjavajob.training.yarginy.socialnetwork.dao.relations.onetomany;

import com.getjavajob.training.yarginy.socialnetwork.common.entities.Entity;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;

public abstract class OneToManyDml<M extends Entity> {
    public abstract Collection<M> selectByOne(Connection connection, int oneId) throws SQLException;
}
