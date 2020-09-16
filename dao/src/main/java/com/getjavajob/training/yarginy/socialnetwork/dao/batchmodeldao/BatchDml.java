package com.getjavajob.training.yarginy.socialnetwork.dao.batchmodeldao;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Entity;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.AbstractDml;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;

public interface BatchDml<E extends Entity> extends AbstractDml<E> {
//    public abstract PreparedStatement getSelect(Connection connection, Collection<E> entities);

    PreparedStatement batchSelect(Connection connection, Collection<E> entities) throws SQLException;
}
