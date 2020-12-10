package com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.dmls;

import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.AccountImpl;
import com.getjavajob.training.yarginy.socialnetwork.common.models.dialog.Dialog;
import com.getjavajob.training.yarginy.socialnetwork.common.models.dialog.DialogImpl;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.AbstractDml;
import com.getjavajob.training.yarginy.socialnetwork.dao.tables.AccountsTable;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import static com.getjavajob.training.yarginy.socialnetwork.common.models.NullEntitiesFactory.getNullDialog;
import static com.getjavajob.training.yarginy.socialnetwork.dao.tables.DialogsTable.*;
import static com.getjavajob.training.yarginy.socialnetwork.dao.utils.querybuilder.SqlQueryBuilder.buildQuery;

@Component("dialogDml")
public class DialogDml extends AbstractDml<Dialog> {
    private static final String FIRST_TABLE_ALIAS = "a1";
    private static final String SECOND_TABLE_ALIAS = "a2";
    private static final String SELECT_ALL = buildQuery().selectAll(TABLE).build();

    private static final String SELECT_BY_ID = "SELECT " + ID + ", " + AccountsTable.getViewFieldsWithAlias("a1") + ", " +
            AccountsTable.getViewFieldsWithAlias("a2") + " FROM Dialogs JOIN Accounts a1 ON Dialogs.first_id = a1.id " +
            "JOIN Accounts as a2 ON Dialogs.second_id = a2.id WHERE Dialogs.id = ? ";
    private static final String SELECT_BY_ALT_KEY = "SELECT " + ID + ", " + AccountsTable.getViewFieldsWithAlias("a1") +
            ", " + AccountsTable.getViewFieldsWithAlias("a2") + " FROM " + TABLE + " JOIN " + AccountsTable.TABLE +
            " a1 ON " + FIRST_ID + " = a1.id JOIN " + AccountsTable.TABLE + " a2 ON " + SECOND_ID + " = a2.id WHERE (" +
            FIRST_ID + " = ? AND " + SECOND_ID + " = ?) OR (" + SECOND_ID + "  = ? AND " + FIRST_ID + " = ?)";

    private static final String SELECT_UPDATE_BY_ID = buildQuery().selectAll(TABLE).where(ID).build();
    private static final String SELECT_UPDATE_BY_ALT_KEY = "SELECT * FROM " + TABLE + " WHERE (" + FIRST_ID +
            " = ? AND " + SECOND_ID + " = ?) OR (" + SECOND_ID + "  = ? AND " + FIRST_ID + " = ?)";

    @Override
    protected String getSelectById() {
        return SELECT_BY_ID;
    }

    @Override
    protected String getSelectAll() {
        return SELECT_ALL;
    }

    @Override
    protected String getSelectByAltKey() {
        return SELECT_BY_ALT_KEY;
    }

    @Override
    protected String getSelectForUpdateByAltKey() {
        return SELECT_UPDATE_BY_ALT_KEY;
    }

    @Override
    protected String getSelectForUpdateById() {
        return SELECT_UPDATE_BY_ID;
    }

    @Override
    protected void setAltKeyParams(PreparedStatement statement, Dialog entity) throws SQLException {
        statement.setLong(1, entity.getFirstAccount().getId());
        statement.setLong(2, entity.getSecondAccount().getId());
        statement.setLong(3, entity.getFirstAccount().getId());
        statement.setLong(4, entity.getSecondAccount().getId());
    }

    @Override
    public Dialog retrieveFromRow(ResultSet resultSet) throws SQLException {
        return retrieveViewFromRow(resultSet);
    }

    @Override
    public Dialog retrieveViewFromRow(ResultSet resultSet) throws SQLException {
        Account firstAccount = getAccount(resultSet, FIRST_TABLE_ALIAS);
        Account secondAccount = getAccount(resultSet, SECOND_TABLE_ALIAS);

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
    public Collection<Dialog> retrieveEntities(ResultSet resultSet) throws SQLException {
        Collection<Dialog> dialogs = new ArrayList<>();
        while (resultSet.next()) {
            dialogs.add(retrieveViewFromRow(resultSet));
        }
        return dialogs;
    }

    @Override
    public void updateRow(ResultSet resultSet, Dialog entity, Dialog storedEntity) throws SQLException {
        updateFieldIfDiffers(entity::getId, storedEntity::getId, resultSet::updateLong, ID);
        updateFieldIfDiffers(entity::getFirstAccount, storedEntity::getFirstAccount, resultSet::updateLong, FIRST_ID,
                Account::getId);
        updateFieldIfDiffers(entity::getSecondAccount, storedEntity::getSecondAccount, resultSet::updateLong, SECOND_ID,
                Account::getId);
    }

    @Override
    public Dialog getNullEntity() {
        return getNullDialog();
    }
}
