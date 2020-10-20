package com.getjavajob.training.yarginy.socialnetwork.service.dto;

import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.AccountImpl;
import com.getjavajob.training.yarginy.socialnetwork.common.models.accountphoto.AccountPhoto;
import com.getjavajob.training.yarginy.socialnetwork.common.models.accountphoto.AccountPhotoImpl;
import com.getjavajob.training.yarginy.socialnetwork.common.models.password.Password;
import com.getjavajob.training.yarginy.socialnetwork.common.models.password.PasswordImpl;
import com.getjavajob.training.yarginy.socialnetwork.common.models.phone.Phone;

import java.util.ArrayList;
import java.util.Collection;

public class AccountInfoDTO {
    private final Account account = new AccountImpl();
    private final AccountPhoto accountPhoto = new AccountPhotoImpl();
    private final Collection<Phone> phones = new ArrayList<>();

    public Account getAccount() {
        return account;
    }

    public AccountPhoto getAccountPhoto() {
        return accountPhoto;
    }

    public Collection<Phone> getPhones() {
        return phones;
    }
}
