package com.getjavajob.training.yarginy.socialnetwork.dao.jpa.modeldaos.implementations;

import com.getjavajob.training.yarginy.socialnetwork.common.models.searchable.Searchable;
import com.getjavajob.training.yarginy.socialnetwork.common.models.searchable.SearchableType;
import com.getjavajob.training.yarginy.socialnetwork.common.models.searchable.SearchablesDto;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Repository
public class GlobalSearchDao implements Serializable {
    @PersistenceContext
    private transient EntityManager entityManager;

    public SearchablesDto searchAccountsGroups(String searchString, int pageNumber, int limit) {
        String search = "%" + searchString + "%";
        Query query = entityManager.createNativeQuery(searchAccountsAndGroupsQuery(pageNumber, limit)).
                setParameter(1, search).setParameter(2, search).setParameter(3, search);
        List<Object[]> result = query.getResultList();
        List<Searchable> searchables = new ArrayList<>();
        for (Object[] raw : result) {
            Searchable searchable = new Searchable();
            searchable.setId(((BigInteger) raw[0]).longValue());
            String type = (String) raw[1];
            if ("user".equals(type)) {
                searchable.setType(SearchableType.ACCOUNT);
            } else {
                searchable.setType(SearchableType.GROUP);
            }
            searchable.setName((String) raw[2]);
            searchables.add(searchable);
        }
        SearchablesDto searchablesDto = new SearchablesDto(searchables);
        Query rowsNumberQuery = entityManager.createNativeQuery(accountsAndGroupsRowCountQuery()).setParameter(1, search).
                setParameter(2, search).setParameter(3, search);
        int rowsNumber = ((BigInteger) rowsNumberQuery.getSingleResult()).intValue();
        searchablesDto.setPages(rowsNumber, limit);
        return searchablesDto;
    }

    private String searchAccountsAndGroupsQuery(int pageNumber, int limit) {
        return "SELECT id, type, name " +
                " FROM " +
                " (SELECT id, 'user' type, CONCAT(name, ' ', surname) name FROM accounts " +
                " WHERE UPPER(name) LIKE UPPER(?) or UPPER(surname) LIKE UPPER(?)" +
                " UNION " +
                " SELECT id, 'group' type, name name FROM `groups` " +
                " WHERE UPPER(name) LIKE UPPER(?) ) s " +
                " LIMIT " + limit + " OFFSET " + (pageNumber - 1) * limit;
    }

    private String accountsAndGroupsRowCountQuery() {
        return "SELECT count(*) rows_number FROM " +
                " (SELECT id, 'user' type, CONCAT(name, ' ', surname) name FROM accounts " +
                " WHERE UPPER(name) LIKE UPPER(?) or UPPER(surname) LIKE UPPER(?)" +
                " UNION " +
                " SELECT id, 'group' type, name name FROM `groups` " +
                " WHERE UPPER(name) LIKE UPPER(?) ) s";
    }
}
