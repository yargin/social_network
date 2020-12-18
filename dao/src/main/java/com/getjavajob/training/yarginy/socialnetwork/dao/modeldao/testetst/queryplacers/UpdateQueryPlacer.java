package com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.testetst.queryplacers;

public class UpdateQueryPlacer extends QueryParametersPlacer {
    private final StringBuilder updateQueryParameters = new StringBuilder();

    @Override
    public String getQueryParameters() {
        return " SET " + getQueryString(updateQueryParameters);
    }

    @Override
    public void addColumn(String column) {
        updateQueryParameters.append(column).append(" = :").append(column).append(", ");
    }

}
