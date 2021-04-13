package com.getjavajob.training.yarginy.socialnetwork.dao.jdbctemplates.facades;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.manytomany.JpaSelfManyToMany;
import com.getjavajob.training.yarginy.socialnetwork.dao.jdbctemplates.relationsdao.manytomany.ManyToManyDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.jdbctemplates.relationsdao.manytomany.SelfManyToManyDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.jpa.relationsdao.manytomany.JpaManyToManyDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.jpa.relationsdao.selfmanytomany.JpaSelfManyToManyDao;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component("friendshipsDaoFacade")
public class FriendshipsDaoFacadeImpl implements FriendshipsDaoFacade {
    private final JpaSelfManyToManyDao<Account> friendshipDao;
    private final JpaManyToManyDao<Account, Account> friendshipRequestsDao;

    public FriendshipsDaoFacadeImpl(@Qualifier("jpaFriendshipDao") JpaSelfManyToManyDao<Account> friendshipDao,
                                    @Qualifier("jpaFriendshipRequestsDao") JpaManyToManyDao<Account, Account>
                                            friendshipRequestsDao) {
        this.friendshipDao = friendshipDao;
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
