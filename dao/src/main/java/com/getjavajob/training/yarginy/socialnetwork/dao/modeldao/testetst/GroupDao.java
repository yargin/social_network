package com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.testetst;

import com.getjavajob.training.yarginy.socialnetwork.common.models.group.Group;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

public class GroupDao extends AbstractDao<Group> {


    @Override
    public Group getNullEntity() {
        return null;
    }

    @Override
    protected String getSelectByIdQuery() {
        return null;
    }

    @Override
    protected String getSelectByAltKeysQuery() {
        return null;
    }

    @Override
    protected Object[] getAltKeys(Group entity) {
        return new Object[0];
    }

    @Override
    protected MapSqlParameterSource createEntityFieldsMap(Group entity) {
        return null;
    }

    @Override
    protected ValuePlacer getValuePlacer(Group entity, Group storedEntity) {
        return null;
    }

    @Override
    protected Object[] getPrimaryKeys(Group entity) {
        return new Object[0];
    }

    @Override
    protected String getDeleteByPrimaryKeyQuery() {
        return null;
    }

    @Override
    protected String getSelectAllQuery() {
        return null;
    }

    @Override
    public ResultSetExtractor<Group> getSuffixedViewExtractor(String suffix) {
        return null;
    }

    @Override
    public ResultSetExtractor<Group> getSuffixedExtractor(String suffix) {
        return null;
    }
}
