package com.getjavajob.training.yarginy.socialnetwork.service;


import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.phone.Phone;

import java.util.Collection;
import java.util.Map;

public interface AccountService {
    Account getAccount(int id);

    Account getAccount(Account account);

    boolean createAccount(Account account, Collection<Phone> phones);

    boolean updateAccount(Account account);

    boolean deleteAccount(Account account);

    boolean addFriend(Account account, Account friend);

    boolean removeFriend(Account account, Account friend);

    Collection<Account> getAll(Account account);

    Collection<Account> getFriends(Account account);

    boolean addPhone(Account account, Phone phone);

    boolean removePhone(Phone phone);

    Collection<Phone> getPhones(Account account);

    Map<Account, Collection<Phone>> getAllWithPhones();
}
