package com.getjavajob.training.yarginy.socialnetwork.dao.facades.messages;

import com.getjavajob.training.yarginy.socialnetwork.common.models.messages.DialogMessage;
import com.getjavajob.training.yarginy.socialnetwork.dao.jpa.modeldaos.implementations.messages.DialogMessageDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.jpa.relationdaos.onetomany.implementations.messages.DialogMessagesDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.utils.TransactionPerformer;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component("dialogMessageDaoFacade")
public class DialogMessageDaoFacadeImpl implements DialogMessageDaoFacade {
    private final DialogMessageDao dialogMessageDao;
    private final DialogMessagesDao dialogsMessagesDao;
    private final TransactionPerformer transactionPerformer;

    public DialogMessageDaoFacadeImpl(DialogMessageDao dialogMessageDao, DialogMessagesDao dialogsMessagesDao,
                                      TransactionPerformer transactionPerformer) {
        this.dialogMessageDao = dialogMessageDao;
        this.dialogsMessagesDao = dialogsMessagesDao;
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
