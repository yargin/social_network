package com.getjavajob.training.yarginy.socialnetwork.web.controllers.datakeepers;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.Group;

import java.sql.Date;

public class GroupDto {
    private long id;
    private String name;
    private String description;
    private Account owner;
    private Date creationDate;
    private byte[] photo;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Account getOwner() {
        return owner;
    }

    public void setOwner(Account owner) {
        this.owner = owner;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public Group getGroup() {
        Group group = new Group(name, owner);
        group.setId(id);
        group.setDescription(description);
        group.setCreationDate(creationDate);
        group.setPhoto(photo);
        return group;
    }
}