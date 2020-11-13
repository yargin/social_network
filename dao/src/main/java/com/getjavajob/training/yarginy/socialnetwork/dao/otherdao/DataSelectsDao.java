package com.getjavajob.training.yarginy.socialnetwork.dao.otherdao;

import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.AccountImpl;
import com.getjavajob.training.yarginy.socialnetwork.common.models.group.Group;
import com.getjavajob.training.yarginy.socialnetwork.common.models.group.GroupImpl;
import com.getjavajob.training.yarginy.socialnetwork.dao.factories.connectionpool.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class DataSelectsDao {
    protected ConnectionPool connectionPool;

    public DataSelectsDao(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    public Map<Account, Boolean> getGroupMembersModerators(long groupId) {
        Map<Account, Boolean> membersModerators = new HashMap<>();
        String query = "SELECT a.id id, a.email email, a.name name, a.surname surname, gmods.account_id moderator " +
                "FROM Groups_members gmems " +
                "LEFT JOIN Accounts a ON a.id = gmems.account_id " +
                "LEFT JOIN groups_moderators gmods " +
                "ON gmems.account_id = gmods.account_id AND gmems.group_id = gmods.group_id " +
                "WHERE gmems.group_id = " + groupId + ';';
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
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

    public Map<Group, Boolean> getAllUnjoinedGroupsAreRequested(long accountId) {
        Map<Group, Boolean> unjoinedGroupsAreRequested = new HashMap<>();
        String query = "SELECT g.id id, g.name name, gmr.group_id requested" +
                " FROM _groups g LEFT JOIN " +
                " (SELECT group_id FROM groups_memberships_requests" +
                " WHERE account_id = " + accountId + ") gmr " +
                " ON gmr.group_id = g.id " +
                " WHERE g.id NOT IN " +
                " (SELECT group_id FROM groups_members" +
                " WHERE account_id = " + accountId + ");";
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
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
}
