package com.getjavajob.training.yarginy.socialnetwork.common.models;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.NamedEntityGraph;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Version;
import java.util.Objects;

@Component
@Scope("prototype")
@Entity
@Table(name = "passwords")
@NamedEntityGraph(name = "graph.Password.allProperties", includeAllAttributes = true)
public class Password implements Model {
    @Id
    @Column(name = "account_id")
    private long id;
    //todo fetch type
    @OneToOne(fetch = FetchType.EAGER)
    @MapsId
    @JoinColumn(name = "account_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "c_21"))
    private Account account;
    @Column(name = "password")
    private String stringPassword;
    @Version
    private long version;

    public Password() {
    }

    public Password(Account account, String stringPassword) {
        this.account = account;
        this.stringPassword = stringPassword;
    }

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
        Password pass = (Password) o;
        return Objects.equals(account, pass.getAccount()) && Objects.equals(stringPassword, pass.getStringPassword());
    }

    @Override
    public int hashCode() {
        return Objects.hash(account, stringPassword);
    }
}
