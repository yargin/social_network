package com.getjavajob.training.yarginy.socialnetwork.service;


import com.getjavajob.training.yarginy.socialnetwork.common.models.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.Dialog;
import com.getjavajob.training.yarginy.socialnetwork.common.models.Phone;

import java.io.Serializable;
import java.util.Collection;

public interface AccountService extends Serializable {
    Account get(long id);

    Account get(Account account);

    boolean createAccount(Account account, Collection<Phone> phones);

    boolean updateAccount(Account account, Account storedAccount, Collection<Phone> phones, Collection<Phone> storedPhones);

    boolean deleteAccount(Account account);

    boolean addFriend(long firstId, long secondId);

    boolean removeFriend(long firstId, long secondId);

    boolean isFriend(long firstId, long secondId);

    Collection<Account> getFriends(long accountId);

    Collection<Phone> getPhones(long accountId);

    boolean createFriendshipRequest(long requester, long receiver);

    boolean deleteFriendshipRequest(long requester, long receiver);

    boolean isRequester(long requester, long receiver);

    Collection<Account> getFriendshipRequests(long receiver);

    Collection<Dialog> getDialogs(long accountId);
}
