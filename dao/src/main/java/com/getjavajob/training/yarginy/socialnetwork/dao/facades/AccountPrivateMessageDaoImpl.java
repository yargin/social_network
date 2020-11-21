package com.getjavajob.training.yarginy.socialnetwork.dao.facades;

import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.message.Message;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.Dao;
import com.getjavajob.training.yarginy.socialnetwork.dao.relationsdao.onetomany.OneToManyDao;

import java.util.Collection;

import static com.getjavajob.training.yarginy.socialnetwork.dao.factories.AbstractDbFactory.getDbFactory;

public class AccountPrivateMessageDaoImpl implements AccountPrivateMessageDao {
    private final Dao<Message> accountPrivateMessageDao = getDbFactory().getAccountPrivateMessageDao();
    private final OneToManyDao<Account, Message> accountPrivateMessagesOneToManyDao = getDbFactory().
            getAccountPrivateMessagesDao();

    @Override
    public Message select(long id) {
        return accountPrivateMessageDao.select(id);
    }

    @Override
    public Message select(Message message) {
        return accountPrivateMessageDao.select(message);
    }

    @Override
    public boolean create(Message message) {
        return accountPrivateMessageDao.create(message);
    }

    @Override
    public boolean update(Message message, Message storedMessage) {
        return accountPrivateMessageDao.update(message, storedMessage);
    }

    @Override
    public boolean delete(Message message) {
        return accountPrivateMessageDao.delete(message);
    }

    @Override
    public Collection<Message> selectAll() {
        return accountPrivateMessageDao.selectAll();
    }

    @Override
    public Message getNullMessage() {
        return accountPrivateMessageDao.getNullEntity();
    }

    @Override
    public Collection<Message> getAccountPrivateMessages(long accountId) {
        return accountPrivateMessagesOneToManyDao.selectMany(accountId);
    }
}
