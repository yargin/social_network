package com.getjavajob.training.yarginy.socialnetwork.dao.facades;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Dialog;
import com.getjavajob.training.yarginy.socialnetwork.common.utils.TransactionPerformer;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldaos.implementations.DialogDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.relationdaos.onetomany.implementations.AccountDialogsDao;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component("dialogDaoFacade")
public class DialogDaoFacadeImpl implements DialogDaoFacade {
    private final DialogDao dialogDao;
    private final AccountDialogsDao accountDialogsDao;
    private final TransactionPerformer transactionPerformer;

    public DialogDaoFacadeImpl(DialogDao dialogDao, AccountDialogsDao accountDialogsDao, TransactionPerformer transactionPerformer) {
        this.dialogDao = dialogDao;
        this.accountDialogsDao = accountDialogsDao;
        this.transactionPerformer = transactionPerformer;
    }

    @Override
    public Dialog select(long id) {
        return dialogDao.select(id);
    }

    @Override
    public Dialog selectFullInfo(long id) {
        return dialogDao.selectFullInfo(id);
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
