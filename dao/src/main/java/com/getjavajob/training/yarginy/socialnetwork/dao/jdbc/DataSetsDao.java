package com.getjavajob.training.yarginy.socialnetwork.dao.jdbc;

import com.getjavajob.training.yarginy.socialnetwork.common.models.searchable.Searchable;
import com.getjavajob.training.yarginy.socialnetwork.common.models.searchable.SearchableType;
import com.getjavajob.training.yarginy.socialnetwork.common.models.searchable.SearchablesDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Collection;

import static java.util.Arrays.fill;

@Component("dataSetsDao")
public class DataSetsDao implements Serializable {
    private static final int ACCOUNTS_GROUPS_PARAMETERS_NUMBER = 3;
    private transient JdbcTemplate template;

    @Autowired
    public void setTemplate(JdbcTemplate template) {
        this.template = template;
    }

    public SearchablesDto searchAccountsGroups(String searchString, int pageNumber, int limit) {
        String query = searchAccountsAndGroupsQuery(pageNumber, limit);
        String[] searchParameters = new String[ACCOUNTS_GROUPS_PARAMETERS_NUMBER];
        fill(searchParameters, '%' + searchString + '%');
        Collection<Searchable> entities = template.query(query, (resultSet, i) -> {
            Searchable searchable = new Searchable();
            searchable.setId(resultSet.getLong("id"));
            searchable.setName(resultSet.getString("name"));
            if ("user".equals(resultSet.getString("type"))) {
                searchable.setType(SearchableType.ACCOUNT);
            } else {
                searchable.setType(SearchableType.GROUP);
            }
            return searchable;
        }, (Object[]) searchParameters);
        SearchablesDto searchablesDto = new SearchablesDto(entities);
        Integer rowsNumber = template.queryForObject(accountsAndGroupsRowCountQuery(), (resultSet, i) ->
                resultSet.getInt("rows_number"), (Object[]) searchParameters);
        searchablesDto.setPages(rowsNumber == null ? 0 : rowsNumber, limit);
        return searchablesDto;
    }

    private String searchAccountsAndGroupsQuery(int pageNumber, int limit) {
        return "SELECT id, type, name " +
                " FROM " +
                " (SELECT id, 'user' type, CONCAT(name, ' ', surname) name FROM accounts " +
                " WHERE UPPER(name) LIKE UPPER(?) or UPPER(surname) LIKE UPPER(?)" +
                " UNION " +
                " SELECT id, 'group' type, name name FROM `Groups` " +
                " WHERE UPPER(name) LIKE UPPER(?) ) s " +
                " LIMIT " + limit + " OFFSET " + (pageNumber - 1) * limit + ';';
    }

    private String accountsAndGroupsRowCountQuery() {
        return "SELECT count(*) rows_number FROM " +
                " (SELECT id, 'user' type, CONCAT(name, ' ', surname) name FROM accounts " +
                " WHERE UPPER(name) LIKE UPPER(?) or UPPER(surname) LIKE UPPER(?)" +
                " UNION " +
                " SELECT id, 'group' type, name name FROM `Groups` " +
                " WHERE UPPER(name) LIKE UPPER(?) ) s ;";
    }
}
