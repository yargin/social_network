package com.getjavajob.training.yarginy.socialnetwork.common.models.searchable;

public interface Searchable {
    long getId();

    void setId(long id);

    String getName();

    void setName(String name);

    SearchableType getType();

    void setType(SearchableType type);
}
