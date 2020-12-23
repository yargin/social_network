package com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.testetst;

import com.getjavajob.training.yarginy.socialnetwork.common.models.NullEntitiesFactory;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.group.Group;
import com.getjavajob.training.yarginy.socialnetwork.common.models.group.GroupImpl;
import com.getjavajob.training.yarginy.socialnetwork.dao.tables.AbstractTable;
import com.getjavajob.training.yarginy.socialnetwork.dao.tables.AccountsTable;
import com.getjavajob.training.yarginy.socialnetwork.dao.tables.GroupsTable;
import com.sun.org.apache.bcel.internal.generic.TABLESWITCH;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

import java.sql.Types;

import static com.getjavajob.training.yarginy.socialnetwork.dao.tables.GroupsTable.*;

@Component("groupDao")
public class GroupDao extends AbstractDao<Group> {
    private static final AbstractTable table = new GroupsTable("gr");
    private static final AbstractTable accountsTable = new AccountsTable("acc");
//    private static final String SELECT_BY_ID = "SELECT "+ table.getFields() + ", " + accountsTable.getViewFields() +
//            " FROM _groups as gr JOIN accounts as acc ON gr.owner_id = acc.id WHERE gr.id = ?;";
    private static final String SELECT_BY_ID = "SELECT gr.id as idgr, gr.name as namegr,\n" +
            "       gr.description as descriptiongr,\n" +
            "       gr.owner_id as owner_idgr,\n" +
            "       gr.creation_date as creation_dategr,\n" +
            "       gr.photo as photogr, acc.id as idacc,\n" +
            "       acc.name as nameacc, acc.surname as surnameacc,\n" +
            "       acc.email as emailacc\n" +
            "FROM _groups as gr\n" +
            "    JOIN accounts as acc ON gr.owner_id = acc.id\n" +
            "WHERE gr.name = 'USSR fans'";

    private static final String SELECT_BY_ALT_KEY = "SELECT "+ table.getFields() + ", " + accountsTable.getViewFields() +
            " FROM _groups as gr JOIN accounts as acc ON gr.owner_id = acc.id WHERE gr.name = ?;";
    private static final String SELECT_ALL = "SELECT "+ table.getFields() + ", " + accountsTable.getViewFields() +
            " FROM _groups as gr JOIN accounts as acc ON gr.owner_id = acc.id;";
    private static final String DELETE_BY_ID = "DELETE FROM _groups WHERE id = ?;";
    private final AccountDao accountDao;

    public GroupDao(DataSource dataSource) {
        super(dataSource, TABLE);
        accountDao = new AccountDao(dataSource);
    }

    @Override
    public Group getNullEntity() {
        return NullEntitiesFactory.getNullGroup();
    }

    @Override
    protected String getSelectByPKeyQuery() {
        return SELECT_BY_ID;
    }

    @Override
    protected String getSelectByAltKeysQuery() {
        return SELECT_BY_ALT_KEY;
    }

    @Override
    protected Object[] getAltKeys(Group group) {
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
    protected ValuePlacer getValuePlacer(Group group, Group storedGroup) {
        ValuePlacer placer = new ValuePlacer(TABLE, new String[]{NAME});
        placer.addFieldIfDiffers(group::getName, storedGroup::getName, NAME, Types.VARCHAR);
        placer.addFieldIfDiffers(group::getDescription, storedGroup::getDescription, DESCRIPTION, Types.VARCHAR);
        placer.addFieldIfDiffers(group::getOwner, storedGroup::getOwner, OWNER, Types.VARCHAR, Account::getId);
        placer.addFieldIfDiffers(group::getCreationDate, storedGroup::getCreationDate, CREATION_DATE, Types.DATE);
        placer.addFieldIfDiffers(group::getPhoto, storedGroup::getPhoto, PHOTO, Types.BLOB);

        placer.addKey(group::getName, NAME, Types.VARCHAR);
        return placer;
    }

    @Override
    protected Object[] getPrimaryKeys(Group group) {
        return new Object[]{group.getId()};
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
    public ResultSetExtractor<Group> getSuffixedViewExtractor(String suffix) {
        return resultSet -> {
            Group group = new GroupImpl();
            group.setId(resultSet.getLong(ID + suffix));
            group.setName(resultSet.getString(NAME + suffix));
            return group;
        };
    }

    @Override
    public ResultSetExtractor<Group> getSuffixedExtractor(String suffix) {
        return resultSet -> {
            Group group = getSuffixedViewExtractor(suffix).extractData(resultSet);
            group.setDescription(resultSet.getString(DESCRIPTION + suffix));
            group.setCreationDate(resultSet.getDate(CREATION_DATE + suffix));
            group.setPhoto(resultSet.getBytes(PHOTO + suffix));
            Account account = accountDao.getViewExtractor("acc").extractData(resultSet);
            group.setOwner(account);
            return group;
        };
    }
}
