package com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.facades;

import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;

import java.util.Collection;

public interface FriendshipDao {
    Collection<Account> selectFriends(Account account);

    boolean createFriendship(Account firstAccount, Account secondAccount);

    boolean cancelFriendship(Account firstAccount, Account secondAccount);
}
