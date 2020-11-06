package com.getjavajob.training.yarginy.socialnetwork.dao.facades;

import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;

import java.util.Collection;

public interface AccountsFriendshipsDao {
    Collection<Account> selectFriends(Account account);

    boolean createFriendship(Account firstAccount, Account secondAccount);

    boolean removeFriendship(Account firstAccount, Account secondAccount);

    boolean areFriends(long firstId, long secondId);

    boolean createRequest(Account requester, Account receiver);

    boolean deleteRequest(Account requester, Account receiver);

    Collection<Account> selectRequests(Account account);
}
