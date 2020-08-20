package com.getjavajob.training.yarginy.socialnetwork.common.entities.group;

import com.getjavajob.training.yarginy.socialnetwork.common.entities.account.Account;

import java.util.Objects;

import static com.getjavajob.training.yarginy.socialnetwork.common.utils.DataCheckHelper.*;
import static java.util.Objects.isNull;

public class GroupImpl implements Group {
    private int id;
    private String name;
    private String description;
    private Account owner;

    public GroupImpl() {
    }

    public GroupImpl(String name, Account owner) {
        this.name = name;
        this.owner = owner;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getIdentifier() {
        return getName();
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = stringCheck(name);
    }

    @Override
    public Account getOwner() {
        return owner;
    }

    @Override
    public void setOwner(Account owner) {
        this.owner = checkObject(owner);
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(String description) {
        this.description = stringTrim(description);
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
            return Objects.equals(stringTrim(name), stringTrim(group.getName()));
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
