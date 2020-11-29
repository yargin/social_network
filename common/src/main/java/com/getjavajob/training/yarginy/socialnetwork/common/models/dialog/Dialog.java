package com.getjavajob.training.yarginy.socialnetwork.common.models.dialog;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Entity;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;

public interface Dialog extends Entity {
    Account getFirstAccount();

    void setFirstAccount(Account firstAccount);

    Account getSecondAccount();

    void setSecondAccount(Account secondAccount);
}
