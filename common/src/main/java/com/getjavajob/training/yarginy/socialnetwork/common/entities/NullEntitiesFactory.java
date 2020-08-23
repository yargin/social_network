package com.getjavajob.training.yarginy.socialnetwork.common.entities;

import com.getjavajob.training.yarginy.socialnetwork.common.entities.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.entities.account.AccountImpl;
import com.getjavajob.training.yarginy.socialnetwork.common.entities.group.Group;
import com.getjavajob.training.yarginy.socialnetwork.common.entities.group.GroupImpl;
import com.getjavajob.training.yarginy.socialnetwork.common.entities.phone.Phone;
import com.getjavajob.training.yarginy.socialnetwork.common.entities.phone.PhoneImpl;

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

    public static Phone getNullPhone() {
        Phone phone = new PhoneImpl();
        phone.setNumber("000000");
        return phone;
    }
}
