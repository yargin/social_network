package com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.testetst.newnew.queryplacers;

public class UpdateQueryPlacer extends QueryPlacer {
    private final StringBuilder updateQueryParameters = new StringBuilder();
    private final String[] altKeys;
    private final String keyPrefix;

    public UpdateQueryPlacer(String table, String[] altKeys, String keyPrefix) {
        super(table);
        this.altKeys = altKeys;
        this.keyPrefix = keyPrefix;
    }

    @Override
    public String getQuery() {
        return "UPDATE " + table + " SET " + getStringWithoutLastComma(updateQueryParameters) + " WHERE " +
                addAltKeys();
    }

    private String addAltKeys() {
        StringBuilder stringBuilder = new StringBuilder();
        for (String key : altKeys) {
            stringBuilder.append(key).append(" = :").append(keyPrefix + key).append(", ");
        }
        return getStringWithoutLastComma(stringBuilder);
    }

    @Override
    public void addColumn(String column) {
        updateQueryParameters.append(column).append(" = :").append(column).append(", ");
    }

}
