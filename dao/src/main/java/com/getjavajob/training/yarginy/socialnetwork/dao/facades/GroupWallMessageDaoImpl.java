package com.getjavajob.training.yarginy.socialnetwork.dao.facades;

import com.getjavajob.training.yarginy.socialnetwork.common.models.group.Group;
import com.getjavajob.training.yarginy.socialnetwork.common.models.message.Message;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.Dao;
import com.getjavajob.training.yarginy.socialnetwork.dao.relationsdao.onetomany.OneToManyDao;

import java.util.Collection;

import static com.getjavajob.training.yarginy.socialnetwork.dao.factories.AbstractDbFactory.getDbFactory;

public class GroupWallMessageDaoImpl implements GroupWallMessageDao {
    private final Dao<Message> groupWallMessageDao = getDbFactory().getGroupWallMessageDao();
    private final OneToManyDao<Group, Message> groupWallMessagesOneToManyDao = getDbFactory().getGroupWallMessagesDao();

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
    public Collection<Message> getGroupWallMessages(long accountId) {
        return groupWallMessagesOneToManyDao.selectMany(accountId);
    }
}
