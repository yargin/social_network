package com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.testetst;

import com.getjavajob.training.yarginy.socialnetwork.common.models.NullEntitiesFactory;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.AccountImpl;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.additionaldata.Role;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.additionaldata.Sex;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.Dao;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;
import java.sql.Types;
import java.util.Collection;

import static com.getjavajob.training.yarginy.socialnetwork.dao.tables.AccountsTable.*;
import static java.util.Objects.isNull;

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
        return template.query(query, getAccountExtractor(), id);
    }

    public ResultSetExtractor<Account> getAccountViewExtractor() {
        return resultSet -> {
            Account account = new AccountImpl();
            account.setId(resultSet.getLong(ID));
            account.setName(resultSet.getString(NAME));
            account.setSurname(resultSet.getString(SURNAME));
            account.setEmail(resultSet.getString(EMAIL));
            return account;
        };
    }

    public ResultSetExtractor<Account> getAccountExtractor() {
        return resultSet -> {
            Account account = getAccountViewExtractor().extractData(resultSet);
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
        return template.query(query, getAccountExtractor(), entityToSelect.getEmail());

    }

    @Override
    public boolean create(Account entity) {
        return jdbcInsert.execute(createAccountFieldsMap(entity)) == 1;
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
        String query = "UPDATE accounts SET name = :surname";
    }

    @Override
    public boolean delete(Account entity) {
        String query = "DELETE FROM accounts WHERE email = ?";
        return template.update(query, entity.getEmail()) == 1;
    }

    @Override
    public Collection<Account> selectAll() {
        String query = "SELECT * FROM accounts";
        return template.queryForList(query, Account.class);
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
