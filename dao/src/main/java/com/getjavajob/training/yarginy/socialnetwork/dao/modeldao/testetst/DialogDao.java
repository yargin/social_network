package com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.testetst;

import com.getjavajob.training.yarginy.socialnetwork.common.models.NullEntitiesFactory;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.dialog.Dialog;
import com.getjavajob.training.yarginy.socialnetwork.common.models.dialog.DialogImpl;
import com.getjavajob.training.yarginy.socialnetwork.dao.tables.AbstractTable;
import com.getjavajob.training.yarginy.socialnetwork.dao.tables.AccountsTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Types;

import static com.getjavajob.training.yarginy.socialnetwork.dao.tables.DialogsTable.*;

@Component("dialogDao")
public class DialogDao extends AbstractDao<Dialog> {
    private static final AbstractTable FIRST_ACCOUNT_TABLE = new AccountsTable("a1");
    private static final AbstractTable SECOND_ACCOUNT_TABLE = new AccountsTable("a2");
    private static final String SELECT_BY_ID = "SELECT d.id as idd, " + FIRST_ACCOUNT_TABLE.getViewFields() + ", " +
            SECOND_ACCOUNT_TABLE.getViewFields() + " FROM Dialogs as d JOIN accounts as a1 ON d.first_id =" +
            " a1.id JOIN accounts as a2 ON d.second_id = a2.id WHERE d.id = ?";
    private static final String SELECT_BY_ALT_KEY = "SELECT d.id as idd, " + FIRST_ACCOUNT_TABLE.getViewFields() + ", " +
            SECOND_ACCOUNT_TABLE.getViewFields() + " FROM " +
            TABLE + " as d JOIN accounts a1 ON d.first_id = a1.id JOIN accounts a2 ON d.second_id = a2.id " +
            "WHERE (a1.id = ? AND a2.id = ?) OR (a2.id  = ? AND a1.id = ?)";
    private static final String SELECT_ALL = "SELECT * FROM " + TABLE;
    private static final String DELETE_BY_ID = "DELETE FROM " + TABLE + " WHERE id = ?";
    private final AccountDao accountDao;

    @Autowired
    public DialogDao(DataSource dataSource, AccountDao accountDao) {
        super(dataSource, TABLE);
        this.accountDao = accountDao;
    }

    @Override
    public ResultSetExtractor<Dialog> getSuffixedViewExtractor(String suffix) {
        return resultSet -> {
            Dialog dialog = new DialogImpl();
            dialog.setId(resultSet.getLong(ID + suffix));
            Account firstAccount = accountDao.getViewExtractor("a1").extractData(resultSet);
            Account secondAccount = accountDao.getViewExtractor("a2").extractData(resultSet);
            dialog.setFirstAccount(firstAccount);
            dialog.setSecondAccount(secondAccount);
            return dialog;
        };
    }

    @Override
    public ResultSetExtractor<Dialog> getSuffixedExtractor(String suffix) {
        return getViewExtractor();
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
    protected Object[] getAltKeys(Dialog dialog) {
        Account firstAccount = dialog.getFirstAccount();
        Account secondAccount = dialog.getSecondAccount();
        return new Object[]{firstAccount.getId(), secondAccount.getId(), firstAccount.getId(), secondAccount.getId()};
    }

    @Override
    protected Object[] getPrimaryKeys(Dialog dialog) {
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
        ValuePlacer valuePlacer = new ValuePlacer(TABLE, new String[]{FIRST_ID, SECOND_ID});
        valuePlacer.addFieldIfDiffers(dialog::getFirstAccount, storedDialog::getFirstAccount, FIRST_ID, Types.BIGINT,
                Account::getId);
        valuePlacer.addFieldIfDiffers(dialog::getSecondAccount, storedDialog::getSecondAccount, FIRST_ID,
                Types.BIGINT, Account::getId);
        return valuePlacer;
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
    public Dialog getNullEntity() {
        return NullEntitiesFactory.getNullDialog();
    }

}
