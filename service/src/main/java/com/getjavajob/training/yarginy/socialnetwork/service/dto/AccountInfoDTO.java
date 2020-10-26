package com.getjavajob.training.yarginy.socialnetwork.service.dto;

import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.AccountImpl;
import com.getjavajob.training.yarginy.socialnetwork.common.models.accountphoto.AccountPhoto;
import com.getjavajob.training.yarginy.socialnetwork.common.models.accountphoto.AccountPhotoImpl;
import com.getjavajob.training.yarginy.socialnetwork.common.models.phone.Phone;
import com.getjavajob.training.yarginy.socialnetwork.common.models.phone.additionaldata.PhoneType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * incapsulates account's information such as {@link Account}, {@link AccountPhoto} & {@link Account}'s {@link Phone}s
 */
public class AccountInfoDTO {
    private final Account account;
    private final AccountPhoto accountPhoto;
    private final Collection<Phone> phones;

    public AccountInfoDTO(Account account, AccountPhoto accountPhoto, Collection<Phone> phones) {
        this.account = account;
        this.accountPhoto = accountPhoto;
        this.phones = phones;
    }

    public AccountInfoDTO() {
        this(new AccountImpl(), new AccountPhotoImpl(), new ArrayList<>());
    }

    public Account getAccount() {
        return account;
    }

    public AccountPhoto getAccountPhoto() {
        return accountPhoto;
    }

    public Collection<Phone> getPhones() {
        return phones;
    }

    public Collection<Phone> getPhonesByType(PhoneType type) {
        return phones.stream().filter(p -> type.equals(p.getType())).collect(Collectors.toList());
    }
}
