package com.getjavajob.training.yarginy.socialnetwork.dao.relationsdao.testtest.onetomany;

import com.getjavajob.training.yarginy.socialnetwork.common.models.dialog.Dialog;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.testetst.AccountDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.testetst.DialogDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Objects;

@Repository
public class AccountDialogs extends AbstractOneToManyDao<Dialog> {
    private static final String FIRST_ACC_ALIAS = "a1";
    private static final String SECOND_ACC_ALIAS = "a2";
    private static final String DIALOG_ALIAS = "d";
    private final AccountDao accountDao;
    private final DialogDao dialogDao;
    private final JdbcTemplate template;

    @Autowired
    public AccountDialogs(JdbcTemplate template, AccountDao accountDao, DialogDao dialogDao) {
        this.accountDao = accountDao;
        this.dialogDao = dialogDao;
        this.template = template;
    }

    protected String getSelectManyQuery() {
        return "SELECT " + dialogDao.getFields(DIALOG_ALIAS) + ", " + accountDao.getViewFields(FIRST_ACC_ALIAS) +
                ", " + accountDao.getViewFields(SECOND_ACC_ALIAS) + " FROM " + dialogDao.getTable(DIALOG_ALIAS) +
                " JOIN " + accountDao.getTable(FIRST_ACC_ALIAS) + " ON d.first_id = a1.id JOIN " +
                accountDao.getTable(SECOND_ACC_ALIAS) + " ON d.second_id = a2.id WHERE d.first_id = ? OR d.second_id = ?";
    }

    private RowMapper<Dialog> getManyRowMapper() {
        return dialogDao.getSuffixedViewRowMapper(DIALOG_ALIAS, FIRST_ACC_ALIAS, SECOND_ACC_ALIAS);
    }

    @Override
    public Collection<Dialog> selectMany(long accountId) {
        String query = getSelectManyQuery();
        return template.query(query, getManyRowMapper(), accountId, accountId);
    }

    @Override
    public boolean relationExists(long accountId, long dialogId) {
        String query = "SELECT 1 FROM " + dialogDao.getTable(DIALOG_ALIAS) + " WHERE d.id = ? AND (d.first_id = ? OR " +
                "d.second_id = ?)";
        return Objects.equals(template.queryForObject(query, new Object[]{dialogId, accountId, accountId},
                ((resultSet, i) -> resultSet.getInt(1))), 1);
    }
}
