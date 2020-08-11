package com.getjavajob.trainig.yarginy.socialnetwork.service;


import com.getjavajob.training.yarginy.socialnetwork.common.entities.account.Account;

import java.util.Collection;

public interface AccountService {
    Account getAccount(int id);

    Account getAccount(String email);

    boolean createAccount(Account account);

    boolean updateAccount(Account account);

    boolean deleteAccount(Account account);

    boolean addFriend(Account account, Account friend);

    boolean removeFriend(Account account, Account friend);

    Collection<Account> getFriends(Account account);
}
