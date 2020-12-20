package com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.testetst.newnew.models.modeldaos.handlers;

import com.getjavajob.training.yarginy.socialnetwork.common.models.NullEntitiesFactory;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.AccountImpl;
import com.getjavajob.training.yarginy.socialnetwork.common.models.dialog.Dialog;
import com.getjavajob.training.yarginy.socialnetwork.common.models.dialog.DialogImpl;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.testetst.newnew.Initializer;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.testetst.newnew.models.modeldaos.DaoFieldsHandler;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.testetst.valuesplacer.queryandparamplacer.InsertQueryAndParamPlacer;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.testetst.valuesplacer.queryandparamplacer.QueryAndParamPlacer;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.testetst.valuesplacer.queryandparamplacer.UpdateQueryAndParamPlacer;
import com.getjavajob.training.yarginy.socialnetwork.dao.tables.AccountsTable;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import static com.getjavajob.training.yarginy.socialnetwork.dao.tables.DialogsTable.*;

@Component("dialogDaoHandler")
public class DialogDaoHandler implements DaoFieldsHandler<Dialog> {
    private static final String SELECT_BY_ID = "SELECT " + TABLE + '.' + ID + ", " +
            AccountsTable.getViewFieldsWithAlias("a1") + ", " + AccountsTable.getViewFieldsWithAlias("a2") +
            " FROM Dialogs JOIN accounts a1 ON Dialogs.first_id = a1.id JOIN accounts as a2 ON Dialogs.second_id = " +
            "a2.id WHERE dialogs.id = :id ";
    private static final String SELECT_BY_ALT_KEY = "SELECT " + TABLE + '.' + ID + ", " +
            AccountsTable.getViewFieldsWithAlias("a1") + ", " + AccountsTable.getViewFieldsWithAlias("a2") + " FROM " +
            TABLE + " JOIN accounts a1 ON " + FIRST_ID + " = a1.id JOIN accounts a2 ON " + SECOND_ID + " = a2.id WHERE (" +
            FIRST_ID + " = :first_id AND " + SECOND_ID + " = :second_id) OR (" + SECOND_ID + "  = :first_id AND " +
            FIRST_ID + " = :second_id)";
    private static final String SELECT_ALL = "SELECT * FROM " + TABLE;
    private static final String DELETE_DIALOG = "DELETE FROM " + TABLE + " WHERE id = :id";

    @Override
    public String getSelectByIdQuery() {
        return SELECT_BY_ID;
    }

    @Override
    public String getSelectByAltKeyQuery() {
        return SELECT_BY_ALT_KEY;
    }

    @Override
    public String getSelectAllQuery() {
        return SELECT_ALL;
    }

    @Override
    public String getDeleteQuery() {
        return DELETE_DIALOG;
    }

    @Override
    public QueryAndParamPlacer getInsertQueryAndParameters(Dialog dialog) {
        return new InsertQueryAndParamPlacer<>(TABLE, new DialogInitializer(), dialog);
    }

    @Override
    public QueryAndParamPlacer getUpdateQueryAndParameters(Dialog dialog, Dialog storedDialog) {
        return new UpdateQueryAndParamPlacer<>(TABLE, new DialogInitializer(), dialog, storedDialog, new String[]{ID});
    }

    @Override
    public Dialog mapRow(ResultSet resultSet, int i) throws SQLException {
        return mapViewRow(resultSet, i);
    }

    @Override
    public Dialog mapViewRow(ResultSet resultSet, int i) throws SQLException {
        Account firstAccount = getAccount(resultSet, "a1");
        Account secondAccount = getAccount(resultSet, "a2");

        Dialog dialog = new DialogImpl();
        dialog.setId(resultSet.getLong(ID));
        dialog.setFirstAccount(firstAccount);
        dialog.setSecondAccount(secondAccount);
        return dialog;
    }

    private Account getAccount(ResultSet resultSet, String alias) throws SQLException {
        Account secondAccount = new AccountImpl();
        secondAccount.setId(resultSet.getLong("id" + alias));
        secondAccount.setName(resultSet.getString("name" + alias));
        secondAccount.setSurname(resultSet.getString("surname" + alias));
        secondAccount.setEmail(resultSet.getString("email" + alias));
        return secondAccount;
    }

    @Override
    public MapSqlParameterSource getAltKeyParameter(Dialog dialog) {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue(FIRST_ID, dialog.getFirstAccount().getId(), Types.BIGINT);
        parameters.addValue(SECOND_ID, dialog.getSecondAccount().getId(), Types.BIGINT);
        return parameters;
    }

    public MapSqlParameterSource getPKeyParameter(Dialog dialog) {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue(ID, dialog.getId(), Types.BIGINT);
        return parameters;
    }

    @Override
    public Dialog getNullEntity() {
        return NullEntitiesFactory.getNullDialog();
    }

    @Override
    public String getTableName() {
        return TABLE;
    }

    static class DialogInitializer extends Initializer<Dialog> {
        @Override
        public void initUpdateParams(Dialog dialog, Dialog storedDialog) {
            placer.placeValueIfDiffers(dialog::getFirstAccount, storedDialog::getFirstAccount, FIRST_ID, Types.BIGINT,
                    Account::getId);
            placer.placeValueIfDiffers(dialog::getSecondAccount, storedDialog::getSecondAccount, SECOND_ID, Types.BIGINT,
                    Account::getId);
        }

        @Override
        public void initInsertParams(Dialog dialog) {
            placer.placeValue(dialog::getFirstAccount, FIRST_ID, Types.BIGINT, Account::getId);
            placer.placeValue(dialog::getSecondAccount, SECOND_ID, Types.BIGINT, Account::getId);
        }

        @Override
        public void setPKey(Dialog storedDialog) {
            placer.placeKey(storedDialog::getId, ID, Types.BIGINT);
        }
    }
}
