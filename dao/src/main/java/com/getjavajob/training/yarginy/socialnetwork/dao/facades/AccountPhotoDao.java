package com.getjavajob.training.yarginy.socialnetwork.dao.facades;

import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.accountphoto.AccountPhoto;

import java.util.Collection;

public interface AccountPhotoDao {
    AccountPhoto select(AccountPhoto accountPhoto);

    boolean create(AccountPhoto accountPhoto);

    boolean update(AccountPhoto accountPhoto);

    boolean delete(AccountPhoto accountPhoto);

    AccountPhoto getNullAccountPhoto();
}
