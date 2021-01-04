package com.getjavajob.training.yarginy.socialnetwork.dao.otherdao;

import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.group.Group;
import com.getjavajob.training.yarginy.socialnetwork.common.models.searchable.Searchable;
import com.getjavajob.training.yarginy.socialnetwork.common.models.searchable.SearchableDto;
import com.getjavajob.training.yarginy.socialnetwork.common.models.searchable.SearchableImpl;
import com.getjavajob.training.yarginy.socialnetwork.common.models.searchable.SearchableType;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.AccountDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.GroupDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static java.util.Objects.isNull;

@Component("dataSetsDao")
public class DataSetsDao implements Serializable {
    private final JdbcTemplate template;
    private final AccountDao accountDao;
    private final GroupDao groupDao;

    @Autowired
    public DataSetsDao(JdbcTemplate template, AccountDao accountDao, GroupDao groupDao) {
        this.template = template;
        this.accountDao = accountDao;
        this.groupDao = groupDao;
    }

    public Map<Account, Boolean> getGroupMembersModerators(long groupId) {
        Map<Account, Boolean> membersModerators = new HashMap<>();
        template.query(getGroupMembersAdModeratorsQUery(), (resultSet, i) -> {
                    Account account = accountDao.getSuffixedViewRowMapper("a").mapRow(resultSet, i);
                    long isModerator = resultSet.getLong("moderator");
                    membersModerators.put(account, isModerator != 0);
                    return account;
                }
                , groupId);
        return membersModerators;
    }

    private String getGroupMembersAdModeratorsQUery() {
        return "SELECT " + accountDao.getViewFields("a") + ", gmods.account_id moderator " +
                "FROM Groups_members gmems " +
                "LEFT JOIN Accounts a ON a.id = gmems.account_id " +
                "LEFT JOIN groups_moderators gmods " +
                "ON gmems.account_id = gmods.account_id AND gmems.group_id = gmods.group_id " +
                "WHERE gmems.group_id = ?;";
    }

    public Map<Group, Boolean> getAllUnjoinedGroupsAreRequested(long accountId) {
        Map<Group, Boolean> unjoinedGroupsAreRequested = new HashMap<>();
        template.query(getAllUnjoinedGroupsQuery(), (resultSet, i) -> {
            Group group = groupDao.getSuffixedViewRowMapper("g").mapRow(resultSet, i);
            long isRequested = resultSet.getLong("requested");
            unjoinedGroupsAreRequested.put(group, isRequested != 0);
            return group;
        }, accountId, accountId);
        return unjoinedGroupsAreRequested;
    }

    private String getAllUnjoinedGroupsQuery() {
        return "SELECT " + groupDao.getViewFields("g") + ", gmr.group_id requested" +
                " FROM _groups g LEFT JOIN " +
                " (SELECT group_id FROM groups_memberships_requests" +
                " WHERE account_id = ?) gmr " +
                " ON gmr.group_id = g.id " +
                " WHERE g.id NOT IN " +
                " (SELECT group_id FROM groups_members" +
                " WHERE account_id = ?);";
    }

    public SearchableDto searchAccountsGroups(String searchString, int pageNumber, int limit) {
        String query = searchAccountsAndGroupsQuery(pageNumber, limit);
        String[] searchParameters = new String[3];
        Arrays.fill(searchParameters, '%' + searchString + '%');
        Collection<Searchable> entities = template.query(query, (resultSet, i) -> {
            Searchable searchable = new SearchableImpl();
            searchable.setId(resultSet.getLong("id"));
            searchable.setName(resultSet.getString("name"));
            if ("user".equals(resultSet.getString("type"))) {
                searchable.setType(SearchableType.ACCOUNT);
            } else {
                searchable.setType(SearchableType.GROUP);
            }
            return searchable;
        }, (Object[]) searchParameters);
        SearchableDto searchableDto = new SearchableDto(entities);
        Integer rowsNumber = template.queryForObject(accountsAndGroupsRowCountQuery(), (resultSet, i) ->
                resultSet.getInt("rows_number"), (Object[]) searchParameters);
        assert !isNull(rowsNumber);
        searchableDto.setPages(rowsNumber, limit);
        return searchableDto;
    }

    private String searchAccountsAndGroupsQuery(int pageNumber, int limit) {
        return "SELECT id, type, name " +
                " FROM " +
                " (SELECT id, 'user' type, CONCAT(name, ' ', surname) name FROM accounts " +
                " WHERE UPPER(name) LIKE UPPER(?) or UPPER(surname) LIKE UPPER(?)" +
                " UNION " +
                " SELECT id, 'group' type, name name FROM _groups " +
                " WHERE UPPER(name) LIKE UPPER(?) ) s " +
                " LIMIT " + limit + " OFFSET " + (pageNumber - 1) * limit + ';';
    }

    private String accountsAndGroupsRowCountQuery() {
        return "SELECT count(*) rows_number FROM " +
                " (SELECT id, 'user' type, CONCAT(name, ' ', surname) name FROM accounts " +
                " WHERE UPPER(name) LIKE UPPER(?) or UPPER(surname) LIKE UPPER(?)" +
                " UNION " +
                " SELECT id, 'group' type, name name FROM _groups " +
                " WHERE UPPER(name) LIKE UPPER(?) ) s ;";
    }
}
