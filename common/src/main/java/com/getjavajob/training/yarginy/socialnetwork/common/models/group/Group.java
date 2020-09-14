package com.getjavajob.training.yarginy.socialnetwork.common.models.group;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Entity;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;

public interface Group extends Entity {
    String getName();

    void setName(String name);

    Account getOwner();

    void setOwner(Account owner);

    String getDescription();

    void setDescription(String description);
}
