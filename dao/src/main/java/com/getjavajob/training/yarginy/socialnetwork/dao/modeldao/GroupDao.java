package com.getjavajob.training.yarginy.socialnetwork.dao.modeldao;

import com.getjavajob.training.yarginy.socialnetwork.common.exceptions.DataFlowViolationException;
import com.getjavajob.training.yarginy.socialnetwork.common.models.NullEntitiesFactory;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.group.Group;
import com.getjavajob.training.yarginy.socialnetwork.common.models.group.GroupImpl;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.batch.AbstractBatchDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.Types;

@Repository("groupDao")
public class GroupDao extends AbstractBatchDao<Group> {
    private static final String TABLE = "_groups";
    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String DESCRIPTION = "description";
    private static final String OWNER = "owner_id";
    private static final String CREATION_DATE = "creation_date";
    private static final String PHOTO = "photo";
    private static final String[] FIELDS = {ID, NAME, DESCRIPTION, OWNER, CREATION_DATE, PHOTO};
    private static final String GROUP_ALIAS = "gr";
    private static final String ACCOUNT_ALIAS = "acc";

    private final String selectAll;
    private final AccountDao accountDao;

    @Autowired
    public GroupDao(JdbcTemplate template, SimpleJdbcInsert jdbcInsert, NamedParameterJdbcTemplate namedTemplate,
                    AccountDao accountDao) {
        super(template, jdbcInsert, namedTemplate, TABLE, GROUP_ALIAS);
        this.accountDao = accountDao;
        selectAll = "SELECT " + getFields(GROUP_ALIAS) + ", " + accountDao.getViewFields(ACCOUNT_ALIAS) + " FROM " +
                getTable(GROUP_ALIAS) + " LEFT JOIN " + accountDao.getTable(ACCOUNT_ALIAS) + " ON gr.owner_id = acc.id";
    }

    @Override
    protected String[] getFieldsList() {
        return FIELDS;
    }

    @Override
    protected String[] getViewFieldsList() {
        return new String[]{ID, NAME};
    }

    @Override
    public String[] getPrimaryKeys() {
        return new String[]{ID};
    }

    @Override
    public String[] getAltKeys() {
        return new String[]{NAME};
    }

    @Override
    public Group getNullEntity() {
        return NullEntitiesFactory.getNullGroup();
    }

    @Override
    protected Object[] getObjectAltKeys(Group group) {
        return new Object[]{group.getName()};
    }

    @Override
    protected MapSqlParameterSource createEntityFieldsMap(Group group) {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue(NAME, group.getName(), Types.VARCHAR);
        parameters.addValue(DESCRIPTION, group.getDescription(), Types.VARCHAR);
        parameters.addValue(OWNER, group.getOwner().getId(), Types.BIGINT);
        parameters.addValue(CREATION_DATE, group.getCreationDate(), Types.DATE);
        parameters.addValue(PHOTO, group.getPhoto(), Types.BLOB);
        return parameters;
    }

    @Override
    protected UpdateValuesPlacer getValuePlacer(Group group, Group storedGroup) {
        UpdateValuesPlacer valuesPlacer = new UpdateValuesPlacer(TABLE);
        valuesPlacer.addFieldIfDiffers(group::getName, storedGroup::getName, NAME, Types.VARCHAR);
        valuesPlacer.addFieldIfDiffers(group::getDescription, storedGroup::getDescription, DESCRIPTION, Types.VARCHAR);
        valuesPlacer.addFieldIfDiffers(group::getOwner, storedGroup::getOwner, OWNER, Types.VARCHAR, Account::getId);
        valuesPlacer.addFieldIfDiffers(group::getCreationDate, storedGroup::getCreationDate, CREATION_DATE, Types.DATE);
        valuesPlacer.addFieldIfDiffers(group::getPhoto, storedGroup::getPhoto, PHOTO, Types.BLOB);

        valuesPlacer.addKey(group::getName, NAME, Types.VARCHAR);
        return valuesPlacer;
    }

    @Override
    protected Object[] getObjectPrimaryKeys(Group group) {
        return new Object[]{group.getId()};
    }

    @Override
    protected String getSelectAllQuery() {
        return selectAll;
    }

    @Override
    public RowMapper<Group> getViewRowMapper() {
        return getSuffixedViewRowMapper(GROUP_ALIAS);
    }

    public RowMapper<Group> getSuffixedViewRowMapper(String suffix) {
        return (resultSet, i) -> {
            Group group = new GroupImpl();
            try {
                group.setId(resultSet.getLong(ID + suffix));
            } catch (DataFlowViolationException e) {
                return getNullEntity();
            }
            group.setName(resultSet.getString(NAME + suffix));
            return group;
        };
    }

    public RowMapper<Group> getSuffixedRowMapper(String groupSuffix, String accountSuffix) {
        return (resultSet, i) -> {
            Group group = getViewRowMapper().mapRow(resultSet, i);
            if (group == null) {
                return getNullEntity();
            }
            group.setDescription(resultSet.getString(DESCRIPTION + groupSuffix));
            group.setCreationDate(resultSet.getDate(CREATION_DATE + groupSuffix));
            group.setPhoto(resultSet.getBytes(PHOTO + groupSuffix));
            Account account = accountDao.getSuffixedViewRowMapper(accountSuffix).mapRow(resultSet, i);
            group.setOwner(account);
            return group;
        };
    }

    @Override
    public RowMapper<Group> getRowMapper() {
        return getSuffixedRowMapper(GROUP_ALIAS, ACCOUNT_ALIAS);
    }
}
