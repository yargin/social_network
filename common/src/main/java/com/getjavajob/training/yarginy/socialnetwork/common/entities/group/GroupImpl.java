package com.getjavajob.training.yarginy.socialnetwork.common.entities.group;

import com.getjavajob.training.yarginy.socialnetwork.common.entities.AbstractEntity;
import com.getjavajob.training.yarginy.socialnetwork.common.entities.account.Account;

import java.util.Objects;

import static com.getjavajob.training.yarginy.socialnetwork.common.utils.DataCheckHelper.*;
import static java.util.Objects.isNull;

public class GroupImpl extends AbstractEntity implements Group {
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
    public long getId() {
        return getIdNumber();
    }

    @Override
    public String getIdentifier() {
        return getName();
    }

    @Override
    public void setId(long id) {
        setIdNumber(id);
    }

    @Override
    public String getName() {
        return stringMandatory(name);
    }

    @Override
    public void setName(String name) {
        this.name = stringMandatory(name);
    }

    @Override
    public Account getOwner() {
        return objectMandatory(owner);
    }

    @Override
    public void setOwner(Account owner) {
        this.owner = objectMandatory(owner);
    }

    @Override
    public String getDescription() {
        return stringOptional(description);
    }

    @Override
    public void setDescription(String description) {
        this.description = stringOptional(description);
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
