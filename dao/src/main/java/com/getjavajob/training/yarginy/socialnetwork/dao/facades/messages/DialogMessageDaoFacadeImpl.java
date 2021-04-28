package com.getjavajob.training.yarginy.socialnetwork.dao.facades.messages;

import com.getjavajob.training.yarginy.socialnetwork.common.models.messages.DialogMessage;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.utils.TransactionPerformer;
import com.getjavajob.training.yarginy.socialnetwork.dao.newdaos.modeldaos.implementations.messages.NewDialogMessageDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.newdaos.relationdaos.onetomany.implementations.messages.NewDialogMessagesDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component("dialogMessageDaoFacade")
public class DialogMessageDaoFacadeImpl implements DialogMessageDaoFacade {
    private NewDialogMessageDao dialogMessageDao;
    private NewDialogMessagesDao dialogsMessagesDao;
    private TransactionPerformer transactionPerformer;

    @Autowired
    public void setDialogMessageDao(NewDialogMessageDao dialogMessageDao) {
        this.dialogMessageDao = dialogMessageDao;
    }

    @Autowired
    public void setDialogsMessagesDao(NewDialogMessagesDao dialogsMessagesDao) {
        this.dialogsMessagesDao = dialogsMessagesDao;
    }

    @Autowired
    public void setTransactionPerformer(TransactionPerformer transactionPerformer) {
        this.transactionPerformer = transactionPerformer;
    }

    @Override
    public DialogMessage select(long messageId) {
        return dialogMessageDao.select(messageId);
    }

    @Override
    public DialogMessage select(DialogMessage message) {
        return dialogMessageDao.select(message);
    }

    @Override
    public boolean create(DialogMessage message) {
        return transactionPerformer.transactionPerformed(dialogMessageDao::create, message);
    }

    @Override
    public boolean update(DialogMessage message) {
        return transactionPerformer.transactionPerformed(dialogMessageDao::update, message);
    }

    @Override
    public boolean delete(DialogMessage message) {
        return transactionPerformer.transactionPerformed(dialogMessageDao::delete, message);
    }

    @Override
    public Collection<DialogMessage> selectAll() {
        return dialogMessageDao.selectAll();
    }

    @Override
    public DialogMessage getNullMessage() {
        return dialogMessageDao.getNullModel();
    }

    @Override
    public Collection<DialogMessage> getMessages(long dialogId) {
        return dialogsMessagesDao.selectMany(dialogId);
    }
}
