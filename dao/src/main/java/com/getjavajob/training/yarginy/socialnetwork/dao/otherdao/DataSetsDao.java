package com.getjavajob.training.yarginy.socialnetwork.dao.otherdao;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.Group;
import com.getjavajob.training.yarginy.socialnetwork.common.models.searchable.Searchable;
import com.getjavajob.training.yarginy.socialnetwork.common.models.searchable.SearchableDto;
import com.getjavajob.training.yarginy.socialnetwork.common.models.searchable.SearchableType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static java.util.Arrays.fill;

@Component("dataSetsDao")
public class DataSetsDao implements Serializable {
    private static final int ACCOUNTS_GROUPS_PARAMETERS_NUMBER = 3;
    private final AccountGroupFieldsHelper fieldsHelper = new AccountGroupFieldsHelper();
    private transient JdbcTemplate template;

    @Autowired
    public void setTemplate(JdbcTemplate template) {
        this.template = template;
    }

    public Map<Account, Boolean> getGroupMembersModerators(long groupId) {
        Map<Account, Boolean> membersModerators = new HashMap<>();
        template.query(getGroupMembersAndModeratorsQuery(), (resultSet, i) -> {
            Account account = fieldsHelper.getAccountSuffixedViewRowMapper("a").mapRow(resultSet, i);
            long isModerator = resultSet.getLong("moderator");
            membersModerators.put(account, isModerator != 0);
            return account;
        }, groupId);
        return membersModerators;
    }

    private String getGroupMembersAndModeratorsQuery() {
        return "SELECT " + fieldsHelper.getAccountFields("a") + ", gmods.account_id moderator " +
                "FROM Groups_members gmems " +
                "LEFT JOIN Accounts a ON a.id = gmems.account_id " +
                "LEFT JOIN groups_moderators gmods " +
                "ON gmems.account_id = gmods.account_id AND gmems.group_id = gmods.group_id " +
                "WHERE gmems.group_id = ?;";
    }

    public Map<Group, Boolean> getAllUnjoinedGroupsAreRequested(long accountId) {
        Map<Group, Boolean> unjoinedGroupsAreRequested = new HashMap<>();
        template.query(getAllUnjoinedGroupsQuery(), (resultSet, i) -> {
            Group group = fieldsHelper.getGroupSuffixedViewRowMapper("g").mapRow(resultSet, i);
            long isRequested = resultSet.getLong("requested");
            unjoinedGroupsAreRequested.put(group, isRequested != 0);
            return group;
        }, accountId, accountId);
        return unjoinedGroupsAreRequested;
    }

    private String getAllUnjoinedGroupsQuery() {
        return "SELECT " + fieldsHelper.getGroupFields("g") + ", gmr.group_id requested" +
                " FROM `Groups` g LEFT JOIN " +
                " (SELECT group_id FROM groups_memberships_requests" +
                " WHERE account_id = ?) gmr " +
                " ON gmr.group_id = g.id " +
                " WHERE g.id NOT IN " +
                " (SELECT group_id FROM groups_members" +
                " WHERE account_id = ?);";
    }

    public SearchableDto searchAccountsGroups(String searchString, int pageNumber, int limit) {
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
        SearchableDto searchableDto = new SearchableDto(entities);
        Integer rowsNumber = template.queryForObject(accountsAndGroupsRowCountQuery(), (resultSet, i) ->
                resultSet.getInt("rows_number"), (Object[]) searchParameters);
        searchableDto.setPages(rowsNumber == null ? 0 : rowsNumber, limit);
        return searchableDto;
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

    static class AccountGroupFieldsHelper implements Serializable {
        private static final String ID = "id";
        private static final String NAME = "name";
        private static final String SURNAME = "surname";
        private static final String EMAIL = "email";
        private static final String[] ACCOUNT_VIEW_FIELDS = {ID, NAME, SURNAME, EMAIL};
        private static final String[] GROUP_VIEW_FIELDS = {ID, NAME};

        public String getAccountFields(String alias) {
            return buildString(ACCOUNT_VIEW_FIELDS, this::appendField, alias);
        }

        public String getGroupFields(String alias) {
            return buildString(GROUP_VIEW_FIELDS, this::appendField, alias);
        }

        public RowMapper<Account> getAccountSuffixedViewRowMapper(String suffix) {
            return (resultSet, i) -> {
                Account account = new Account();
                account.setId(resultSet.getLong(ID + suffix));
                account.setName(resultSet.getString(NAME + suffix));
                account.setSurname(resultSet.getString(SURNAME + suffix));
                account.setEmail(resultSet.getString(EMAIL + suffix));
                return account;
            };
        }

        public RowMapper<Group> getGroupSuffixedViewRowMapper(String suffix) {
            return (resultSet, i) -> {
                Group group = new Group();
                group.setId(resultSet.getLong(ID + suffix));
                group.setName(resultSet.getString(NAME + suffix));
                return group;
            };
        }

        private boolean appendField(StringBuilder stringBuilder, boolean firstIteration, String field, String alias) {
            String optionalAlias = alias.isEmpty() ? alias : alias + '.';
            if (!firstIteration) {
                stringBuilder.append(", ");
            }
            stringBuilder.append(optionalAlias).append(field).append(" as ").append(field).append(alias);
            return false;
        }

        private String buildString(String[] fields, Appender appender, String alias) {
            StringBuilder stringBuilder = new StringBuilder();
            boolean firstIteration = true;
            for (String field : fields) {
                firstIteration = appender.append(stringBuilder, firstIteration, field, alias);
            }
            return stringBuilder.toString();
        }
    }

    private interface Appender {
        boolean append(StringBuilder builder, boolean firstIteration, String value, String alias);
    }
}
