package com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.testetst;

import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.AccountImpl;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.additionaldata.Role;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.additionaldata.Sex;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.testetst.queryplacers.InsertQueryPlacer;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.testetst.queryplacers.QueryParametersPlacer;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.testetst.queryplacers.UpdateQueryPlacer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import static java.util.Objects.isNull;

public class AccountFieldsHandler {
    public static final String TABLE = "accounts";
    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String SURNAME = "surname";
    public static final String PATRONYMIC = "patronymic";
    public static final String SEX = "sex";
    public static final String BIRTH_DATE = "birth_date";
    public static final String ICQ = "icq";
    public static final String SKYPE = "skype";
    public static final String EMAIL = "email";
    public static final String ADDITIONAL_EMAIL = "additional_email";
    public static final String COUNTRY = "country";
    public static final String CITY = "city";
    public static final String REGISTRATION_DATE = "registration_date";
    public static final String ROLE = "role";
    public static final String PHOTO = "photo";
    public static final String VIEW_FIELDS = ID + ", " + NAME + ", " + SURNAME + ", " + EMAIL;
    public static final String[] FIELDS = {NAME, SURNAME, PATRONYMIC, SEX, BIRTH_DATE, ICQ, SKYPE, EMAIL,
            ADDITIONAL_EMAIL, COUNTRY, CITY, REGISTRATION_DATE, ROLE, PHOTO};

    public QueryAndParameters getInsertQueryAndParameters(Account account, Account storedAccount) {
        return new QueryAndParameters(new InsertQueryPlacer(), account, storedAccount);
    }

    public QueryAndParameters getUpdateQueryAndParameters(Account account, Account storedAccount) {
        return new QueryAndParameters(new UpdateQueryPlacer(), account, storedAccount);
    }

    //todo i want to see here table.field
    public Account mapRow(ResultSet resultSet, int i) throws SQLException {
        Account account = mapViewRow(resultSet, i);
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

    public Account mapViewRow(ResultSet resultSet, int i) throws SQLException {
        Account account = new AccountImpl();
        account.setId(resultSet.getLong(ID));
        account.setName(resultSet.getString(NAME));
        account.setSurname(resultSet.getString(SURNAME));
        account.setEmail(resultSet.getString(EMAIL));
        return account;
    }

    private String fieldWithTable(String table, String field) {
        return table + '.' + field;
    }

    static class QueryAndParameters extends AbstractQueryAndParameters<Account> {
        public QueryAndParameters(QueryParametersPlacer queryParametersPlacer, Account account, Account storedAccount) {
            super(queryParametersPlacer);
            setParamsInFields(account, storedAccount);
        }

        private void setParamsInFields(Account account, Account storedAccount) {
            addFieldIfDiffers(account::getName, storedAccount::getName, NAME, Types.VARCHAR);
            addFieldIfDiffers(account::getSurname, storedAccount::getSurname, SURNAME, Types.VARCHAR);
            addFieldIfDiffers(account::getPatronymic, storedAccount::getPatronymic, PATRONYMIC, Types.VARCHAR);
            addFieldIfDiffers(account::getSex, storedAccount::getSex, SEX, Types.CHAR, Sex::toString);
            addFieldIfDiffers(account::getBirthDate, storedAccount::getBirthDate, BIRTH_DATE, Types.DATE);
            addFieldIfDiffers(account::getIcq, storedAccount::getIcq, ICQ, Types.VARCHAR);
            addFieldIfDiffers(account::getSkype, storedAccount::getSkype, SKYPE, Types.VARCHAR);
            addFieldIfDiffers(account::getEmail, storedAccount::getEmail, EMAIL, Types.VARCHAR);
            addFieldIfDiffers(account::getAdditionalEmail, storedAccount::getAdditionalEmail, ADDITIONAL_EMAIL,
                    Types.VARCHAR);
            addFieldIfDiffers(account::getCountry, storedAccount::getCountry, COUNTRY, Types.VARCHAR);
            addFieldIfDiffers(account::getCity, storedAccount::getCity, CITY, Types.VARCHAR);
            addFieldIfDiffers(account::getRegistrationDate, storedAccount::getRegistrationDate, REGISTRATION_DATE,
                    Types.DATE);
            addFieldIfDiffers(account::getRole, storedAccount::getRole, ROLE, Types.CHAR, Role::toString);
            addFieldIfDiffers(account::getPhoto, storedAccount::getPhoto, PHOTO, Types.BLOB);
        }

        public String getQueryParameters() {
            return queryParametersPlacer.getQueryParameters();
        }
    }
}
