package com.getjavajob.training.yarginy.socialnetwork.dao.facades;

import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;

import java.util.Collection;

public interface FriendshipDao {
    Collection<Account> selectFriends(Account account);

    boolean createFriendship(Account firstAccount, Account secondAccount);

    boolean removeFriendship(Account firstAccount, Account secondAccount);
}
