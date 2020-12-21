package com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.testetst;

import com.getjavajob.training.yarginy.socialnetwork.common.models.NullEntitiesFactory;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.AccountImpl;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.additionaldata.Role;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.additionaldata.Sex;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.Dao;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.TransientDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Types;
import java.util.Collection;

import static com.getjavajob.training.yarginy.socialnetwork.dao.tables.AccountsTable.*;
import static java.util.Objects.isNull;

@Component
public class AccountDao implements Dao<Account> {
    private final JdbcTemplate template;
    private final NamedParameterJdbcTemplate namedTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    public AccountDao(DataSource dataSource) {
        template = new JdbcTemplate(dataSource);
        namedTemplate = new NamedParameterJdbcTemplate(dataSource);
        jdbcInsert = new SimpleJdbcInsert(dataSource);
        jdbcInsert.withTableName(TABLE);
    }

    @Override
    public Account select(long id) {
        String query = "SELECT * FROM accounts WHERE id = ?";
        try {
            return template.queryForObject(query, getAccountRowMapper(), id);
        } catch (TransientDataAccessException e) {
            throw new IllegalStateException(e);
        } catch (EmptyResultDataAccessException e) {
            return getNullEntity();
        }
    }

    public RowMapper<Account> getAccountViewRowMapper() {
        return (resultSet, i) -> {
            Account account = new AccountImpl();
            account.setId(resultSet.getLong(ID));
            account.setName(resultSet.getString(NAME));
            account.setSurname(resultSet.getString(SURNAME));
            account.setEmail(resultSet.getString(EMAIL));
            return account;
        };
    }

    public RowMapper<Account> getAccountRowMapper() {
        return (resultSet, i) -> {
            Account account = getAccountViewRowMapper().mapRow(resultSet, i);
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
        };
    }

    @Override
    public Account select(Account entityToSelect) {
        String query = "SELECT * FROM accounts WHERE email = ?";
        try {
            return template.queryForObject(query, getAccountRowMapper(), entityToSelect.getEmail());
        } catch (TransientDataAccessException e) {
            throw new IllegalArgumentException(e);
        } catch (EmptyResultDataAccessException e) {
            return getNullEntity();
        }
    }

    @Override
    public boolean create(Account entity) {
        try {
            return jdbcInsert.execute(createAccountFieldsMap(entity)) == 1;
        } catch (DuplicateKeyException e) {
            return false;
        }
    }

    public MapSqlParameterSource createAccountFieldsMap(Account account) {
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
    public boolean update(Account entity, Account storedEntity) {
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
        try {
            return namedTemplate.update(valuePlacer.getQuery(), valuePlacer.getParameters()) == 1;
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(Account entity) {
        String query = "DELETE FROM accounts WHERE email = ?";
        return template.update(query, entity.getEmail()) == 1;
    }

    @Override
    public Collection<Account> selectAll() {
        String query = "SELECT * FROM accounts";
        return template.query(query, getAccountViewRowMapper());
    }

    @Override
    public void checkEntity(Account entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Account getNullEntity() {
        return NullEntitiesFactory.getNullAccount();
    }

    @Override
    public Account approveFromStorage(Account entity) {
        throw new UnsupportedOperationException();
    }
}
