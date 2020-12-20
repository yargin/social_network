package com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.testetst.valuesplacer.queryplacers;

public abstract class QueryPlacer {
    protected String table;

    public QueryPlacer(String table) {
        this.table = table;
    }

    public abstract String getQuery();

    public abstract void addColumn(String column);

    public String getStringWithoutLastComma(StringBuilder stringBuilder) {
        int length = stringBuilder.length();
        if (length > 0) {
            return stringBuilder.substring(0, length - 2);
        } else {
            throw new IllegalArgumentException();
        }
    }
}
