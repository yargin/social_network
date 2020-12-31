package com.getjavajob.training.yarginy.socialnetwork.dao.relationsdao.testtest;

import com.getjavajob.training.yarginy.socialnetwork.common.models.dialog.Dialog;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.testetst.AccountDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.testetst.DialogDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Collection;

@Repository
public class AccountDialogs extends AbstractOneToManyDao<Dialog> {
    private static final String FIRST_ACC_ALIAS = "a1";
    private static final String SECOND_ACC_ALIAS = "a2";
    private static final String DIALOG_ALIAS = "d";
    private final AccountDao accountDao;
    private final DialogDao dialogDao;

    @Autowired
    public AccountDialogs(DataSource dataSource, AccountDao accountDao, DialogDao dialogDao) {
        super(dataSource);
        this.accountDao = accountDao;
        this.dialogDao = dialogDao;
    }

    private String getSelectManyQuery() {
        return "SELECT " + dialogDao.getFields(DIALOG_ALIAS) + ", " + accountDao.getViewFields(FIRST_ACC_ALIAS) +
                ", " + accountDao.getViewFields(SECOND_ACC_ALIAS) + " FROM " + dialogDao.getTable(DIALOG_ALIAS) +
                " JOIN " + accountDao.getTable(FIRST_ACC_ALIAS) + " ON d.first_id = a1.id JOIN " +
                accountDao.getTable(SECOND_ACC_ALIAS) + " ON d.second_id = a2.id WHERE d.first_id = ? OR d.second_id = ?";
    }

    @Override
    public Collection<Dialog> selectMany(long accountId) {
        String query = getSelectManyQuery();
        return template.query(query, dialogDao.getSuffixedViewRowMapper(DIALOG_ALIAS, FIRST_ACC_ALIAS, SECOND_ACC_ALIAS),
                accountId, accountId);
    }

    @Override
    protected String getSelectByBothQuery() {
        return "SELECT 1 FROM " + dialogDao.getTable(DIALOG_ALIAS) + " WHERE d.id = ? AND (d.first_id = ? OR d.second_id = ?)";
    }

    @Override
    protected Object[] getBothParams(long accountId, long dialogId) {
        return new Object[]{dialogId, accountId, accountId};
    }
}
