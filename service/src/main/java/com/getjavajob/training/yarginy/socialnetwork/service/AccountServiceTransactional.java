package com.getjavajob.training.yarginy.socialnetwork.service;

import com.getjavajob.training.yarginy.socialnetwork.dao.facades.FriendshipsDaoFacade;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class AccountServiceTransactional implements Serializable {
    private final FriendshipsDaoFacade friendshipDao;

    public AccountServiceTransactional(FriendshipsDaoFacade friendshipDao) {
        this.friendshipDao = friendshipDao;
    }

    public void addFriendTransactional(long firstId, long secondId) {
        if (!friendshipDao.createFriendship(firstId, secondId) || !friendshipDao.deleteRequest(firstId, secondId)) {
            throw new IllegalArgumentException();
        }
    }
}
