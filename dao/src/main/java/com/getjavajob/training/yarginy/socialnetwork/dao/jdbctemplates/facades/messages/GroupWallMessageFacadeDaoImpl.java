package com.getjavajob.training.yarginy.socialnetwork.dao.jdbctemplates.facades.messages;

import com.getjavajob.training.yarginy.socialnetwork.common.models.messages.GroupWallMessage;
import com.getjavajob.training.yarginy.socialnetwork.dao.jpa.messages.JpaGroupWallMessageDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.jpa.relationsdao.onetomany.JpaGroupWallMessagesDao;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component("groupWallMessageDaoFacade")
public class GroupWallMessageFacadeDaoImpl implements GroupWallMessageDaoFacade {
    private final JpaGroupWallMessageDao groupWallMessageDao;
    private final JpaGroupWallMessagesDao groupWallMessagesDao;

    public GroupWallMessageFacadeDaoImpl(JpaGroupWallMessageDao groupWallMessageDao,
                                         JpaGroupWallMessagesDao groupWallMessagesDao) {
        this.groupWallMessageDao = groupWallMessageDao;
        this.groupWallMessagesDao = groupWallMessagesDao;
    }

    @Override
    public GroupWallMessage select(long messageId) {
        return groupWallMessageDao.select(messageId);
    }

    @Override
    public GroupWallMessage select(GroupWallMessage message) {
        return groupWallMessageDao.select(message);
    }

    @Override
    public boolean create(GroupWallMessage message) {
        return groupWallMessageDao.create(message);
    }

    @Override
    public boolean update(GroupWallMessage message, GroupWallMessage storedMessage) {
        //todo
//        return groupWallMessageDao.update(message, storedMessage);
        return groupWallMessageDao.update(message);
    }

    @Override
    public boolean delete(GroupWallMessage message) {
        return groupWallMessageDao.delete(message);
    }

    @Override
    public Collection<GroupWallMessage> selectAll() {
        return groupWallMessageDao.selectAll();
    }

    @Override
    public GroupWallMessage getNullMessage() {
        return groupWallMessageDao.getNullModel();
    }

    @Override
    public Collection<GroupWallMessage> getMessages(long groupId) {
        return groupWallMessagesDao.selectMany(groupId);
    }
}
