package com.getjavajob.training.yarginy.socialnetwork.dao.accountsingroups;

import com.getjavajob.training.yarginy.socialnetwork.dao.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.dao.group.Group;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AccountsGroupsSQLHandler {
    private static final String TABLE = "Accounts_In_Groups";
    private static final String ACCOUNTS_ID = "Account_id";
    private static final String GROUPS_ID = "Group_id";
    private static final String ACCOUNTS_TABLE = "Accounts";
    private static final String GROUPS_TABLE = "Groups";
    private static final String SELECT_BY_GROUPS = "SELECT * FROM " + ACCOUNTS_TABLE + " JOIN " + TABLE + "WHERE " + ACCOUNTS_TABLE + "." + ACCOUNTS_ID + " = ?";
    //todo: join
    String toS = "SELECT * FROM Groups JOIN Accounts_In_Groups WHERE Accounts_In_Groups.Account_id = ?";
    private static final String SELECT_BY_ACCOUNT = "SELECT Group_id FROM " + TABLE + " WHERE Account_id = ?";

    public static List<Group> selectByAccount(Connection connection, Account account) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(SELECT_BY_ACCOUNT);
             ResultSet resultSet = statement.executeQuery()) {
            List<Group> groups = new ArrayList<>();
            while (resultSet.next()) {
                groups.add()
            }
        }
    }


}
