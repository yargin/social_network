package com.getjavajob.training.yarginy.socialnetwork.dao.facades.messages;

import com.getjavajob.training.yarginy.socialnetwork.common.models.message.Message;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.Dao;
import com.getjavajob.training.yarginy.socialnetwork.dao.relationsdao.onetomany.OneToManyDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component("dialogMessageDaoFacade")
public class DialogMessageDaoImpl implements DialogMessageDao {
    private Dao<Message> dialogMessageDao;
    private OneToManyDao<Message> dialogsMessagesDao;

    @Autowired
    public void setDialogMessageDao(Dao<Message> dialogMessageDao) {
        this.dialogMessageDao = dialogMessageDao;
    }

    @Autowired
    public void setDialogsMessagesDao(@Qualifier("dialogMessagesDao")OneToManyDao<Message> dialogsMessagesDao) {
        this.dialogsMessagesDao = dialogsMessagesDao;
    }

    @Override
    public Message select(long id) {
        return dialogMessageDao.select(id);
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
        return dialogMessageDao.getNullEntity();
    }

    @Override
    public Collection<Message> getMessages(long accountId) {
        return dialogsMessagesDao.selectMany(accountId);
    }
}
