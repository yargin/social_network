package com.getjavajob.training.yarginy.socialnetwork.common.models.searchable;

import java.util.Collection;

public class SearchableDto {
    private final Collection<Searchable> searchAbles;
    private int pages;

    public SearchableDto(Collection<Searchable> searchAbles) {
        this.searchAbles = searchAbles;
    }

    public void setPages(int rows, int limit) {
        pages = rows / limit;
        if (rows % limit != 0) {
            pages++;
        }
    }

    public Collection<Searchable> getSearchAbles() {
        return searchAbles;
    }

    public int getPages() {
        return pages;
    }
}
