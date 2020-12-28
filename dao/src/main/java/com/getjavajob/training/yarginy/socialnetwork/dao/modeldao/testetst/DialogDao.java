package com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.testetst;

import com.getjavajob.training.yarginy.socialnetwork.common.models.NullEntitiesFactory;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.dialog.Dialog;
import com.getjavajob.training.yarginy.socialnetwork.common.models.dialog.DialogImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Types;

@Component("dialogDao")
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

    @Autowired
    public DialogDao(DataSource dataSource, AccountDao accountDao) {
        super(dataSource, TABLE, ALIAS, true);
        this.accountDao = accountDao;
        selectAll = "SELECT " + getFields(ALIAS) + ", " + accountDao.getFields(FIRST_ACC_ALIAS) + ", " +
                accountDao.getFields(SECOND_ACC_ALIAS) + " FROM " + getTable(ALIAS) + " JOIN " +
                accountDao.getTable(FIRST_ACC_ALIAS) + "ON d.first_id = a1.id JOIN " +
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
            Dialog dialog = new DialogImpl();
            dialog.setId(resultSet.getLong(ID + dialogSuffix));
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
    protected Object[] getObjectsAltKeys(Dialog dialog) {
        Account firstAccount = dialog.getFirstAccount();
        Account secondAccount = dialog.getSecondAccount();
        return new Object[]{firstAccount.getId(), secondAccount.getId(), firstAccount.getId(), secondAccount.getId()};
    }

    @Override
    protected Object[] getObjectPrimaryKeys(Dialog dialog) {
        return new Object[]{dialog.getId()};
    }

    @Override
    protected MapSqlParameterSource createEntityFieldsMap(Dialog dialog) {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue(ID, dialog.getId(), Types.BIGINT);
        Account account = dialog.getFirstAccount();
        parameters.addValue(FIRST_ID, account.getId(), Types.BIGINT);
        account = dialog.getSecondAccount();
        parameters.addValue(SECOND_ID, account.getId(), Types.BIGINT);
        return parameters;
    }

    @Override
    protected ValuePlacer getValuePlacer(Dialog dialog, Dialog storedDialog) {
        ValuePlacer valuePlacer = new ValuePlacer(TABLE, getAltKeys());
        valuePlacer.addFieldIfDiffers(dialog::getFirstAccount, storedDialog::getFirstAccount, FIRST_ID, Types.BIGINT,
                Account::getId);
        valuePlacer.addFieldIfDiffers(dialog::getSecondAccount, storedDialog::getSecondAccount, FIRST_ID,
                Types.BIGINT, Account::getId);
        return valuePlacer;
    }

    @Override
    protected String getSelectAllQuery() {
        return selectAll;
    }

    @Override
    public Dialog getNullEntity() {
        return NullEntitiesFactory.getNullDialog();
    }

}
