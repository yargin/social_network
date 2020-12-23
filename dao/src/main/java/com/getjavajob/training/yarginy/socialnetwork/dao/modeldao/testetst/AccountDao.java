package com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.testetst;

import com.getjavajob.training.yarginy.socialnetwork.common.models.NullEntitiesFactory;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.AccountImpl;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.additionaldata.Role;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.additionaldata.Sex;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Types;

import static com.getjavajob.training.yarginy.socialnetwork.dao.tables.AccountsTable.*;
import static java.util.Objects.isNull;

@Component
public class AccountDao extends AbstractDao<Account> {
    private static final String SELECT_BY_ID = "SELECT * FROM accounts WHERE id = ?";
    private static final String SELECT_BY_ALT_KEY = "SELECT * FROM accounts WHERE email = ?";
    private static final String DELETE_BY_ID = "DELETE FROM accounts WHERE id = ?";
    private static final String SELECT_ALL = "SELECT * FROM accounts";

    public AccountDao(DataSource dataSource) {
        super(dataSource, TABLE);
    }

    @Override
    protected String getSelectByIdQuery() {
        return SELECT_BY_ID;
    }

    @Override
    public ResultSetExtractor<Account> getSuffixedViewExtractor(String suffix) {
        return resultSet -> {
            Account account = new AccountImpl();
            account.setId(resultSet.getLong(ID + suffix));
            account.setName(resultSet.getString(NAME + suffix));
            account.setSurname(resultSet.getString(SURNAME + suffix));
            account.setEmail(resultSet.getString(EMAIL + suffix));
            return account;
        };
    }

    @Override
    protected Object[] getAltKeys(Account account) {
        return new Object[]{account.getEmail()};
    }

    @Override
    protected Object[] getPrimaryKeys(Account account) {
        return new Object[]{account.getId()};
    }

    @Override
    protected String getSelectByAltKeysQuery() {
        return SELECT_BY_ALT_KEY;
    }

    @Override
    public ResultSetExtractor<Account> getSuffixedExtractor(String suffix) {
        return resultSet -> {
            Account account = getViewExtractor().extractData(resultSet);
            account.setPatronymic(resultSet.getString(PATRONYMIC + suffix));
            if (!isNull(resultSet.getString(SEX + suffix))) {
                account.setSex(Sex.valueOf(resultSet.getString(SEX + suffix)));
            }
            if (!isNull(resultSet.getDate(BIRTH_DATE + suffix))) {
                account.setBirthDate(resultSet.getDate(BIRTH_DATE + suffix));
            }
            if (!isNull(resultSet.getDate(REGISTRATION_DATE + suffix))) {
                account.setRegistrationDate(resultSet.getDate(REGISTRATION_DATE + suffix));
            }
            account.setAdditionalEmail(resultSet.getString(ADDITIONAL_EMAIL + suffix));
            if (!isNull(resultSet.getString(ROLE + suffix))) {
                account.setRole(Role.valueOf(resultSet.getString(ROLE + suffix)));
            }
            account.setIcq(resultSet.getString(ICQ + suffix));
            account.setSkype(resultSet.getString(SKYPE + suffix));
            account.setCity(resultSet.getString(CITY + suffix));
            account.setCountry(resultSet.getString(COUNTRY + suffix));
            account.setPhoto(resultSet.getBytes(PHOTO + suffix));
            return account;
        };
    }

    protected MapSqlParameterSource createEntityFieldsMap(Account account) {
        MapSqlParameterSource map = new MapSqlParameterSource();
        map.addValue(NAME, account.getName(), Types.VARCHAR);
        map.addValue(SURNAME, account.getSurname(), Types.VARCHAR);
        map.addValue(PATRONYMIC, account.getPatronymic(), Types.VARCHAR);
        if (!isNull(account.getSex())) {
            map.addValue(SEX, account.getSex().toString(), Types.CHAR);
        }
        map.addValue(BIRTH_DATE, account.getBirthDate(), Types.DATE);
        map.addValue(ICQ, account.getIcq(), Types.VARCHAR);
        map.addValue(SKYPE, account.getSkype(), Types.VARCHAR);
        map.addValue(EMAIL, account.getEmail(), Types.VARCHAR);
        map.addValue(ADDITIONAL_EMAIL, account.getAdditionalEmail(), Types.VARCHAR);
        map.addValue(COUNTRY, account.getCountry(), Types.VARCHAR);
        map.addValue(CITY, account.getCity(), Types.VARCHAR);
        map.addValue(REGISTRATION_DATE, account.getRegistrationDate(), Types.DATE);
        map.addValue(PHOTO, account.getPhoto(), Types.BLOB);
        if (!isNull(account.getRole())) {
            map.addValue(ROLE, account.getRole(), Types.CHAR);
        }
        return map;
    }

    @Override
    public ValuePlacer getValuePlacer(Account entity, Account storedEntity) {
        ValuePlacer valuePlacer = new ValuePlacer(TABLE, new String[]{EMAIL});
        valuePlacer.addFieldIfDiffers(entity::getName, storedEntity::getName, NAME, Types.VARCHAR);
        valuePlacer.addFieldIfDiffers(entity::getSurname, storedEntity::getSurname, SURNAME, Types.VARCHAR);
        valuePlacer.addFieldIfDiffers(entity::getPatronymic, storedEntity::getPatronymic, PATRONYMIC, Types.VARCHAR);
        valuePlacer.addFieldIfDiffers(entity::getSex, storedEntity::getSex, SEX, Types.CHAR, Sex::toString);
        valuePlacer.addFieldIfDiffers(entity::getBirthDate, storedEntity::getBirthDate, BIRTH_DATE, Types.DATE);
        valuePlacer.addFieldIfDiffers(entity::getIcq, storedEntity::getIcq, ICQ, Types.VARCHAR);
        valuePlacer.addFieldIfDiffers(entity::getSkype, storedEntity::getSkype, SKYPE, Types.VARCHAR);
        valuePlacer.addFieldIfDiffers(entity::getEmail, storedEntity::getEmail, EMAIL, Types.VARCHAR);
        valuePlacer.addFieldIfDiffers(entity::getAdditionalEmail, storedEntity::getAdditionalEmail, ADDITIONAL_EMAIL,
                Types.VARCHAR);
        valuePlacer.addFieldIfDiffers(entity::getCountry, storedEntity::getCountry, COUNTRY, Types.VARCHAR);
        valuePlacer.addFieldIfDiffers(entity::getCity, storedEntity::getCity, CITY, Types.VARCHAR);
        valuePlacer.addFieldIfDiffers(entity::getRegistrationDate, storedEntity::getRegistrationDate, REGISTRATION_DATE,
                Types.DATE);
        valuePlacer.addFieldIfDiffers(entity::getRole, storedEntity::getRole, ROLE, Types.CHAR, Role::toString);
        valuePlacer.addFieldIfDiffers(entity::getPhoto, storedEntity::getPhoto, PHOTO, Types.BLOB);

        valuePlacer.addKey(entity::getEmail, EMAIL, Types.VARCHAR);
        return valuePlacer;
    }

    @Override
    protected String getDeleteByPrimaryKeyQuery() {
        return DELETE_BY_ID;
    }

    @Override
    protected String getSelectAllQuery() {
        return SELECT_ALL;
    }

    @Override
    public Account getNullEntity() {
        return NullEntitiesFactory.getNullAccount();
    }
}
