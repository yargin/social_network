package com.getjavajob.training.yarginy.socialnetwork.common.models.phone;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Entity;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.phone.additionaldata.PhoneType;

public interface Phone extends Entity {
    String getNumber();

    void setNumber(String number);

    PhoneType getType();

    void setType(PhoneType type);

    Account getOwner();

    void setOwner(Account owner);
}
