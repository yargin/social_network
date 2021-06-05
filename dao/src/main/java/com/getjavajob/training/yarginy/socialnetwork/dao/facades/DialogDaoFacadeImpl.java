package com.getjavajob.training.yarginy.socialnetwork.dao.facades;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.Dialog;
import com.getjavajob.training.yarginy.socialnetwork.common.utils.NullModelsFactory;
import com.getjavajob.training.yarginy.socialnetwork.dao.repositories.DialogDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.utils.TransactionPerformer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Collection;

import static java.util.Objects.isNull;

@Component("dialogDaoFacade")
public class DialogDaoFacadeImpl implements DialogDaoFacade {
    private final DialogDao dialogDao;
    private final TransactionPerformer transactionPerformer;

    public DialogDaoFacadeImpl(@Qualifier("dialogRepo") DialogDao dialogDao, TransactionPerformer transactionPerformer) {
        this.dialogDao = dialogDao;
        this.transactionPerformer = transactionPerformer;
    }

    @Override
    public Dialog select(long id) {
        return dialogDao.findById(id).orElseGet(this::getNullDialog);
    }

    @Override
    public Dialog select(Dialog dialog) {
        orderAccountsById(dialog);
        Dialog selectedDialog = dialogDao.findByFirstAccountAndSecondAccount(dialog.getFirstAccount(),
                dialog.getSecondAccount());
        return isNull(selectedDialog) ? getNullDialog() : selectedDialog;
    }

    private void orderAccountsById(Dialog dialog) {
        Account firstAccount = dialog.getFirstAccount();
        Account secondAccount = dialog.getSecondAccount();
        if (isNull(firstAccount) || isNull(secondAccount)) {
            throw new IllegalArgumentException();
        }
        Account greaterAccount;
        if (firstAccount.getId() < secondAccount.getId()) {
            greaterAccount = secondAccount;
            dialog.setSecondAccount(dialog.getFirstAccount());
            dialog.setFirstAccount(greaterAccount);
        }
    }

    @Override
    public Dialog getNullDialog() {
        return NullModelsFactory.getNullDialog();
    }

    @Override
    public boolean create(Dialog dialog) {
        orderAccountsById(dialog);
        return transactionPerformer.repoPerformCreate(dialog, dialogDao);
    }

    @Override
    public boolean update(Dialog dialog) {
        orderAccountsById(dialog);
        return transactionPerformer.repoPerformUpdateOrDelete(dialog, dialogDao, dialogDao::save);
    }

    @Override
    public boolean delete(Dialog dialog) {
        return transactionPerformer.repoPerformUpdateOrDelete(dialog, dialogDao, dialogDao::delete);
    }

    @Override
    public Collection<Dialog> selectAll() {
        return dialogDao.findAll();
    }

    @Override
    public Collection<Dialog> selectDialogsByAccount(long accountId) {
        Account account = new Account(accountId);
        return dialogDao.findByFirstAccountOrSecondAccount(account, account);
    }

    @Override
    public boolean isTalker(long dialogId, long accountId) {
        Account account = new Account(accountId);
        return !isNull(dialogDao.selectByIdAndTalker(dialogId, account, account));
    }
}
