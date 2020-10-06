package com.getjavajob.training.yarginy.socialnetwork.dao.batchmodeldao;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Entity;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.Dml;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;

public interface BatchDml<E extends Entity> extends Dml<E> {
//    public abstract PreparedStatement getSelect(Connection connection, Collection<E> entities);

    PreparedStatement batchSelectUpdate(Connection connection, Collection<E> entities) throws SQLException;
}
