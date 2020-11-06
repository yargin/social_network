package com.getjavajob.training.yarginy.socialnetwork.service;


import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.phone.Phone;
import com.getjavajob.training.yarginy.socialnetwork.service.dto.AccountInfoDTO;

import java.util.Collection;
import java.util.Map;

public interface AccountService {
    AccountInfoDTO getAccountInfo(long id);

    Account getAccount(long id);

    Account getAccount(Account account);

    boolean createAccount(Account account, Collection<Phone> phones);

    boolean updateAccount(Account account, Account storedAccount);

    boolean deleteAccount(Account account);

    boolean addFriend(Account account, Account friend);

    boolean removeFriend(Account account, Account friend);

    boolean isFriend(long firstId, long secondId);

    Collection<Account> getAll(Account account);

    Collection<Account> getFriends(Account account);

    boolean addPhone(Account account, Phone phone);

    boolean removePhone(Phone phone);

    Collection<Phone> getPhones(Account account);

    Map<Account, Collection<Phone>> getAllWithPhones();

    boolean updatePhones(Collection<Phone> phones, Account account);

    //todo logic for group
}
