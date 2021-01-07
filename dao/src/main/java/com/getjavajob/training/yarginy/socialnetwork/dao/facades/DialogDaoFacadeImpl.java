package com.getjavajob.training.yarginy.socialnetwork.dao.facades;

import com.getjavajob.training.yarginy.socialnetwork.common.models.dialog.Dialog;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.Dao;
import com.getjavajob.training.yarginy.socialnetwork.dao.relationsdao.onetomany.OneToManyDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component("dialogDaoFacade")
public class DialogDaoFacadeImpl implements DialogDaoFacade {
    private final Dao<Dialog> dialogDao;
    private final OneToManyDao<Dialog> accountDialogsDao;

    @Autowired
    public DialogDaoFacadeImpl(Dao<Dialog> dialogDao, OneToManyDao<Dialog> accountDialogs) {
        this.dialogDao = dialogDao;
        this.accountDialogsDao = accountDialogs;
    }

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
