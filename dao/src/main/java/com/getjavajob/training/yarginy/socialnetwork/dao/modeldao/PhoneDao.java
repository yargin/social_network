package com.getjavajob.training.yarginy.socialnetwork.dao.modeldao;

import com.getjavajob.training.yarginy.socialnetwork.common.exceptions.DataFlowViolationException;
import com.getjavajob.training.yarginy.socialnetwork.common.models.NullEntitiesFactory;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.phone.Phone;
import com.getjavajob.training.yarginy.socialnetwork.common.models.phone.PhoneImpl;
import com.getjavajob.training.yarginy.socialnetwork.common.models.phone.additionaldata.PhoneType;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.batch.AbstractBatchDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.Types;

@Repository
public class PhoneDao extends AbstractBatchDao<Phone> {
    private static final String TABLE = "phones";
    private static final String ALIAS = "ph";
    private static final String ACCOUNT_ALIAS = "acc";
    private static final String ID = "id";
    private static final String NUMBER = "number";
    private static final String TYPE = "type";
    private static final String OWNER = "owner_id";
    private static final String[] FIELDS = new String[]{ID, NUMBER, TYPE, OWNER};
    private static final String[] VIEW_FIELDS = new String[]{ID, NUMBER, TYPE};
    private final AccountDao accountDao;
    private final String selectAll;

    @Autowired
    public PhoneDao(JdbcTemplate template, SimpleJdbcInsert jdbcInsert, NamedParameterJdbcTemplate namedTemplate,
                    AccountDao accountDao) {
        super(template, jdbcInsert, namedTemplate, TABLE, ALIAS);
        this.accountDao = accountDao;
        selectAll = "SELECT " + getFields(ALIAS) + ", " + accountDao.getViewFields(ACCOUNT_ALIAS) + " FROM " +
                getTable(ALIAS) + " LEFT JOIN " + accountDao.getTable(ACCOUNT_ALIAS) + " ON ph.owner_id = acc.id";
    }

    @Override
    public Phone getNullEntity() {
        return NullEntitiesFactory.getNullPhone();
    }

    @Override
    protected Object[] getObjectAltKeys(Phone phone) {
        return new Object[]{phone.getNumber()};
    }

    @Override
    protected MapSqlParameterSource createEntityFieldsMap(Phone phone) {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue(NUMBER, phone.getNumber(), Types.VARCHAR);
        parameters.addValue(TYPE, phone.getType(), Types.CHAR);
        parameters.addValue(OWNER, phone.getOwner().getId(), Types.BIGINT);
        return parameters;
    }

    @Override
    protected UpdateValuesPlacer getValuePlacer(Phone phone, Phone storedPhone) {
        UpdateValuesPlacer valuesPlacer = new UpdateValuesPlacer(TABLE);
        valuesPlacer.addFieldIfDiffers(phone::getNumber, storedPhone::getNumber, NUMBER, Types.VARCHAR);
        valuesPlacer.addFieldIfDiffers(phone::getType, storedPhone::getType, TYPE, Types.CHAR, PhoneType::toString);
        valuesPlacer.addFieldIfDiffers(phone::getOwner, storedPhone::getOwner, OWNER, Types.BIGINT, Account::getId);

        valuesPlacer.addKey(storedPhone::getId, ID, Types.BIGINT);
        return valuesPlacer;
    }

    @Override
    protected Object[] getObjectPrimaryKeys(Phone phone) {
        return new Object[]{phone.getId()};
    }

    @Override
    protected String getSelectAllQuery() {
        return selectAll;
    }

    public RowMapper<Phone> getSuffixedRowMapper(String phoneSuffix, String accountSuffix) {
        return (resultSet, i) -> {
            Phone phone = getSuffixedViewRowMapper(phoneSuffix).mapRow(resultSet, i);
            if (phone == null) {
                return getNullEntity();
            }
            Account account = accountDao.getSuffixedViewRowMapper(accountSuffix).mapRow(resultSet, i);
            phone.setOwner(account);
            return phone;
        };
    }

    public RowMapper<Phone> getSuffixedViewRowMapper(String phoneSuffix) {
        return (resultSet, i) -> {
            Phone phone = new PhoneImpl();
            try {
                phone.setId(resultSet.getLong(ID + phoneSuffix));
            } catch (DataFlowViolationException e) {
                return getNullEntity();
            }
            phone.setNumber(resultSet.getString(NUMBER + phoneSuffix));
            String phoneType = resultSet.getString(TYPE + phoneSuffix);
            if (phoneType != null) {
                phone.setType(PhoneType.valueOf(resultSet.getString(TYPE + phoneSuffix)));
            }
            return phone;
        };
    }

    @Override
    public RowMapper<Phone> getViewRowMapper() {
        return getSuffixedViewRowMapper(ALIAS);
    }

    @Override
    public RowMapper<Phone> getRowMapper() {
        return getSuffixedRowMapper(ALIAS, ACCOUNT_ALIAS);
    }

    @Override
    protected String[] getFieldsList() {
        return FIELDS;
    }

    @Override
    protected String[] getViewFieldsList() {
        return VIEW_FIELDS;
    }

    @Override
    public String[] getPrimaryKeys() {
        return new String[]{ID};
    }

    @Override
    public String[] getAltKeys() {
        return new String[]{NUMBER};
    }
}
