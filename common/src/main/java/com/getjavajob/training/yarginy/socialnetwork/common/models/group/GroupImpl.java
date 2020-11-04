package com.getjavajob.training.yarginy.socialnetwork.common.models.group;

import com.getjavajob.training.yarginy.socialnetwork.common.exceptions.IncorrectData;
import com.getjavajob.training.yarginy.socialnetwork.common.exceptions.IncorrectDataException;
import com.getjavajob.training.yarginy.socialnetwork.common.models.AbstractEntity;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.util.Base64;
import java.util.Objects;

import static com.getjavajob.training.yarginy.socialnetwork.common.utils.DataCheckHelper.*;
import static java.util.Objects.isNull;

public class GroupImpl extends AbstractEntity implements Group {
    private static final int MAX_PHOTO_SIZE = 16000000;
    private String name;
    private String description;
    private Account owner;
    private Date creationDate;
    private byte[] photo;
    private byte[] photoPreview;

    public GroupImpl() {
    }

    public GroupImpl(String name, Account owner) {
        this.name = name;
        this.owner = owner;
    }

    @Override
    public long getId() {
        return getIdNumber();
    }

    @Override
    public void setId(long id) {
        setIdNumber(id);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = stringMandatory(name);
    }

    @Override
    public Account getOwner() {
        return owner;
    }

    @Override
    public void setOwner(Account owner) {
        this.owner = objectMandatory(owner);
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(String description) {
        this.description = stringOptional(description);
    }

    @Override
    public byte[] getPhoto() {
        return photo;
    }

    @Override
    public void setPhoto(byte[] photo) {
        this.photo = photo;
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
    public String getHtmlPhoto() {
        if (!isNull(photo)) {
            return Base64.getEncoder().encodeToString(photo);
        }
        return "";
    }

    @Override
    public Date getCreationDate() {
        return creationDate;
    }

    @Override
    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public byte[] getPhotoPreview() {
        return photoPreview;
    }

    @Override
    public void setPhotoPreview(byte[] photoPreview) {
        this.photoPreview = photoPreview;
    }

    @Override
    public boolean equals(Object o) {
        if (isNull(o)) {
            return false;
        }
        if (this == o) {
            return true;
        }
        if (o instanceof Group) {
            Group group = (Group) o;
            return Objects.equals(stringOptional(name), stringOptional(group.getName()));
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "GroupImpl{name = '" + name + "'}";
    }
}
