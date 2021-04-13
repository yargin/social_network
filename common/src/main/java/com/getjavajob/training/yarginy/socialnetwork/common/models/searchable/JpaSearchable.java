package com.getjavajob.training.yarginy.socialnetwork.common.models.searchable;


public class JpaSearchable {
    private long id;
    private String name;
    private SearchableType type;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SearchableType getType() {
        return type;
    }

    public void setType(SearchableType type) {
        this.type = type;
    }
}
