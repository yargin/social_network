package com.getjavajob.training.yarginy.socialnetwork.dao.entities.account.dao.sql;

import com.getjavajob.training.yarginy.socialnetwork.dao.entities.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.dao.entities.account.dto.AccountImpl;

import java.sql.*;

import static com.getjavajob.training.yarginy.socialnetwork.dao.entities.account.additionaldata.Sex.valueOf;
import static java.util.Objects.isNull;

public class AccountSQLQueriesHandler {
    private static final String TABLE = "Accounts";
    private static final String ID = "ID";
    private static final String NAME = "Name";
    private static final String SURNAME = "Surname";
    private static final String PATRONYMIC = "Patronymic";
    private static final String SEX = "sex";
    private static final String BIRTH_DATE = "Birth_date";
    private static final String PHONE = "Phone";
    private static final String WORK_PHONE = "Work_phone";
    private static final String HOME_ADDRESS = "Home_address";
    private static final String WORK_ADDRESS = "Work_address";
    private static final String ICQ = "Icq";
    private static final String SKYPE = "Skype";
    private static final String EMAIL = "Email";
    private static final String ADDITIONAL_EMAIL = "Additional_email";
    private static final String COUNTRY = "Country";
    private static final String CITY = "City";
    private static final String SELECT_BY_ID = "SELECT * FROM " + TABLE + " WHERE " + ID + " = ?";
    private static final String SELECT_BY_EMAIL = "SELECT * FROM " + TABLE + " WHERE " + EMAIL + " = ?";

    /**
     * updates row that given {@link ResultSet}'s cursor currently points to
     *
     * @param resultSet {@link ResultSet} with positioned cursor
     * @param account {@link Account} that will be placed to <code>resultSet</code>
     * @throws SQLException
     */
    public static void updateAccountRow(ResultSet resultSet, Account account) throws SQLException {
        resultSet.updateString(NAME, account.getName());
        resultSet.updateString(SURNAME, account.getSurname());
        resultSet.updateString(PATRONYMIC, account.getPatronymic());
        if (!isNull(account.getSex())) {
            resultSet.updateString(SEX, account.getSex().toString());
        }
        if (!isNull(account.getBirthDate())) {
            resultSet.updateDate(BIRTH_DATE, Date.valueOf(account.getBirthDate()));
        }
        resultSet.updateString(PHONE, account.getPhone());
        resultSet.updateString(WORK_PHONE, account.getWorkPhone());
        resultSet.updateString(HOME_ADDRESS, account.getHomeAddress());
        resultSet.updateString(WORK_ADDRESS, account.getWorkAddress());
        resultSet.updateString(ICQ, account.getIcq());
        resultSet.updateString(SKYPE, account.getSkype());
        resultSet.updateString(EMAIL, account.getEmail());
        resultSet.updateString(ADDITIONAL_EMAIL, account.getAdditionalEmail());
        resultSet.updateString(COUNTRY, account.getCountry());
        resultSet.updateString(CITY, account.getCity());
    }

    /**
     * retrieves {@link PreparedStatement} which execution result will be {@link Account} found by email. This query
     * might be useful if account not saved(for example new account) hence it doesn't have id number yet
     *
     * @param connection {@link Connection} specified connection to make {@link PreparedStatement}
     * @param email {@link Account}'s email
     * @return {@link PreparedStatement} that selects {@link Account} found by email
     */
    public static PreparedStatement getSelectStatementByEmail(Connection connection, String email) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(SELECT_BY_EMAIL, ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_UPDATABLE);
        statement.setString(1, email);
        return statement;
    }

    /**
     * retrieves {@link PreparedStatement} which execution result will be {@link Account} found by id
     *
     * @param connection {@link Connection} specified connection to make {@link PreparedStatement}
     * @param id {@link Account}'s id
     * @return {@link PreparedStatement} that selects {@link Account} found by id
     */
    public static PreparedStatement getSelectStatementById(Connection connection, int id) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID, ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_UPDATABLE);
        statement.setInt(1, id);
        return statement;
    }

    public static Account selectById(Connection connection, int id) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID)) {
            statement.setInt(1, id);
            return selectAccount(statement);
        }
    }

    public static Account selectByEmail(Connection connection, String email) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(SELECT_BY_EMAIL)) {
            statement.setString(1, email);
            return selectAccount(statement);
        }
    }

    /**
     * retrieves {@link Account} by executing specified statement
     *
     * @param statement statement to execute
     * @return {@link Account} if any was found, otherwise {@link Account#getNullAccount()}
     * @throws SQLException
     */
    private static Account selectAccount(PreparedStatement statement) throws SQLException {
        try (ResultSet resultSet = statement.executeQuery()) {
            Account account = new AccountImpl();
            if (!resultSet.next()) {
                return Account.getNullAccount();
            }
            account.setId(resultSet.getInt(ID));
            account.setName(resultSet.getString(NAME));
            account.setSurname(resultSet.getString(SURNAME));
            account.setPatronymic(resultSet.getString(PATRONYMIC));
            if (!isNull(resultSet.getString(SEX))) {
                account.setSex(valueOf(resultSet.getString(SEX)));
            }
            if (!isNull(resultSet.getDate(BIRTH_DATE))) {
                account.setBirthDate(resultSet.getDate(BIRTH_DATE).toLocalDate());
            }
            account.setHomeAddress(resultSet.getString(HOME_ADDRESS));
            account.setWorkAddress(resultSet.getString(WORK_ADDRESS));
            account.setPhone(resultSet.getString(PHONE));
            account.setWorkPhone(resultSet.getString(WORK_PHONE));
            account.setEmail(resultSet.getString(EMAIL));
            account.setAdditionalEmail(resultSet.getString(ADDITIONAL_EMAIL));
            account.setIcq(resultSet.getString(ICQ));
            account.setSkype(resultSet.getString(SKYPE));
            account.setCity(resultSet.getString(CITY));
            account.setCountry(resultSet.getString(COUNTRY));
            return account;
        }
    }
}
