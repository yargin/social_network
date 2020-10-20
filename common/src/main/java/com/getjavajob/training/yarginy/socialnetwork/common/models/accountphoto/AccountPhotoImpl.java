package com.getjavajob.training.yarginy.socialnetwork.common.models.accountphoto;

import com.getjavajob.training.yarginy.socialnetwork.common.exceptions.IncorrectData;
import com.getjavajob.training.yarginy.socialnetwork.common.exceptions.IncorrectDataException;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;

import java.io.*;
import java.util.Arrays;
import java.util.Objects;

import static com.getjavajob.training.yarginy.socialnetwork.common.utils.DataCheckHelper.stringOptional;
import static java.util.Objects.isNull;

public class AccountPhotoImpl implements AccountPhoto {
    public static final int MAX_PHOTO_SIZE = 16000000;
    private Account account;
    private byte[] photo;

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    @Override
    public byte[] getPhoto() {
        return photo;
    }

    @Override
    public void setPhoto(InputStream photo) {
        try {
            int size = photo.available();
            if (size > MAX_PHOTO_SIZE) {
                throw new IncorrectDataException(IncorrectData.FILE_TOO_LARGE);
            }
            this.photo = new byte[photo.available()];
            photo.read(this.photo);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    @Override
    public Account getOwner() {
        return account;
    }

    @Override
    public void setOwner(Account account) {
        this.account = account;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(photo) + getOwner().hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (isNull(o)) {
            return false;
        }
        if (this == o) {
            return true;
        }
        if (o instanceof AccountPhoto) {
            AccountPhoto accountPhoto = (AccountPhoto) o;
            return Objects.equals(accountPhoto.getOwner(), account) && Arrays.equals(accountPhoto.getPhoto(), photo);
        }
        return false;
    }
}
