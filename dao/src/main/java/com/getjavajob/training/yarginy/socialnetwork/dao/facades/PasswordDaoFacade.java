package com.getjavajob.training.yarginy.socialnetwork.dao.facades;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.Password;

import java.io.Serializable;

public interface PasswordDaoFacade extends Serializable {
    Password select(Password password);

    Password select(long accountId);

    boolean create(Password password);

    boolean delete(Password password);

    Password getNullPassword();

    Password getPasswordByAccount(Account account);
}
