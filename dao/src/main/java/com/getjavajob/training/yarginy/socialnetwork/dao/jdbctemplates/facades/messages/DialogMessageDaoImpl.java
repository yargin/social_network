package com.getjavajob.training.yarginy.socialnetwork.dao.jdbctemplates.facades.messages;

import com.getjavajob.training.yarginy.socialnetwork.common.models.message.Message;
import com.getjavajob.training.yarginy.socialnetwork.dao.jdbctemplates.modeldao.Dao;
import com.getjavajob.training.yarginy.socialnetwork.dao.jdbctemplates.relationsdao.onetomany.OneToManyDao;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component("dialogMessageDaoFacade")
public class DialogMessageDaoImpl implements DialogMessageDaoFacade {
    private final Dao<Message> dialogMessageDao;
    private final OneToManyDao<Message> dialogsMessagesDao;

    public DialogMessageDaoImpl(@Qualifier("dialogMessageDao") Dao<Message> dialogMessageDao,
                                @Qualifier("dialogMessagesDao") OneToManyDao<Message> dialogsMessagesDao) {
        this.dialogMessageDao = dialogMessageDao;
        this.dialogsMessagesDao = dialogsMessagesDao;
    }

    @Override
    public Message select(long messageId) {
        return dialogMessageDao.select(messageId);
    }

    @Override
    public Message select(Message message) {
        return dialogMessageDao.select(message);
    }

    @Override
    public boolean create(Message message) {
        return dialogMessageDao.create(message);
    }

    @Override
    public boolean update(Message message, Message storedMessage) {
        return dialogMessageDao.update(message, storedMessage);
    }

    @Override
    public boolean delete(Message message) {
        return dialogMessageDao.delete(message);
    }

    @Override
    public Collection<Message> selectAll() {
        return dialogMessageDao.selectAll();
    }

    @Override
    public Message getNullMessage() {
        return dialogMessageDao.getNullModel();
    }

    @Override
    public Collection<Message> getMessages(long accountId) {
        return dialogsMessagesDao.selectMany(accountId);
    }
}
