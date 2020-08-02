package com.getjavajob.training.yarginy.socialnetwork.dao.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

public interface EntitySql<E extends Entity> {
    /**
     * retrieves {@link PreparedStatement} which execution result will be {@link E} found by email. Used for
     * insertion, modification & deletion
     * <br>BE CAREFUL - use only with Try-with-resources or Finally block
     *
     * @param connection {@link Connection} specified connection to make {@link PreparedStatement}
     * @param id {@link E}'s id
     * @return {@link PreparedStatement} that selects {@link E} found by id
     */
    PreparedStatement getSelectStatement(Connection connection, int id) throws SQLException;

    /**
     * retrieves {@link PreparedStatement} which execution result will be {@link E} found by identifier. Used for
     * insertion, modification & deletion
     * <br>BE CAREFUL - use only with Try-with-resources or Finally block
     *
     * @param connection {@link Connection} specified connection to make {@link PreparedStatement}
     * @param identifier {@link E}'s {@link String} identifier
     * @return {@link PreparedStatement} that selects {@link E} found by identifier
     */
    PreparedStatement getSelectStatement(Connection connection, String identifier) throws SQLException;

    /**
     * retrieves {@link E} from {@link ResultSet}'s current cursor position
     *
     * @param resultSet specified {@link ResultSet}
     * @return extracted {@link E}
     */
    E selectFromRow(ResultSet resultSet) throws SQLException;

    /**
     * retrieves {@link Collection}<{@link E}> from specified {@link ResultSet}
     *
     * @param resultSet specified {@link ResultSet}
     * @return extracted {@link Collection}<{@link E}>
     */
    Collection<E> selectEntities(ResultSet resultSet) throws SQLException;

    /**
     * updates row that given {@link ResultSet}'s cursor currently points to
     *
     * @param resultSet {@link ResultSet} with positioned cursor
     * @param entity {@link E}that need to be updated
     */
    void updateRow(ResultSet resultSet, E entity) throws SQLException;
}
