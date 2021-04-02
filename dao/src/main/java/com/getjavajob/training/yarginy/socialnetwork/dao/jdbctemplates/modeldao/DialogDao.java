package com.getjavajob.training.yarginy.socialnetwork.dao.jdbctemplates.modeldao;

import com.getjavajob.training.yarginy.socialnetwork.common.exceptions.DataFlowViolationException;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.dialog.Dialog;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.Types;

import static com.getjavajob.training.yarginy.socialnetwork.common.models.NullModelsFactory.getNullDialog;

@Repository("dialogDao")
public class DialogDao extends AbstractDao<Dialog> {
    private static final String TABLE = "dialogs";
    private static final String ALIAS = "d";
    private static final String FIRST_ACC_ALIAS = "a1";
    private static final String SECOND_ACC_ALIAS = "a2";
    private static final String ID = "id";
    private static final String FIRST_ID = "first_id";
    private static final String SECOND_ID = "second_id";
    private final String selectAll;
    private final AccountDao accountDao;

    public DialogDao(JdbcTemplate template, SimpleJdbcInsert jdbcInsert, NamedParameterJdbcTemplate namedTemplate,
                     AccountDao accountDao) {
        super(template, jdbcInsert, namedTemplate, TABLE, ALIAS);
        this.accountDao = accountDao;
        selectAll = "SELECT " + getFields(ALIAS) + ", " + accountDao.getFields(FIRST_ACC_ALIAS) + ", " +
                accountDao.getFields(SECOND_ACC_ALIAS) + " FROM " + getTable(ALIAS) + " LEFT JOIN " +
                accountDao.getTable(FIRST_ACC_ALIAS) + " ON d.first_id = a1.id LEFT JOIN " +
                accountDao.getTable(SECOND_ACC_ALIAS) + " ON d.second_id = a2.id";
    }

    @Override
    protected String[] getFieldsList() {
        return getViewFieldsList();
    }

    @Override
    protected String[] getViewFieldsList() {
        return new String[]{ID, FIRST_ID, SECOND_ID};
    }

    @Override
    public String[] getPrimaryKeys() {
        return new String[]{ID};
    }

    @Override
    public String[] getAltKeys() {
        return new String[]{FIRST_ID, SECOND_ID};
    }

    public RowMapper<Dialog> getSuffixedViewRowMapper(String dialogSuffix, String firstAccountSuffix,
                                                      String secondAccountSuffix) {
        return (resultSet, i) -> {
            Dialog dialog = new Dialog();
            try {
                dialog.setId(resultSet.getLong(ID + dialogSuffix));
            } catch (DataFlowViolationException e) {
                return getNullModel();
            }
            Account firstAccount = accountDao.getSuffixedViewRowMapper(firstAccountSuffix).mapRow(resultSet, i);
            Account secondAccount = accountDao.getSuffixedViewRowMapper(secondAccountSuffix).mapRow(resultSet, i);
            dialog.setFirstAccount(firstAccount);
            dialog.setSecondAccount(secondAccount);
            return dialog;
        };
    }

    public RowMapper<Dialog> getSuffixedRowMapper(String dialogSuffix, String firstAccountSuffix,
                                                  String secondAccountSuffix) {
        return getSuffixedViewRowMapper(dialogSuffix, firstAccountSuffix, secondAccountSuffix);
    }

    @Override
    public RowMapper<Dialog> getViewRowMapper() {
        return getSuffixedViewRowMapper(ALIAS, FIRST_ACC_ALIAS, SECOND_ACC_ALIAS);
    }

    @Override
    public RowMapper<Dialog> getRowMapper() {
        return getSuffixedRowMapper(ALIAS, FIRST_ACC_ALIAS, SECOND_ACC_ALIAS);
    }

    @Override
    protected Object[] getObjectAltKeys(Dialog dialog) {
        Account firstAccount = dialog.getFirstAccount();
        Account secondAccount = dialog.getSecondAccount();
        long firstId = firstAccount.getId();
        long secondId = secondAccount.getId();
        return firstId < secondId ? new Object[]{firstId, secondId} : new Object[]{secondId, firstId};
    }

    @Override
    protected Object[] getObjectPrimaryKeys(Dialog dialog) {
        return new Object[]{dialog.getId()};
    }

    @Override
    protected MapSqlParameterSource createEntityFieldsMap(Dialog dialog) {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue(ID, dialog.getId(), Types.BIGINT);
        long firstId = dialog.getFirstAccount().getId();
        long secondId = dialog.getSecondAccount().getId();
        if (firstId < secondId) {
            parameters.addValue(FIRST_ID, firstId, Types.BIGINT);
            parameters.addValue(SECOND_ID, secondId, Types.BIGINT);
        } else {
            parameters.addValue(FIRST_ID, secondId, Types.BIGINT);
            parameters.addValue(SECOND_ID, firstId, Types.BIGINT);
        }
        return parameters;
    }

    @Override
    protected UpdateValuesPlacer getValuePlacer(Dialog dialog, Dialog storedDialog) {
        UpdateValuesPlacer valuesPlacer = new UpdateValuesPlacer(TABLE);
        Account firstAccount = dialog.getFirstAccount();
        Account secondAccount = dialog.getSecondAccount();
        if (firstAccount.getId() > secondAccount.getId()) {
            dialog.setSecondAccount(firstAccount);
            dialog.setFirstAccount(secondAccount);
        }
        valuesPlacer.addFieldIfDiffers(dialog::getFirstAccount, storedDialog::getFirstAccount, FIRST_ID, Types.BIGINT,
                Account::getId);
        valuesPlacer.addFieldIfDiffers(dialog::getSecondAccount, storedDialog::getSecondAccount, FIRST_ID,
                Types.BIGINT, Account::getId);

        valuesPlacer.addKey(storedDialog::getId, ID, Types.BIGINT);
        return valuesPlacer;
    }

    @Override
    protected String getSelectAllQuery() {
        return selectAll;
    }

    @Override
    public Dialog getNullModel() {
        return getNullDialog();
    }

}
