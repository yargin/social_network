package com.getjavajob.training.yarginy.socialnetwork.dao.jdbctemplates.modeldao;

import com.getjavajob.training.yarginy.socialnetwork.common.exceptions.DataFlowViolationException;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.additionaldata.Role;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.additionaldata.Sex;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.Types;

import static com.getjavajob.training.yarginy.socialnetwork.common.models.NullModelsFactory.getNullAccount;
import static java.util.Objects.isNull;

@Repository
public class AccountDao extends AbstractDao<Account> {
    private static final String TABLE = "accounts";
    private static final String ALIAS = "acc";
    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String SURNAME = "surname";
    private static final String PATRONYMIC = "patronymic";
    private static final String SEX = "sex";
    private static final String BIRTH_DATE = "birth_date";
    private static final String ICQ = "icq";
    private static final String SKYPE = "skype";
    private static final String EMAIL = "email";
    private static final String ADDITIONAL_EMAIL = "additional_email";
    private static final String COUNTRY = "country";
    private static final String CITY = "city";
    private static final String REGISTRATION_DATE = "registration_date";
    private static final String ROLE = "role";
    private static final String PHOTO = "photo";
    private static final String[] FIELDS = {ID, NAME, SURNAME, PATRONYMIC, SEX, BIRTH_DATE, ICQ, SKYPE, EMAIL,
            ADDITIONAL_EMAIL, COUNTRY, CITY, REGISTRATION_DATE, ROLE, PHOTO};
    private static final String[] VIEW_FIELDS = {ID, NAME, SURNAME, EMAIL};
    private final String selectAll = "SELECT " + getFields(ALIAS) + " FROM " + getTable(ALIAS);

    public AccountDao(JdbcTemplate template, SimpleJdbcInsert jdbcInsert, NamedParameterJdbcTemplate namedTemplate) {
        super(template, jdbcInsert, namedTemplate, TABLE, ALIAS);
    }

    @Override
    public RowMapper<Account> getViewRowMapper() {
        return getSuffixedViewRowMapper(ALIAS);
    }

    @Override
    protected Object[] getObjectAltKeys(Account account) {
        return new Object[]{account.getEmail()};
    }

    @Override
    protected Object[] getObjectPrimaryKeys(Account account) {
        return new Object[]{account.getId()};
    }

    @Override
    protected String[] getFieldsList() {
        return FIELDS;
    }

    @Override
    public String[] getViewFieldsList() {
        return VIEW_FIELDS;
    }

    @Override
    public String[] getPrimaryKeys() {
        return new String[]{ID};
    }

    @Override
    public String[] getAltKeys() {
        return new String[]{EMAIL};
    }

    @Override
    public RowMapper<Account> getRowMapper() {
        return getSuffixedRowMapper(ALIAS);
    }

    public RowMapper<Account> getSuffixedViewRowMapper(String suffix) {
        return (resultSet, i) -> {
            Account account = new Account();
            try {
                account.setId(resultSet.getLong(ID + suffix));
            } catch (DataFlowViolationException e) {
                return getNullModel();
            }
            account.setName(resultSet.getString(NAME + suffix));
            account.setSurname(resultSet.getString(SURNAME + suffix));
            account.setEmail(resultSet.getString(EMAIL + suffix));
            return account;
        };
    }

    public RowMapper<Account> getSuffixedRowMapper(String suffix) {
        return (resultSet, i) -> {
            Account account = getSuffixedViewRowMapper(suffix).mapRow(resultSet, i);
            assert account != null;
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
            byte[] photo = resultSet.getBytes(PHOTO + suffix);
            if (!isNull(photo)) {
                account.setPhoto(photo);
            }
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
        map.addValue(ROLE, isNull(account.getRole()) ? Role.USER.toString() : account.getRole().toString(), Types.CHAR);
        return map;
    }

    @Override
    public UpdateValuesPlacer getValuePlacer(Account account, Account storedAccount) {
        UpdateValuesPlacer valuesPlacer = new UpdateValuesPlacer(TABLE);
        valuesPlacer.addFieldIfDiffers(account::getName, storedAccount::getName, NAME, Types.VARCHAR);
        valuesPlacer.addFieldIfDiffers(account::getSurname, storedAccount::getSurname, SURNAME, Types.VARCHAR);
        valuesPlacer.addFieldIfDiffers(account::getPatronymic, storedAccount::getPatronymic, PATRONYMIC, Types.VARCHAR);
        valuesPlacer.addFieldIfDiffers(account::getSex, storedAccount::getSex, SEX, Types.CHAR, Sex::toString);
        valuesPlacer.addFieldIfDiffers(account::getBirthDate, storedAccount::getBirthDate, BIRTH_DATE, Types.DATE);
        valuesPlacer.addFieldIfDiffers(account::getIcq, storedAccount::getIcq, ICQ, Types.VARCHAR);
        valuesPlacer.addFieldIfDiffers(account::getSkype, storedAccount::getSkype, SKYPE, Types.VARCHAR);
        valuesPlacer.addFieldIfDiffers(account::getEmail, storedAccount::getEmail, EMAIL, Types.VARCHAR);
        valuesPlacer.addFieldIfDiffers(account::getAdditionalEmail, storedAccount::getAdditionalEmail, ADDITIONAL_EMAIL,
                Types.VARCHAR);
        valuesPlacer.addFieldIfDiffers(account::getCountry, storedAccount::getCountry, COUNTRY, Types.VARCHAR);
        valuesPlacer.addFieldIfDiffers(account::getCity, storedAccount::getCity, CITY, Types.VARCHAR);
        valuesPlacer.addFieldIfDiffers(account::getRole, storedAccount::getRole, ROLE, Types.CHAR, Role::toString);
        valuesPlacer.addFieldIfDiffers(account::getPhoto, storedAccount::getPhoto, PHOTO, Types.BLOB);

        valuesPlacer.addKey(storedAccount::getEmail, EMAIL, Types.VARCHAR);
        return valuesPlacer;
    }

    @Override
    protected String getSelectAllQuery() {
        return selectAll;
    }

    @Override
    public Account getNullModel() {
        return getNullAccount();
    }
}