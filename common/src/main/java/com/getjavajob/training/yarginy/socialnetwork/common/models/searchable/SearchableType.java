package com.getjavajob.training.yarginy.socialnetwork.common.models.searchable;

public enum SearchableType {
    ACCOUNT("account"), GROUP("group");

    private final String typeName;

    SearchableType(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeName() {
        return typeName;
    }
}
