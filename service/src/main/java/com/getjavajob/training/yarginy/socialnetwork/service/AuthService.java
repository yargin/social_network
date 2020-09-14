package com.getjavajob.training.yarginy.socialnetwork.service;

import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.password.Password;
import com.getjavajob.training.yarginy.socialnetwork.common.models.phone.Phone;

import java.util.Collection;

public interface AuthService {
    boolean register(Account account, Collection<Phone> phones, Password password);

    Account login(Password password);

    boolean logout(Account account);

    boolean delete(Account account);
}
