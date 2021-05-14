package com.getjavajob.training.yarginy.socialnetwork.common.models.searchable;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class Searchable {
    private long id;
    private String name;
    private SearchableType type;

    public Searchable() {
    }

    public Searchable(long id, String name, SearchableType type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }

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
