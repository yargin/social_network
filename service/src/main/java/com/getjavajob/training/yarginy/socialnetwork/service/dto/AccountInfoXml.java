package com.getjavajob.training.yarginy.socialnetwork.service.dto;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.Phone;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class AccountInfoXml {
    private Account account;
    private List<Phone> phones;

    public AccountInfoXml(Account account, Collection<Phone> phones) {
        this.account = account;
        this.phones = new ArrayList<>(phones);
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public List<Phone> getPhones() {
        return phones;
    }

    public void setPhones(Collection<Phone> phones) {
        this.phones = new ArrayList<>(phones);
    }
}
