package com.getjavajob.training.yarginy.socialnetwork.common.models.accountphoto;

import com.getjavajob.training.yarginy.socialnetwork.common.models.OwnedEntity;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;

import java.io.InputStream;
import java.sql.Blob;

public interface AccountPhoto extends OwnedEntity<Account> {
    byte[] getPhoto();

//    InputStream getPhoto();

    void setPhoto(InputStream photo);

    void setPhoto(byte[] photo);

    int getMaxSize();
}
