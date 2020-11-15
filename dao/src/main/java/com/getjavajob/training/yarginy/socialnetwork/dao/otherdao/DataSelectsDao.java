package com.getjavajob.training.yarginy.socialnetwork.dao.otherdao;

import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.AccountImpl;
import com.getjavajob.training.yarginy.socialnetwork.common.models.group.Group;
import com.getjavajob.training.yarginy.socialnetwork.common.models.group.GroupImpl;
import com.getjavajob.training.yarginy.socialnetwork.common.models.searchable.Searchable;
import com.getjavajob.training.yarginy.socialnetwork.common.models.searchable.SearchableDto;
import com.getjavajob.training.yarginy.socialnetwork.common.models.searchable.SearchableImpl;
import com.getjavajob.training.yarginy.socialnetwork.common.models.searchable.SearchableType;
import com.getjavajob.training.yarginy.socialnetwork.dao.factories.connectionpool.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class DataSelectsDao {
    private static final int LIMIT = 10;
    private final ConnectionPool connectionPool;

    public DataSelectsDao(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    public Map<Account, Boolean> getGroupMembersModerators(long groupId) {
        Map<Account, Boolean> membersModerators = new HashMap<>();
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = prepareGroupMembersModerators(groupId, connection);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Account account = new AccountImpl();
                account.setId(resultSet.getLong("id"));
                account.setName(resultSet.getString("name"));
                account.setEmail(resultSet.getString("email"));
                account.setSurname(resultSet.getString("surname"));
                long isModerator = resultSet.getLong("moderator");
                membersModerators.put(account, isModerator != 0);
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
        return membersModerators;
    }

    private PreparedStatement prepareGroupMembersModerators(long groupId, Connection connection) throws SQLException {
        String query = "SELECT a.id id, a.email email, a.name name, a.surname surname, gmods.account_id moderator " +
                "FROM Groups_members gmems " +
                "LEFT JOIN Accounts a ON a.id = gmems.account_id " +
                "LEFT JOIN groups_moderators gmods " +
                "ON gmems.account_id = gmods.account_id AND gmems.group_id = gmods.group_id " +
                "WHERE gmems.group_id = ?;";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setLong(1, groupId);
        return statement;
    }

    public Map<Group, Boolean> getAllUnjoinedGroupsAreRequested(long accountId) {
        Map<Group, Boolean> unjoinedGroupsAreRequested = new HashMap<>();

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = prepareAllUnjoinedGroupsAreRequested(accountId, connection);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Group group = new GroupImpl();
                group.setId(resultSet.getLong("id"));
                group.setName(resultSet.getString("name"));
                long isRequested = resultSet.getLong("requested");
                unjoinedGroupsAreRequested.put(group, isRequested != 0);
            }
            return unjoinedGroupsAreRequested;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    private PreparedStatement prepareAllUnjoinedGroupsAreRequested(long accountId, Connection connection) throws SQLException {
        String query = "SELECT g.id id, g.name name, gmr.group_id requested" +
                " FROM _groups g LEFT JOIN " +
                " (SELECT group_id FROM groups_memberships_requests" +
                " WHERE account_id = ?) gmr " +
                " ON gmr.group_id = g.id " +
                " WHERE g.id NOT IN " +
                " (SELECT group_id FROM groups_members" +
                " WHERE account_id = ?);";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setLong(1, accountId);
        statement.setLong(2, accountId);
        return statement;
    }

    public SearchableDto searchAccountsGroups(String searchString, int pageNumber) {
        Collection<Searchable> entities = new ArrayList<>();
        SearchableDto searchableDto = new SearchableDto(entities);
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement resultStatement = prepareSearchAccountsGroups(connection, '%' + searchString + '%',
                     pageNumber);
             PreparedStatement rowsNumberStatement = prepareRowsCount(connection, '%' + searchString + '%');
             ResultSet resultSet = resultStatement.executeQuery();
             ResultSet resultSetRowsNumber = rowsNumberStatement.executeQuery();) {
            while (resultSet.next()) {
                Searchable searchable = new SearchableImpl();
                searchable.setId(resultSet.getLong("id"));
                searchable.setName(resultSet.getString("name"));
                if ("user".equals(resultSet.getString("type"))) {
                    searchable.setType(SearchableType.ACCOUNT);
                } else {
                    searchable.setType(SearchableType.GROUP);
                }
                entities.add(searchable);
            }
            searchableDto.setPages(resultSetRowsNumber.getInt("rows_number"), LIMIT);
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
        return searchableDto;
    }

    private PreparedStatement prepareSearchAccountsGroups(Connection connection, String searchString, int pageNumber)
            throws SQLException {
        String query = "SELECT id, type, name " +
                " , count(*) OVER() " +
                " AS rows_number FROM " +
                " (SELECT id, 'user' type, CONCAT(name, ' ', surname) name FROM accounts " +
                " WHERE UPPER(name) LIKE UPPER(?) or UPPER(surname) LIKE UPPER(?)" +
                " UNION " +
                " SELECT id, 'group' type, name name FROM _groups " +
                " WHERE UPPER(name) LIKE UPPER(?) ) s " +
                " LIMIT " + LIMIT + " OFFSET " + (pageNumber - 1) * LIMIT + ';';
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, searchString);
        statement.setString(2, searchString);
        statement.setString(3, searchString);
        return statement;
    }

    private PreparedStatement prepareRowsCount(Connection connection, String searchString)
            throws SQLException {
        String query = "SELECT count(*) rows_number FROM " +
                " (SELECT id, 'user' type, CONCAT(name, ' ', surname) name FROM accounts " +
                " WHERE UPPER(name) LIKE UPPER(?) or UPPER(surname) LIKE UPPER(?)" +
                " UNION " +
                " SELECT id, 'group' type, name name FROM _groups " +
                " WHERE UPPER(name) LIKE UPPER(?) ) s ;";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, searchString);
        statement.setString(2, searchString);
        statement.setString(3, searchString);
        return statement;
    }
}
