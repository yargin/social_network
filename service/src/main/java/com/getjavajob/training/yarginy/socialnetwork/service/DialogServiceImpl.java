package com.getjavajob.training.yarginy.socialnetwork.service;

import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.AccountImpl;
import com.getjavajob.training.yarginy.socialnetwork.common.models.dialog.Dialog;
import com.getjavajob.training.yarginy.socialnetwork.common.models.dialog.DialogImpl;
import com.getjavajob.training.yarginy.socialnetwork.common.models.message.Message;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.DialogDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.DialogDaoImpl;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.TransactionManager;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.TransactionManagerImpl;
import com.getjavajob.training.yarginy.socialnetwork.dao.factories.connectionpool.Transaction;
import com.getjavajob.training.yarginy.socialnetwork.service.messages.DialogMessageServiceImpl;
import com.getjavajob.training.yarginy.socialnetwork.service.messages.MessageService;

public class DialogServiceImpl implements DialogService {
    private final TransactionManager transactionManager = new TransactionManagerImpl();
    private final DialogDao dialogDao = new DialogDaoImpl();
    private final MessageService messageService = new DialogMessageServiceImpl();

    @Override
    public Dialog get(long dialogId) {
        return dialogDao.select(dialogId);
    }

    @Override
    public Dialog get(Dialog dialog) {
        return dialogDao.select(dialog);
    }

    @Override
    public boolean create(Dialog dialog, Message message) {
        try (Transaction transaction = transactionManager.getTransaction()) {
            if (!dialogDao.create(dialog)) {
                transaction.rollback();
                throw new IllegalStateException();
            }
            Dialog createdDialog = dialogDao.select(dialog);
            message.setReceiverId(createdDialog.getId());
            if (!messageService.addMessage(message)) {
                transaction.rollback();
                throw new IllegalStateException();
            }
            transaction.commit();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean delete(Dialog dialog) {
        return false;
    }

    @Override
    public Dialog getByTalkers(long firstAccountId, long secondAccountId) {
        Dialog dialog = new DialogImpl();
        Account firstAccount = new AccountImpl();
        firstAccount.setId(firstAccountId);
        dialog.setFirstAccount(firstAccount);
        Account secondAccount = new AccountImpl();
        secondAccount.setId(secondAccountId);
        dialog.setSecondAccount(secondAccount);
        return dialogDao.select(dialog);
    }

    @Override
    public boolean isTalker(long accountId, long dialogId) {
        return dialogDao.isTalker(accountId, dialogId);
    }

    @Override
    public Dialog getNullDialog() {
        return dialogDao.getNullEntity();
    }
}
