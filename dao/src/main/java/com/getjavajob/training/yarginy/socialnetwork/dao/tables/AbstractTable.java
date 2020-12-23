package com.getjavajob.training.yarginy.socialnetwork.dao.tables;

import java.util.function.Supplier;

public abstract class AbstractTable {
    private final String alias;

    public AbstractTable(String alias) {
        this.alias = alias;
    }

    protected abstract String[] getFieldsList();

    public String getFields() {
        return buildString(this::getFieldsList);
    }

    protected abstract String[] getViewFieldsList();

    public String getViewFields() {
        return buildString(this::getViewFieldsList);
    }

    private String buildString(Supplier<String[]> fieldsGetter) {
        String[] fields = fieldsGetter.get();
        StringBuilder stringBuilder = new StringBuilder();
        for(String field: fields) {
            stringBuilder.append(alias).append('.').append(field).append(" as ").append(field).append(alias).
                    append(", ");
        }
        return stringBuilder.substring(0, stringBuilder.length() - 2);
    }

    protected abstract String getTableName();

    public String getTable() {
        return getTableName() + ' ' + alias;
    }
}
