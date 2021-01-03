package com.getjavajob.training.yarginy.socialnetwork.service.datakeepers;

import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.AccountImpl;
import com.getjavajob.training.yarginy.socialnetwork.common.models.phone.Phone;
import com.getjavajob.training.yarginy.socialnetwork.common.models.phone.additionaldata.PhoneType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * holds account's information such as {@link Account} & {@link Account}'s {@link Phone}s
 */
public class AccountInfoKeeper implements Serializable {
    private final Account account;
    private final Collection<Phone> phones;

    public AccountInfoKeeper(Account account, Collection<Phone> phones) {
        this.account = account;
        this.phones = phones;
    }

    public AccountInfoKeeper() {
        this(new AccountImpl(), new ArrayList<>());
    }

    public Account getAccount() {
        return account;
    }

    public Collection<Phone> getPhones() {
        return phones;
    }

    public Collection<Phone> getPhonesByType(PhoneType type) {
        return phones.stream().filter(p -> type.equals(p.getType())).collect(Collectors.toList());
    }
}
