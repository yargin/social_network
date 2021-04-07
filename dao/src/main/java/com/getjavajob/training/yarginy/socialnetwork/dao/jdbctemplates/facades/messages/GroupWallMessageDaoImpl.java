package com.getjavajob.training.yarginy.socialnetwork.dao.jdbctemplates.facades.messages;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Message;
import com.getjavajob.training.yarginy.socialnetwork.dao.jdbctemplates.modeldao.Dao;
import com.getjavajob.training.yarginy.socialnetwork.dao.jdbctemplates.relationsdao.onetomany.OneToManyDao;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component("groupWallMessageDaoFacade")
public class GroupWallMessageDaoImpl implements GroupWallMessageDaoFacade {
    private final Dao<Message> groupWallMessageDao;
    private final OneToManyDao<Message> groupWallMessagesDao;

    public GroupWallMessageDaoImpl(@Qualifier("groupWallMessageDao") Dao<Message> groupWallMessageDao,
                                   @Qualifier("groupWallMessagesDao") OneToManyDao<Message> groupWallMessagesDao) {
        this.groupWallMessageDao = groupWallMessageDao;
        this.groupWallMessagesDao = groupWallMessagesDao;
    }

    @Override
    public Message select(long messageId) {
        return groupWallMessageDao.select(messageId);
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
        return groupWallMessageDao.getNullModel();
    }

    @Override
    public Collection<Message> getMessages(long groupId) {
        return groupWallMessagesDao.selectMany(groupId);
    }
}
