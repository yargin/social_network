package com.getjavajob.training.yarginy.socialnetwork.common.models;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Version;
import java.util.Objects;

@Component
@Scope("prototype")
@Entity
@Table(name = "passwords")
public class Password implements Model {
    @Id
    @OneToOne
    @PrimaryKeyJoinColumn(name = "email", referencedColumnName = "email", foreignKey = @ForeignKey(name = "c_21"))
    private Account account;
    @Id
    @Column(name = "password")
    private String stringPassword;
    @Version
    private long version;

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String getStringPassword() {
        return stringPassword;
    }

    public void setStringPassword(String stringPassword) {
        this.stringPassword = stringPassword;
    }

    @Override
    public long getId() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setId(long id) {
        throw new UnsupportedOperationException();
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
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Password password1 = (Password) o;
        return Objects.equals(account, password1.account) && Objects.equals(stringPassword, password1.stringPassword);
    }

    @Override
    public int hashCode() {
        return Objects.hash(account, stringPassword);
    }
}
