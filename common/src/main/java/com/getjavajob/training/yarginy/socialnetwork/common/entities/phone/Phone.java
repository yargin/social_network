package com.getjavajob.training.yarginy.socialnetwork.common.entities.phone;

import com.getjavajob.training.yarginy.socialnetwork.common.entities.Entity;
import com.getjavajob.training.yarginy.socialnetwork.common.entities.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.entities.phone.additionaldata.PhoneType;

public interface Phone extends Entity {
    String getNumber();

    void setNumber(String number);

    PhoneType getType();

    void setType(PhoneType type);

    Account getOwner();

    void setOwner(Account owner);
}
