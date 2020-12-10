package com.getjavajob.training.yarginy.socialnetwork.dao.facades;

import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.dao.relationsdao.manytomany.selfrelated.SelfManyToManyDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.relationsdao.manytomany.variousrelated.ManyToManyDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component("friendshipsDaoFacade")
public class FriendshipsFacadeImpl implements FriendshipsFacade {
    private SelfManyToManyDao<Account> friendshipDao;
    private ManyToManyDao<Account, Account> friendshipRequestsDao;

    @Autowired
    public void setFriendshipDao(SelfManyToManyDao<Account> friendshipDao) {
        this.friendshipDao = friendshipDao;
    }

    @Autowired
    public void setFriendshipRequestsDao(@Qualifier("friendshipRequestsDao")
                                                     ManyToManyDao<Account, Account> friendshipRequestsDao) {
        this.friendshipRequestsDao = friendshipRequestsDao;
    }

    @Override
    public Collection<Account> selectFriends(long id) {
        return friendshipDao.select(id);
    }

    @Override
    public boolean createFriendship(long firstId, long secondId) {
        return friendshipDao.create(firstId, secondId);
    }

    @Override
    public boolean removeFriendship(long firstId, long secondId) {
        return friendshipDao.delete(firstId, secondId);
    }

    @Override
    public boolean areFriends(long firstId, long secondId) {
        return friendshipDao.relationExists(firstId, secondId);
    }

    @Override
    public boolean createRequest(long requesterId, long receiverId) {
        return friendshipRequestsDao.create(requesterId, receiverId);
    }

    @Override
    public boolean deleteRequest(long requesterId, long receiverId) {
        return friendshipRequestsDao.delete(requesterId, receiverId);
    }

    @Override
    public boolean isRequester(long requesterId, long receiverId) {
        return friendshipRequestsDao.relationExists(requesterId, receiverId);
    }

    @Override
    public Collection<Account> selectRequests(long receiverId) {
        return friendshipRequestsDao.selectBySecond(receiverId);
    }
}
