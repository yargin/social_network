package com.getjavajob.training.yarginy.socialnetwork.common.models.group;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Entity;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;

import java.io.InputStream;
import java.sql.Date;

public interface Group extends Entity {
    String getName();

    void setName(String name);

    String getDescription();

    void setDescription(String description);

    Account getOwner();

    void setOwner(Account entity);

    Date getCreationDate();

    void setCreationDate(Date date);

    byte[] getPhoto();

    String getHtmlPhoto();

    void setPhoto(InputStream photo);

    void setPhoto(byte[] photo);

    byte[] getPhotoPreview();

    void setPhotoPreview(byte[] photoPreview);
}
