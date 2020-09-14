package com.getjavajob.training.yarginy.socialnetwork.common.models.phone;

import com.getjavajob.training.yarginy.socialnetwork.common.models.AbstractEntity;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.phone.additionaldata.PhoneType;

import java.util.Objects;

import static com.getjavajob.training.yarginy.socialnetwork.common.utils.DataCheckHelper.*;
import static java.util.Objects.isNull;

public class PhoneImpl extends AbstractEntity implements Phone {
    private String number;
    private PhoneType type;
    private Account owner;

    public PhoneImpl() {
    }

    public PhoneImpl(String number, Account owner) {
        this.number = phoneMandatory(number);
        this.owner = objectMandatory(owner);
    }

    @Override
    public String getNumber() {
        return phoneMandatory(number);
    }

    @Override
    public void setNumber(String number) {
        this.number = phoneMandatory(number);
    }

    @Override
    public PhoneType getType() {
        return objectMandatory(type);
    }

    public void setType(PhoneType type) {
        this.type = objectMandatory(type);
    }

    @Override
    public Account getOwner() {
        return objectMandatory(owner);
    }

    public void setOwner(Account owner) {
        this.owner = objectMandatory(owner);
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
    public boolean equals(Object o) {
        if (isNull(o)) {
            return false;
        }
        if (this == o) {
            return true;
        }
        if (o instanceof Phone) {
            Phone phone = (Phone) o;
            return Objects.equals(stringOptional(number), stringOptional(phone.getNumber()));
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(number);
    }

    @Override
    public String toString() {
        return "PhoneImpl{number='" + number + "'}";
    }
}
