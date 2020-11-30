package com.getjavajob.training.yarginy.socialnetwork.dao.relationsdao.onetomany.dmls;

import com.getjavajob.training.yarginy.socialnetwork.common.models.dialog.Dialog;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.Dml;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.dmls.DialogDml;
import com.getjavajob.training.yarginy.socialnetwork.dao.relationsdao.onetomany.OneToManyDml;
import com.getjavajob.training.yarginy.socialnetwork.dao.tables.AccountsTable;
import com.getjavajob.training.yarginy.socialnetwork.dao.tables.DialogsTable;

public class AccountDialogsDml extends OneToManyDml<Dialog> {
    private static final String SELECT_BY_BOTH = "SELECT " + AccountsTable.getViewFieldsWithAlias("a1") + ", " +
            AccountsTable.getViewFieldsWithAlias("a2") + " FROM Dialogs JOIN Accounts a1 ON a1.Id = Dialogs.first_id " +
            " JOIN Accounts a2 ON Dialogs.second_id = a2.ID WHERE Dialogs.id = ? AND (a1.Id = ? OR a2.Id = ?) ";
    private static final String SELECT_BY_ONE = "SELECT Dialogs.id, " + AccountsTable.getViewFieldsWithAlias("a1") + ", " +
            AccountsTable.getViewFieldsWithAlias("a2") + " FROM Dialogs JOIN Accounts a1 ON " + DialogsTable.FIRST_ID +
            " = a1.id JOIN Accounts a2 ON " + DialogsTable.SECOND_ID + " = a2.id WHERE " + DialogsTable.FIRST_ID +
            " = ?  OR " + DialogsTable.SECOND_ID + " = ?";

    @Override
    protected String getSelectByBothQuery() {
        return SELECT_BY_BOTH;
    }

    @Override
    protected String getSelectByOneQuery() {
        return SELECT_BY_ONE;
    }

    @Override
    protected Dml<Dialog> getManyDml() {
        return new DialogDml();
    }
}
