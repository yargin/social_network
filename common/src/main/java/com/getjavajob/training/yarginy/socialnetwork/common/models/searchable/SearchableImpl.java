package com.getjavajob.training.yarginy.socialnetwork.common.models.searchable;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class SearchableImpl implements Searchable {
    private long id;
    private String name;
    private SearchableType type;

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public SearchableType getType() {
        return type;
    }

    @Override
    public void setType(SearchableType type) {
        this.type = type;
    }
}
