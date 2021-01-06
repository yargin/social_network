package com.getjavajob.training.yarginy.socialnetwork.service;

import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.password.Password;
import com.getjavajob.training.yarginy.socialnetwork.common.models.phone.Phone;
import com.getjavajob.training.yarginy.socialnetwork.service.aaa.AccountInfoKeeper;

import java.io.Serializable;
import java.util.Collection;

public interface AuthService extends Serializable {
    boolean register(AccountInfoKeeper accountInfoKeeper, Password password);

    boolean register(Account account, Collection<Phone> phones, Password password);

    Account login(String email, String password);
}
