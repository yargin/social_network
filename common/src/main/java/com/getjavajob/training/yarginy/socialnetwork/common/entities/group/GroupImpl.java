package com.getjavajob.training.yarginy.socialnetwork.common.entities.group;

import com.getjavajob.training.yarginy.socialnetwork.common.entities.account.Account;

import java.util.List;
import java.util.Objects;

import static com.getjavajob.training.yarginy.socialnetwork.common.utils.StringHandler.trimString;
import static java.util.Objects.isNull;

public class GroupImpl implements Group {
    private int id;
    private String name;
    private String description;
    private Account owner;
    private List<Account> members;

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
        this.name = name;
    }

    @Override
    public Account getOwner() {
        return owner;
    }

    @Override
    public void setOwner(Account owner) {
        this.owner = owner;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
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
            return Objects.equals(trimString(name), trimString(group.getName()));
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
