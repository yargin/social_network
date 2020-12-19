package com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.testetst;

import com.getjavajob.training.yarginy.socialnetwork.common.models.NullEntitiesFactory;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.AccountImpl;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.additionaldata.Role;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.additionaldata.Sex;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.testetst.newnew.Initializer;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.testetst.newnew.queryandparamplacer.InsertQueryAndParamPlacer;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.testetst.newnew.queryandparamplacer.QueryAndParamPlacer;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.testetst.newnew.queryandparamplacer.UpdateQueryAndParamPlacer;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import static com.getjavajob.training.yarginy.socialnetwork.dao.tables.AccountsTable.*;
import static java.util.Objects.isNull;

@Component("accountDaoFieldsHandler")
public class AccountDaoFieldsHandler implements DaoFieldsHandler<Account> {
    private static final String SELECT_BY_ID = "SELECT * FROM Accounts WHERE id = :id";
    private static final String SELECT_BY_ALT_KEY = "SELECT * FROM Accounts WHERE email = :email";
    private static final String SELECT_ALL = "SELECT * FROM Accounts";
    private static final String DELETE_ACCOUNT = "DELETE FROM Accounts WHERE email = :email";

    public String getSelectByIdQuery() {
        return SELECT_BY_ID;
    }

    public String getSelectByAltKeyQuery() {
        return SELECT_BY_ALT_KEY;
    }

    public String getSelectAllQuery() {
        return SELECT_ALL;
    }

    public String getDeleteQuery() {
        return DELETE_ACCOUNT;
    }

    public QueryAndParamPlacer getInsertQueryAndParameters(Account account, Account storedAccount) {
        return new InsertQueryAndParamPlacer<>(TABLE, new AccountInitializer(), account, storedAccount);
    }

    public QueryAndParamPlacer getUpdateQueryAndParameters(Account account, Account storedAccount) {
        return new UpdateQueryAndParamPlacer<>(TABLE, new AccountInitializer(), account, storedAccount, new String[]{ID});
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

    public MapSqlParameterSource getAltKeyParameter(Account account) {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("email", account.getEmail(), Types.VARCHAR);
        return parameters;
    }

    public Account getNullEntity() {
        return NullEntitiesFactory.getNullAccount();
    }

    static class AccountInitializer extends Initializer<Account> {
        @Override
        public void initParams(Account entity, Account storedAccount) {
            placer.placeValueIfDiffers(entity::getName, storedAccount::getName, NAME, Types.VARCHAR);
            placer.placeValueIfDiffers(entity::getSurname, storedAccount::getSurname, SURNAME, Types.VARCHAR);
            placer.placeValueIfDiffers(entity::getPatronymic, storedAccount::getPatronymic, PATRONYMIC, Types.VARCHAR);
            placer.placeValueIfDiffers(entity::getSex, storedAccount::getSex, SEX, Types.CHAR, Sex::toString);
            placer.placeValueIfDiffers(entity::getBirthDate, storedAccount::getBirthDate, BIRTH_DATE, Types.DATE);
            placer.placeValueIfDiffers(entity::getIcq, storedAccount::getIcq, ICQ, Types.VARCHAR);
            placer.placeValueIfDiffers(entity::getSkype, storedAccount::getSkype, SKYPE, Types.VARCHAR);
            placer.placeValueIfDiffers(entity::getEmail, storedAccount::getEmail, EMAIL, Types.VARCHAR);
            placer.placeValueIfDiffers(entity::getAdditionalEmail, storedAccount::getAdditionalEmail, ADDITIONAL_EMAIL,
                    Types.VARCHAR);
            placer.placeValueIfDiffers(entity::getCountry, storedAccount::getCountry, COUNTRY, Types.VARCHAR);
            placer.placeValueIfDiffers(entity::getCity, storedAccount::getCity, CITY, Types.VARCHAR);
            placer.placeValueIfDiffers(entity::getRegistrationDate, storedAccount::getRegistrationDate, REGISTRATION_DATE,
                    Types.DATE);
            placer.placeValueIfDiffers(entity::getRole, storedAccount::getRole, ROLE, Types.CHAR, Role::toString);
            placer.placeValueIfDiffers(entity::getPhoto, storedAccount::getPhoto, PHOTO, Types.BLOB);
        }

        @Override
        public void setPKey(Account storedAccount) {
            placer.placeKey(storedAccount::getId, ID, Types.BIGINT);
        }
    }
}
