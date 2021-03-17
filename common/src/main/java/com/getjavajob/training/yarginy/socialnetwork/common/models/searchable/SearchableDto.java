package com.getjavajob.training.yarginy.socialnetwork.common.models.searchable;

import java.util.Collection;

public class SearchableDto {
    private final Collection<Searchable> searchAbles;
    private int[] pages;

    public SearchableDto(Collection<Searchable> searchAbles) {
        this.searchAbles = searchAbles;
    }

    public void setPages(int rows, int limit) {
        int allPagesNumber = rows / limit;
        if (rows % limit != 0) {
            allPagesNumber++;
        }
        pages = new int[allPagesNumber];
        for (int i = 0; i < allPagesNumber; i++) {
            pages[i] = i + 1;
        }
    }

    public Collection<Searchable> getSearchAbles() {
        return searchAbles;
    }

    public int[] getPages() {
        return pages;
    }
}
