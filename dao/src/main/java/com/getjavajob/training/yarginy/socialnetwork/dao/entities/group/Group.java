package com.getjavajob.training.yarginy.socialnetwork.dao.entities.group;

import com.getjavajob.training.yarginy.socialnetwork.dao.entities.Entity;
import com.getjavajob.training.yarginy.socialnetwork.dao.entities.account.Account;

public interface Group extends Entity {
    int getId();

    void setId(int id);

    String getName();

    void setName(String name);

    Account getOwner();

    void setOwner(Account owner);

    String getDescription();

    void setDescription(String description);
}
