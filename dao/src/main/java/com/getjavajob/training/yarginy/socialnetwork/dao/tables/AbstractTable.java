package com.getjavajob.training.yarginy.socialnetwork.dao.tables;

import java.util.function.Supplier;

public abstract class AbstractTable {
    private final String alias;

    public AbstractTable(String alias) {
        this.alias = alias == null ? "" : alias;
    }

    protected abstract String[] getFieldsList();

    public String getFields() {
        return buildString(this::getFieldsList, this::appendField);
    }

    protected abstract String[] getViewFieldsList();

    public String getViewFields() {
        return buildString(this::getViewFieldsList, this::appendField);
    }

    private boolean appendField(StringBuilder stringBuilder, boolean firstIteration, String field) {
        String optionalAlias = alias.isEmpty() ? alias : alias + '.';
        if (!firstIteration) {
            stringBuilder.append(", ");
        }
        stringBuilder.append(optionalAlias).append(field).append(" as ").append(field).append(alias);
        return false;
    }

    private String buildString(Supplier<String[]> fieldsGetter, Appender appender) {
        String[] fields = fieldsGetter.get();
        StringBuilder stringBuilder = new StringBuilder();
        boolean firstIteration = true;
        for (String field : fields) {
            firstIteration = appender.append(stringBuilder, firstIteration, field);
        }
        return stringBuilder.toString();
    }

    private boolean appendKey(StringBuilder stringBuilder, boolean firstIteration, String key) {
        if (!firstIteration) {
            stringBuilder.append(" AND ");
        }
        stringBuilder.append(key).append(" = ? ");
        return false;
    }

    public abstract String getTable();

    public String getPKParameters() {
        return buildString(this::getPrimaryKeys, this::appendKey);
    }

    public abstract String[] getPrimaryKeys();

    public String getAltParameters() {
        return buildString(this::getAltKeys, this::appendKey);
    }

    public abstract String[] getAltKeys();

    public String getSuffix() {
        return alias;
    }

    private interface Appender {
        boolean append(StringBuilder builder, boolean firstIteration, String value);
    }
}
