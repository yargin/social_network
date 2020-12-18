package com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.testetst.queryplacers;

public abstract class QueryParametersPlacer {
    public abstract String getQueryParameters();

    public abstract void addColumn(String column);

    public String getQueryString(StringBuilder stringBuilder) {
        int length = stringBuilder.length();
        if (length > 0) {
            return stringBuilder.substring(0, length - 2);
        } else {
            throw new IllegalArgumentException();
        }
    }
}
