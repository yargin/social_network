package com.getjavajob.training.yarginy.socialnetwork.dao.tables;

import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.AccountImpl;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.additionaldata.Role;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.additionaldata.Sex;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import static java.util.Objects.isNull;

public final class AccountsTable {
    public static final String TABLE = "Accounts";
    public static final String ID = TABLE + '.' + "Id";
    public static final String NAME = TABLE + '.' + "Name";
    public static final String SURNAME = TABLE + '.' + "Surname";
    public static final String PATRONYMIC = TABLE + '.' + "Patronymic";
    public static final String SEX = TABLE + '.' + "Sex";
    public static final String BIRTH_DATE = TABLE + '.' + "Birth_date";
    public static final String ICQ = TABLE + '.' + "Icq";
    public static final String SKYPE = TABLE + '.' + "Skype";
    public static final String EMAIL = TABLE + '.' + "Email";
    public static final String ADDITIONAL_EMAIL = TABLE + '.' + "Additional_email";
    public static final String COUNTRY = TABLE + '.' + "Country";
    public static final String CITY = TABLE + '.' + "City";
    public static final String REGISTRATION_DATE = TABLE + '.' + "Registration_date";
    public static final String ROLE = TABLE + '.' + "Role";
    public static final String PHOTO = TABLE + '.' + "Photo";
    public static final String VIEW_FIELDS = ID + ", " + NAME + ", " + SURNAME + ", " + EMAIL;
    public static final String[] FIELDS = {"name", "surname", "patronymic", "sex", "birth_date", "icq",
            "skype", "email", "additional_email", "country", "city", "registration_date", "role", "photo"};

    private AccountsTable() {
    }

    public static String getViewFieldsWithAlias(String alias) {
        return alias + ".id id" + alias + ", " +
                alias + ".name name" + alias + ", " +
                alias + ".surname surname" + alias + ", " +
                alias + ".email email" + alias;
    }

    public static String getFields() {
        StringBuilder stringBuilder = new StringBuilder();
        for (String field : FIELDS) {
            stringBuilder.append(field).append(", ");
        }
        return stringBuilder.substring(0, stringBuilder.length() - 2);
    }

    public static String getParams() {
        StringBuilder stringBuilder = new StringBuilder();
        for (String field : FIELDS) {
            stringBuilder.append(':').append(field).append(", ");
        }
        return stringBuilder.substring(0, stringBuilder.length() - 2);
    }

    public static String getInsertParams() {
        StringBuilder stringBuilder = new StringBuilder();
        for (String field : FIELDS) {
            stringBuilder.append(field).append(" = :").append(field).append(", ");
        }
        return stringBuilder.substring(0, stringBuilder.length() - 2);
    }

    public static MapSqlParameterSource getMapSqlParameterSource(Account account) {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("name", account.getName(), Types.VARCHAR);
        parameters.addValue("surname", account.getSurname(), Types.VARCHAR);
        parameters.addValue("patronymic", account.getPatronymic(), Types.VARCHAR);

        String sexString = account.getSex() == null ? null : account.getSex().toString();
        parameters.addValue("sex", sexString, Types.CHAR);

        parameters.addValue("birth_date", account.getBirthDate(), Types.DATE);
        parameters.addValue("icq", account.getIcq(), Types.VARCHAR);
        parameters.addValue("skype", account.getSkype(), Types.VARCHAR);
        parameters.addValue("email", account.getEmail(), Types.VARCHAR);
        parameters.addValue("additional_email", account.getAdditionalEmail(), Types.VARCHAR);
        parameters.addValue("country", account.getCountry(), Types.VARCHAR);
        parameters.addValue("city", account.getCity(), Types.VARCHAR);
        //todo null values
        parameters.addValue("registration_date", account.getRegistrationDate(), Types.DATE);

        String roleString = account.getRole() == null ? null : account.getRole().toString();
        parameters.addValue("role", roleString, Types.CHAR);

        parameters.addValue("photo", account.getPhoto(), Types.BLOB);
        return parameters;
    }

    public static RowMapper<Account> getViewRowMapper() {
        return new AccountViewRowMapper();
    }

    public static RowMapper<Account> getAccountRowMapper() {
        return new AccountRowMapper();
    }

    private static class AccountRowMapper implements RowMapper<Account> {
        private final AccountViewRowMapper viewRowMapper = new AccountViewRowMapper();

        @Override
        public Account mapRow(ResultSet resultSet, int i) throws SQLException {
            Account account = viewRowMapper.mapRow(resultSet, i);
            account.setPatronymic(resultSet.getString(PATRONYMIC));
            if (!isNull(resultSet.getString(SEX))) {
                account.setSex(Sex.valueOf(resultSet.getString(SEX)));
            }
            if (!isNull(resultSet.getDate(BIRTH_DATE))) {
                account.setBirthDate(resultSet.getDate(BIRTH_DATE));
            }
            if (!isNull(resultSet.getDate(REGISTRATION_DATE))) {
                account.setRegistrationDate(resultSet.getDate(REGISTRATION_DATE));
            }
            account.setAdditionalEmail(resultSet.getString(ADDITIONAL_EMAIL));
            if (!isNull(resultSet.getString(ROLE))) {
                account.setRole(Role.valueOf(resultSet.getString(ROLE)));
            }
            account.setIcq(resultSet.getString(ICQ));
            account.setSkype(resultSet.getString(SKYPE));
            account.setCity(resultSet.getString(CITY));
            account.setCountry(resultSet.getString(COUNTRY));
            account.setPhoto(resultSet.getBytes(PHOTO));
            return account;
        }
    }

    private static class AccountViewRowMapper implements RowMapper<Account> {
        @Override
        public Account mapRow(ResultSet resultSet, int i) throws SQLException {
            Account account = new AccountImpl();
            account.setId(resultSet.getLong(ID));
            account.setName(resultSet.getString(NAME));
            account.setSurname(resultSet.getString(SURNAME));
            account.setEmail(resultSet.getString(EMAIL));
            return account;
        }
    }
}
