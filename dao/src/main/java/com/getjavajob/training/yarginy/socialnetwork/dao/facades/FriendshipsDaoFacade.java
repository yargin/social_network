package com.getjavajob.training.yarginy.socialnetwork.dao.facades;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Account;

import java.io.Serializable;
import java.util.Collection;

public interface FriendshipsDaoFacade extends Serializable {
    Collection<Account> selectFriends(long id);

    boolean createFriendship(long firstId, long secondId);

    boolean removeFriendship(long firstId, long secondId);

    boolean areFriends(long firstId, long secondId);

    boolean createRequest(long requesterId, long receiverId);

    boolean deleteRequest(long requesterId, long receiverId);

    boolean isRequester(long requesterId, long receiverId);

    Collection<Account> selectRequests(long receiverId);
}
