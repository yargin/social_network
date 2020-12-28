package com.getjavajob.training.yarginy.socialnetwork.dao.relationsdao.testtest;

import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.dialog.Dialog;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.testetst.AccountDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.testetst.DialogDao;

import javax.sql.DataSource;

public class AccountDialogs extends AbstractOneToManyDao<Account, Dialog> {
    private static final String FIRST_ACC_ALIAS = "a1";
    private static final String SECOND_ACC_ALIAS = "a2";
    private static final String DIALOG_ALIAS = "d";
    private final AccountDao accountDao;
    private final DialogDao dialogDao;

    public AccountDialogs(DataSource dataSource) {
        super(dataSource, new AccountDao(dataSource), new DialogDao(dataSource, new AccountDao(dataSource)));
        this.accountDao = new AccountDao(dataSource);
        this.dialogDao = new DialogDao(dataSource, accountDao);
    }

    @Override
    protected String getSelectManyQuery() {
        return "SELECT " + dialogDao.getFields(DIALOG_ALIAS) + ", " + accountDao.getViewFields(FIRST_ACC_ALIAS) +
                ", " + accountDao.getViewFields(SECOND_ACC_ALIAS) + " FROM " + dialogDao.getTable(DIALOG_ALIAS) +
                " JOIN " + accountDao.getTable(FIRST_ACC_ALIAS) + " ON d.first_id = a1.id JOIN " +
                accountDao.getTable(SECOND_ACC_ALIAS) + " ON d.second_id = a2.id WHERE " +
                dialogDao.getAltParameters(DIALOG_ALIAS);
    }

    @Override
    protected String getSelectOneQuery() {
        return null;
    }
}
