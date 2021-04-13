package com.getjavajob.training.yarginy.socialnetwork.common.models;

import com.getjavajob.training.yarginy.socialnetwork.common.models.additionaldata.PhoneType;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;
import java.util.Objects;

import static com.getjavajob.training.yarginy.socialnetwork.common.models.additionaldata.PhoneType.PRIVATE;
import static java.util.Objects.isNull;

@Component
@Scope("prototype")
@Entity
@Table(name = "phones")
public class Phone implements Model {
    @Id
    @GeneratedValue
    private long id;
    @Column(nullable = false, unique = true)
    private String number;
    @Enumerated(EnumType.STRING)
    private PhoneType type;
    @ManyToOne
    @JoinColumn(nullable = false, name = "owner_id", foreignKey = @ForeignKey(name = "c_18"))
    private Account owner;
    @Version
    private long version;

    public Phone() {
        type = PRIVATE;
    }

    public Phone(String number, Account owner) {
        this.number = number;
        this.owner = owner;
        setType(PRIVATE);
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public PhoneType getType() {
        return type;
    }

    public void setType(PhoneType type) {
        this.type = type;
    }

    public Account getOwner() {
        return owner;
    }

    public void setOwner(Account owner) {
        this.owner = owner;
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    @Override
    public long getVersion() {
        return version;
    }

    @Override
    public void setVersion(long version) {
        this.version = version;
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
            return Objects.equals(number, phone.getNumber());
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
