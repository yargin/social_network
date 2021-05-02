package com.getjavajob.training.yarginy.socialnetwork.service;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.Password;
import com.getjavajob.training.yarginy.socialnetwork.common.models.Phone;

import java.io.Serializable;
import java.util.Collection;

public interface AuthService extends Serializable {
    boolean register(Account account, Collection<Phone> phones, String password);

    Account login(String email, String password);
}
