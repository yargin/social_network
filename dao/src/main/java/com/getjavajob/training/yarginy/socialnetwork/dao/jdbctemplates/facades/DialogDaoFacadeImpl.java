package com.getjavajob.training.yarginy.socialnetwork.dao.jdbctemplates.facades;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Dialog;
import com.getjavajob.training.yarginy.socialnetwork.dao.jdbctemplates.modeldao.Dao;
import com.getjavajob.training.yarginy.socialnetwork.dao.jdbctemplates.relationsdao.onetomany.OneToManyDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.jpa.JpaDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.jpa.relationsdao.onetomany.JpaOneToManyDao;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component("dialogDaoFacade")
public class DialogDaoFacadeImpl implements DialogDaoFacade {
    private final JpaDao<Dialog> dialogDao;
    private final JpaOneToManyDao<Dialog> accountDialogsDao;

    public DialogDaoFacadeImpl(@Qualifier("jpaDialogDao") JpaDao<Dialog> dialogDao,
                               JpaOneToManyDao<Dialog> accountDialogs) {
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
    public Dialog getNullModel() {
        return dialogDao.getNullModel();
    }

    @Override
    public boolean create(Dialog dialog) {
        return dialogDao.create(dialog);
    }

    @Override
    public boolean update(Dialog dialog, Dialog storedDialog) {
        return dialogDao.update(dialog);
//        return dialogDao.update(dialog, storedDialog);
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
