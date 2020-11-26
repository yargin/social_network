package com.getjavajob.training.yarginy.socialnetwork.common.models.phone;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Entity;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.phone.additionaldata.PhoneType;

/**
 * provides object model of relational entity Phone. All phones are unique
 */
public interface Phone extends Entity {
    String getNumber();

    void setNumber(String number);

    PhoneType getType();

    void setType(PhoneType type);

    void setOwner(Account entity);

    Account getOwner();
}
