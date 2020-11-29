package com.getjavajob.training.yarginy.socialnetwork.service;


import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.dialog.Dialog;
import com.getjavajob.training.yarginy.socialnetwork.common.models.phone.Phone;
import com.getjavajob.training.yarginy.socialnetwork.service.dto.AccountInfoDTO;

import java.io.Serializable;
import java.util.Collection;

public interface AccountService extends Serializable {
    AccountInfoDTO getAccountInfo(long id);

    Account get(long id);

    Account get(Account account);

    boolean createAccount(Account account, Collection<Phone> phones);

    boolean updateAccount(Account account, Account storedAccount);

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
