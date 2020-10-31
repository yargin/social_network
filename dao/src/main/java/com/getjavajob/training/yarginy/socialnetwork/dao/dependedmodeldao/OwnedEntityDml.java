package com.getjavajob.training.yarginy.socialnetwork.dao.dependedmodeldao;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Entity;
import com.getjavajob.training.yarginy.socialnetwork.common.models.OwnedEntity;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface OwnedEntityDml<O extends Entity, E extends OwnedEntity<O>> {
    PreparedStatement getSelect(Connection connection, O owner) throws SQLException;

    E getNullEntity();

    E   selectFromRow(ResultSet resultSet, O owner) throws SQLException, IOException;

    void updateRow(ResultSet resultSet, E ownedEntity, E storedOwnedEntity) throws SQLException;
}
