package com.getjavajob.training.yarginy.socialnetwork.common.models;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedEntityGraph;
import javax.persistence.Table;
import javax.persistence.Version;
import java.sql.Date;
import java.util.Arrays;
import java.util.Objects;

import static java.util.Objects.isNull;

@Component
@Scope("prototype")
@Entity
@Table(name = "groups")
@NamedEntityGraph(name = "graph.Group.allProperties", includeAllAttributes = true)
public class Group implements Model {
    @Id
    @GeneratedValue
    private long id;
    @Column(nullable = false, unique = true)
    private String name;
    private String description;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", foreignKey = @ForeignKey(name = "c_11"))
    private Account owner;
    @Column(nullable = false)
    private Date creationDate;
    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[] photo;
    @Version
    private long version;

    public Group() {
    }

    public Group(long id) {
        this.id = id;
    }

    public Group(String name, Account owner) {
        this.name = name;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Account getOwner() {
        return owner;
    }

    public void setOwner(Account owner) {
        this.owner = owner;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        if (!isNull(photo)) {
            this.photo = Arrays.copyOf(photo, photo.length);
        }
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public long getVersion() {
        return version;
    }

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
        if (o instanceof Group) {
            Group group = (Group) o;
            return Objects.equals(name, group.getName());
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
