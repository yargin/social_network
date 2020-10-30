package com.getjavajob.training.yarginy.socialnetwork.dao.facades;

import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.accountphoto.AccountPhoto;

public interface AccountPhotoDao {
    AccountPhoto select(Account account);

    boolean create(AccountPhoto accountPhoto);

    boolean update(AccountPhoto accountPhoto);

    boolean delete(AccountPhoto accountPhoto);

    AccountPhoto getNullAccountPhoto();
}
