package com.getjavajob.training.yarginy.socialnetwork.dao.facades.messages;

import com.getjavajob.training.yarginy.socialnetwork.common.models.messages.GroupWallMessage;
import com.getjavajob.training.yarginy.socialnetwork.dao.models.Dao;
import com.getjavajob.training.yarginy.socialnetwork.dao.relationsdao.onetomany.OneToManyDao;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component("groupWallMessageDaoFacade")
public class GroupWallMessageFacadeDaoImpl implements GroupWallMessageDaoFacade {
    private final Dao<GroupWallMessage> groupWallMessageDao;
    private final OneToManyDao<GroupWallMessage> groupWallMessagesDao;

    public GroupWallMessageFacadeDaoImpl(@Qualifier("groupWallMessageDao") Dao<GroupWallMessage> groupWallMessageDao,
                                         @Qualifier("groupWallMessagesDao") OneToManyDao<GroupWallMessage> groupWallMessagesDao) {
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
    public boolean update(GroupWallMessage message) {
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
