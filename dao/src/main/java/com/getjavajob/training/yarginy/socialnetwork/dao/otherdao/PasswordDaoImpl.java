package com.getjavajob.training.yarginy.socialnetwork.dao.otherdao;

import com.getjavajob.training.yarginy.socialnetwork.common.entities.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.dao.factories.AbstractDbFactory;
import com.getjavajob.training.yarginy.socialnetwork.dao.factories.connector.DbConnector;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PasswordDaoImpl implements PasswordDao {
    public static final String SELECT_BY_EMAIL_PASSWORD = "SELECT EXISTS FROM Passwords WHERE email = ? AND password = ?";
    private final DbConnector dbConnector;
    private final String encryptionType;

    public PasswordDaoImpl(DbConnector dbConnector, String encryptionType) {
        this.dbConnector = dbConnector;
        this.encryptionType = encryptionType;
    }

    @Override
    public boolean isRegistered(String email, String password) {
        try (Connection connection = dbConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_BY_EMAIL_PASSWORD);
             ResultSet resultSet = statement.executeQuery()) {
            MessageDigest md = MessageDigest.getInstance(encryptionType);
            if (!resultSet.next()) {
                return false;
            }
        } catch (SQLException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Account getAccount(String email, String password) {
        return null;
    }

    @Override
    public boolean changePassword(Account account, String oldPassword, String newPassword) {
        return false;
    }
}
