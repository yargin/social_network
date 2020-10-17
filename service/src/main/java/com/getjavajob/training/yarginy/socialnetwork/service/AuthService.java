package com.getjavajob.training.yarginy.socialnetwork.service;

import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.accountphoto.AccountPhoto;
import com.getjavajob.training.yarginy.socialnetwork.common.models.password.Password;
import com.getjavajob.training.yarginy.socialnetwork.common.models.phone.Phone;

import java.util.Collection;

public interface AuthService {
    /**
     * @param account
     * @param phones
     * @param password
     * @return
     * @throws
     */
    boolean register(Account account, Collection<Phone> phones, Password password, AccountPhoto accountPhoto);

    Account login(String email, String password);

    boolean logout(Account account);

    boolean delete(Account account);
}
