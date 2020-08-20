package com.getjavajob.training.yarginy.socialnetwork.common.entities;

import com.getjavajob.training.yarginy.socialnetwork.common.entities.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.entities.account.AccountImpl;
import com.getjavajob.training.yarginy.socialnetwork.common.entities.group.Group;
import com.getjavajob.training.yarginy.socialnetwork.common.entities.group.GroupImpl;

public abstract class NullEntitiesFactory {
    private NullEntitiesFactory() {
    }

    public static Account getNullAccount() {
        Account nullAccount = new AccountImpl();
        nullAccount.setId(-1);
        nullAccount.setEmail("email@doesnt.exist");
        return nullAccount;
    }

    public static Group getNullGroup() {
        Group nullGroup = new GroupImpl();
        nullGroup.setId(-1);
        nullGroup.setName("group doesn't exist");
        return nullGroup;
    }
}
