package com.getjavajob.training.yarginy.socialnetwork.dao.facades;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.utils.TransactionPerformer;
import com.getjavajob.training.yarginy.socialnetwork.dao.relationdaos.manytomany.implementations.FriendshipRequestsDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.relationdaos.selfmanytomany.implementations.FriendshipsDao;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component("friendshipsDaoFacade")
public class FriendshipsDaoFacadeImpl implements FriendshipsDaoFacade {
    private final FriendshipsDao friendshipDao;
    private final FriendshipRequestsDao friendshipRequestsDao;
    private final TransactionPerformer transactionPerformer;

    public FriendshipsDaoFacadeImpl(FriendshipsDao friendshipDao, FriendshipRequestsDao friendshipRequestsDao,
                                    TransactionPerformer transactionPerformer) {
        this.friendshipDao = friendshipDao;
        this.friendshipRequestsDao = friendshipRequestsDao;
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
