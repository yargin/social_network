package com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.testetst.queryplacers;

public class InsertQueryPlacer extends QueryParametersPlacer {
    private final StringBuilder insertParameters = new StringBuilder();
    private final StringBuilder insertFields = new StringBuilder();

    @Override
    public String getQueryParameters() {
        return " ( " + getQueryString(insertFields) + " ) VALUES (" + getQueryString(insertParameters) + ')';
    }

    @Override
    public void addColumn(String column) {
        insertParameters.append(':').append(column).append(", ");
        insertFields.append(column).append(", ");
    }
}
