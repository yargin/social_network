package com.getjavajob.training.yarginy.socialnetwork.service;

import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.service.aaa.AccountInfoKeeper;

import java.io.Serializable;

public interface AccountInfoService extends Serializable {
    AccountInfoKeeper select(Account account);

    AccountInfoKeeper select(long id);

    boolean update(AccountInfoKeeper accountInfoKeeper, AccountInfoKeeper storedAccountInfoKeeper);
}
