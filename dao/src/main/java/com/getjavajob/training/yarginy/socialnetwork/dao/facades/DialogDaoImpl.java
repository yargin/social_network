package com.getjavajob.training.yarginy.socialnetwork.dao.facades;

import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.dialog.Dialog;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.Dao;
import com.getjavajob.training.yarginy.socialnetwork.dao.relationsdao.onetomany.OneToManyDao;

import java.util.Collection;

import static com.getjavajob.training.yarginy.socialnetwork.dao.factories.AbstractDbFactory.getDbFactory;

public class DialogDaoImpl implements DialogDao {
    private final Dao<Dialog> dialogDao = getDbFactory().getDialogDao();
    private final OneToManyDao<Account, Dialog> accountDialogsDao = getDbFactory().getAccountDialogsDao();

    @Override
    public Dialog select(long id) {
        return dialogDao.select(id);
    }

    @Override
    public Dialog select(Dialog dialog) {
        return dialogDao.select(dialog);
    }

    @Override
    public Dialog getNullEntity() {
        return dialogDao.getNullEntity();
    }

    @Override
    public boolean create(Dialog dialog) {
        return dialogDao.create(dialog);
    }

    @Override
    public boolean update(Dialog dialog, Dialog storedDialog) {
        return dialogDao.update(dialog, storedDialog);
    }

    @Override
    public boolean delete(Dialog dialog) {
        return dialogDao.delete(dialog);
    }

    @Override
    public Collection<Dialog> selectAll() {
        return dialogDao.selectAll();
    }

    @Override
    public Collection<Dialog> selectDialogsByAccount(long accountId) {
        return accountDialogsDao.selectMany(accountId);
    }

    @Override
    public boolean isTalker(long dialogId, long accountId) {
        return accountDialogsDao.relationExists(dialogId, accountId);
    }
}
