package com.getjavajob.training.yarginy.socialnetwork.dao.facades;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Dialog;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.utils.TransactionPerformer;
import com.getjavajob.training.yarginy.socialnetwork.dao.newdaos.modeldaos.implementations.NewDialogDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.newdaos.relationdaos.onetomany.implementations.NewAccountDialogsDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component("dialogDaoFacade")
public class DialogDaoFacadeImpl implements DialogDaoFacade {
    private NewDialogDao dialogDao;
    private NewAccountDialogsDao accountDialogsDao;
    private TransactionPerformer transactionPerformer;

    @Autowired
    public void setDialogDao(NewDialogDao dialogDao) {
        this.dialogDao = dialogDao;
    }

    @Autowired
    public void setAccountDialogsDao(NewAccountDialogsDao accountDialogsDao) {
        this.accountDialogsDao = accountDialogsDao;
    }

    @Autowired
    public void setTransactionPerformer(TransactionPerformer transactionPerformer) {
        this.transactionPerformer = transactionPerformer;
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
        return transactionPerformer.transactionPerformed(dialogDao::create, dialog);
    }

    @Override
    public boolean update(Dialog dialog) {
        return transactionPerformer.transactionPerformed(dialogDao::update, dialog);
    }

    @Override
    public boolean delete(Dialog dialog) {
        return transactionPerformer.transactionPerformed(dialogDao::delete, dialog);
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
