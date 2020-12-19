package com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.testetst.newnew.queryplacers;

public class InsertQueryPlacer extends QueryPlacer {
    private final StringBuilder insertParameters = new StringBuilder();
    private final StringBuilder insertFields = new StringBuilder();

    public InsertQueryPlacer(String table) {
        super(table);
    }

    @Override
    public String getQuery() {
        return "INSERT INTO " + table + " ( " + getStringWithoutLastComma(insertFields) + " ) VALUES ( " +
                getStringWithoutLastComma(insertParameters) + " )";
    }

    @Override
    public void addColumn(String column) {
        insertParameters.append(':').append(column).append(", ");
        insertFields.append(column).append(", ");
    }
}
