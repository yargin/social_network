package com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.testetst;

import com.getjavajob.training.yarginy.socialnetwork.common.models.NullEntitiesFactory;
import com.getjavajob.training.yarginy.socialnetwork.common.models.phone.Phone;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Types;

@Component
public class PhoneDao extends AbstractDao<Phone> {
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

    public PhoneDao(DataSource dataSource) {
        super(dataSource, TABLE, ALIAS);
        accountDao = new AccountDao(dataSource);
        selectAll = "SELECT " + getFields(ALIAS) + ", " + accountDao.getViewFields(ACCOUNT_ALIAS) + " FROM " +
                TABLE + ' ' + ALIAS +  " JOIN accounts acc ON ph.owner_id = acc.id";
    }

    @Override
    public Phone getNullEntity() {
        return NullEntitiesFactory.getNullPhone();
    }

    @Override
    protected Object[] getObjectsAltKeys(Phone phone) {
        return new Object[]{phone.getNumber()};
    }

    @Override
    protected MapSqlParameterSource createEntityFieldsMap(Phone phone) {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue(NUMBER, phone.getNumber(), Types.VARCHAR);
        parameters.addValue(TYPE, phone.getType(), Types.CHAR);

        return null;
    }

    @Override
    protected ValuePlacer getValuePlacer(Phone phone, Phone storedPhone) {
        ValuePlacer placer = new ValuePlacer(TABLE, getAltKeys());
        return null;
    }

    @Override
    protected Object[] getObjectPrimaryKeys(Phone phone) {
        return new Object[]{phone.getId()};
    }

    @Override
    protected String getSelectAllQuery() {
        return selectAll;
    }

    public RowMapper<Phone> getSuffixedRowMapper(String suffix) {
        return null;
    }

    public RowMapper<Phone> getSuffixedViewRowMapper(String suffix) {
        return null;
    }

    @Override
    public RowMapper<Phone> getViewRowMapper() {
        return null;
    }

    @Override
    public RowMapper<Phone> getRowMapper() {
        return null;
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
