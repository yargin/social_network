package com.getjavajob.training.yarginy.socialnetwork.dao.facades;

import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.message.Message;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.Dao;
import com.getjavajob.training.yarginy.socialnetwork.dao.relationsdao.onetomany.OneToManyDao;

import java.util.Collection;

import static com.getjavajob.training.yarginy.socialnetwork.dao.factories.AbstractDbFactory.getDbFactory;

public class AccountWallMessageDaoImpl implements AccountWallMessageDao {
    private final Dao<Message> accountWallMessageDao = getDbFactory().getAccountWallMessageDao();
    private final OneToManyDao<Account, Message> accountWallMessagesOneToManyDao = getDbFactory().
            getAccountWallMessagesDao();

    @Override
    public Message select(long id) {
        return accountWallMessageDao.select(id);
    }

    @Override
    public Message select(Message message) {
        return accountWallMessageDao.select(message);
    }

    @Override
    public boolean create(Message message) {
        return accountWallMessageDao.create(message);
    }

    @Override
    public boolean update(Message message, Message storedMessage) {
        return accountWallMessageDao.update(message, storedMessage);
    }

    @Override
    public boolean delete(Message message) {
        return accountWallMessageDao.delete(message);
    }

    @Override
    public Collection<Message> selectAll() {
        return accountWallMessageDao.selectAll();
    }

    @Override
    public Message getNullMessage() {
        return accountWallMessageDao.getNullEntity();
    }

    @Override
    public Collection<Message> getAccountWallMessages(long accountId) {
        return accountWallMessagesOneToManyDao.selectMany(accountId);
    }
}
