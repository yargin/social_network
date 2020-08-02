package com.getjavajob.training.yarginy.socialnetwork.dao.group;

import com.getjavajob.training.yarginy.socialnetwork.dao.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.dao.group.dtoimplementation.GroupImpl;

import java.util.List;

public interface Group {
    /**
     * notifies that current {@link Group} doesn't exist
     *
     * @return representation of non-existing {@link Group}
     */
    static Group getNullGroup() {
        Group nullAccount = new GroupImpl();
        nullAccount.setName("group not found");
        return nullAccount;
    }

    int getId();

    void setId(int id);

    String getName();

    void setName(String name);

    Account getOwner();

    void setOwner(Account owner);

    String getDescription();

    void setDescription(String description);

    List<Account> getMembers();

    void setMembers(List<Account> members);
}
