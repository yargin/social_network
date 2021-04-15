package com.getjavajob.training.yarginy.socialnetwork.dao.facades.messages;

import com.getjavajob.training.yarginy.socialnetwork.common.models.messages.DialogMessage;
import com.getjavajob.training.yarginy.socialnetwork.dao.models.messages.DialogMessageDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.relationsdao.onetomany.DialogMessagesDao;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component("dialogMessageDaoFacade")
public class DialogMessageDaoFacadeImpl implements DialogMessageDaoFacade {
    private final DialogMessageDao dialogMessageDao;
    private final DialogMessagesDao dialogsMessagesDao;

    public DialogMessageDaoFacadeImpl(DialogMessageDao dialogMessageDao,
                                      DialogMessagesDao dialogsMessagesDao) {
        this.dialogMessageDao = dialogMessageDao;
        this.dialogsMessagesDao = dialogsMessagesDao;
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
        return dialogMessageDao.create(message);
    }

    @Override
    public boolean update(DialogMessage message) {
        return dialogMessageDao.update(message);
    }

    @Override
    public boolean delete(DialogMessage message) {
        return dialogMessageDao.delete(message);
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
