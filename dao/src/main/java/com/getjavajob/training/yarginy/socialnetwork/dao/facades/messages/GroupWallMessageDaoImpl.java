package com.getjavajob.training.yarginy.socialnetwork.dao.facades.messages;

import com.getjavajob.training.yarginy.socialnetwork.common.models.message.Message;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.Dao;
import com.getjavajob.training.yarginy.socialnetwork.dao.relationsdao.onetomany.OneToManyDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component("groupWallMessageDaoFacade")
public class GroupWallMessageDaoImpl implements GroupWallMessageDao {
    private Dao<Message> groupWallMessageDao;
    private OneToManyDao<Message> groupWallMessagesOneToManyDao;

    @Autowired
    public void setGroupWallMessageDao(Dao<Message> groupWallMessageDao) {
        this.groupWallMessageDao = groupWallMessageDao;
    }

    @Autowired
    public void setGroupWallMessagesOneToManyDao(@Qualifier("groupWallMessagesDao")OneToManyDao<Message>
                                                             groupWallMessagesOneToManyDao) {
        this.groupWallMessagesOneToManyDao = groupWallMessagesOneToManyDao;
    }

    @Override
    public Message select(long id) {
        return groupWallMessageDao.select(id);
    }

    @Override
    public Message select(Message message) {
        return groupWallMessageDao.select(message);
    }

    @Override
    public boolean create(Message message) {
        return groupWallMessageDao.create(message);
    }

    @Override
    public boolean update(Message message, Message storedMessage) {
        return groupWallMessageDao.update(message, storedMessage);
    }

    @Override
    public boolean delete(Message message) {
        return groupWallMessageDao.delete(message);
    }

    @Override
    public Collection<Message> selectAll() {
        return groupWallMessageDao.selectAll();
    }

    @Override
    public Message getNullMessage() {
        return groupWallMessageDao.getNullEntity();
    }

    @Override
    public Collection<Message> getMessages(long groupId) {
        return groupWallMessagesOneToManyDao.selectMany(groupId);
    }
}
