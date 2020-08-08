package com.getjavajob.trainig.yarginy.socialnetwork.service;

import com.getjavajob.training.yarginy.socialnetwork.dao.entities.account.Account;

public interface AccountService {
    boolean createAccount(Account account);

    boolean updateAccount();

    boolean deleteAccount(Account account);

    boolean addFriend(Account account, Account friend);

    boolean removeFriend(Account account, Account friend);


}
