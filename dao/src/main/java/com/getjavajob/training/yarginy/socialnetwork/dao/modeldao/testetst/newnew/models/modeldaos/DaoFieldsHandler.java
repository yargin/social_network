package com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.testetst.newnew.models.modeldaos;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Entity;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.testetst.valuesplacer.queryandparamplacer.QueryAndParamPlacer;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface DaoFieldsHandler<E extends Entity> {
    String getSelectByIdQuery();

    String getSelectByAltKeyQuery();

    String getSelectAllQuery();

    String getDeleteQuery();

    QueryAndParamPlacer getInsertQueryAndParameters(E entity);

    QueryAndParamPlacer getUpdateQueryAndParameters(E entity, E storedEntity);

    //todo i want to see here table.field
    E mapRow(ResultSet resultSet, int i) throws SQLException;

    E mapViewRow(ResultSet resultSet, int i) throws SQLException;

    MapSqlParameterSource getAltKeyParameter(E entity);

    MapSqlParameterSource getPKeyParameter(E entity);

    E getNullEntity();

    String getTableName();

    interface InnerInitializer<E> {
        void initUpdateParams(E entity, E storedEntity);

        void initInsertParams(E entity);

        void setPKey(E storedEntity);
    }
}
