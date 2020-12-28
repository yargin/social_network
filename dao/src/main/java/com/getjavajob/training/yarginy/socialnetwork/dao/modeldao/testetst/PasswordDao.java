package com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.testetst;

import com.getjavajob.training.yarginy.socialnetwork.common.models.NullEntitiesFactory;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.password.Password;
import com.getjavajob.training.yarginy.socialnetwork.common.models.password.PasswordImpl;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import javax.sql.DataSource;
import java.sql.Types;

public class PasswordDao extends AbstractDao<Password> {
    private static final String TABLE = "passwords";
    private static final String EMAIL = "email";
    private static final String PASSWORD = "password";
    private static final String[] FIELDS = {EMAIL, PASSWORD};
    private static final String PASSWORD_ALIAS = "pass";
    private static final String ACCOUNT_ALIAS = "acc";
    private final String selectAll;
    private final AccountDao accountDao;

    public PasswordDao(DataSource dataSource) {
        super(dataSource, TABLE, PASSWORD_ALIAS);
        accountDao = new AccountDao(dataSource);
        selectAll = "SELECT " + getFields(PASSWORD_ALIAS) + ", " + accountDao.getViewFields(ACCOUNT_ALIAS) + " FROM " +
                getTable(PASSWORD_ALIAS) + " JOIN " + accountDao.getTable(ACCOUNT_ALIAS) +
                " ON pass.email = acc.email";
    }

    @Override
    public String[] getPrimaryKeys() {
        return new String[]{EMAIL};
    }

    @Override
    public String[] getAltKeys() {
        return getPrimaryKeys();
    }

    @Override
    public Password getNullEntity() {
        return NullEntitiesFactory.getNullPassword();
    }

    @Override
    protected Object[] getObjectsAltKeys(Password password) {
        return new Object[]{password.getAccount().getEmail()};
    }

    @Override
    protected MapSqlParameterSource createEntityFieldsMap(Password password) {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue(EMAIL, password.getAccount().getEmail(), Types.VARCHAR);
        parameters.addValue(PASSWORD, password.getPassword(), Types.VARCHAR);
        return parameters;
    }

    @Override
    protected ValuePlacer getValuePlacer(Password password, Password storedPassword) {
        ValuePlacer placer = new ValuePlacer(TABLE, getAltKeys());
        placer.addFieldIfDiffers(password::getAccount, storedPassword::getAccount, EMAIL, Types.VARCHAR,
                Account::getEmail);
        placer.addFieldIfDiffers(password::getPassword, storedPassword::getPassword, PASSWORD, Types.VARCHAR);
        return placer;
    }

    @Override
    protected Object[] getObjectPrimaryKeys(Password password) {
        return new Object[]{password.getAccount().getEmail()};
    }

    @Override
    protected String getSelectAllQuery() {
        return selectAll;
    }

    @Override
    public RowMapper<Password> getViewRowMapper() {
        return (resultSet, i) -> {
            Password password = new PasswordImpl();
            password.setAccount(accountDao.getSuffixedViewRowMapper(ACCOUNT_ALIAS).mapRow(resultSet, i));
            password.setPassword(resultSet.getString(PASSWORD));
            return password;
        };
    }

    @Override
    public RowMapper<Password> getRowMapper() {
        return getViewRowMapper();
    }

    @Override
    protected String[] getFieldsList() {
        return getViewFieldsList();
    }

    @Override
    protected String[] getViewFieldsList() {
        return FIELDS;
    }
}
