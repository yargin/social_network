package com.getjavajob.training.yarginy.socialnetwork.dao.facades;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Account;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.utils.TransactionPerformer;
import com.getjavajob.training.yarginy.socialnetwork.dao.newdaos.relationdaos.manytomany.implementations.NewFriendshipRequestsDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.newdaos.relationdaos.selfmanytomany.implementations.NewFriendshipsDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component("friendshipsDaoFacade")
public class FriendshipsDaoFacadeImpl implements FriendshipsDaoFacade {
    private NewFriendshipsDao friendshipDao;
    private NewFriendshipRequestsDao friendshipRequestsDao;
    private TransactionPerformer transactionPerformer;

    @Autowired
    public void setFriendshipDao(NewFriendshipsDao friendshipDao) {
        this.friendshipDao = friendshipDao;
    }

    @Autowired
    public void setFriendshipRequestsDao(NewFriendshipRequestsDao friendshipRequestsDao) {
        this.friendshipRequestsDao = friendshipRequestsDao;
    }

    @Autowired
    public void setTransactionPerformer(TransactionPerformer transactionPerformer) {
        this.transactionPerformer = transactionPerformer;
    }

    @Override
    public Collection<Account> selectFriends(long id) {
        return friendshipDao.select(id);
    }

    @Override
    public boolean createFriendship(long firstId, long secondId) {
        return transactionPerformer.transactionPerformed(friendshipDao::create, firstId, secondId);
    }

    @Override
    public boolean removeFriendship(long firstId, long secondId) {
        return transactionPerformer.transactionPerformed(friendshipDao::delete, firstId, secondId);
    }

    @Override
    public boolean areFriends(long firstId, long secondId) {
        return friendshipDao.relationExists(firstId, secondId);
    }

    @Override
    public boolean createRequest(long requesterId, long receiverId) {
        return transactionPerformer.transactionPerformed(friendshipRequestsDao::create, requesterId, receiverId);
    }

    @Override
    public boolean deleteRequest(long requesterId, long receiverId) {
        return transactionPerformer.transactionPerformed(friendshipRequestsDao::delete, requesterId, receiverId);
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
