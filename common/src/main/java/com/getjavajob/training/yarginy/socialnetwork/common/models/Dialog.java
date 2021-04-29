package com.getjavajob.training.yarginy.socialnetwork.common.models;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedEntityGraph;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;
import java.util.Objects;

import static java.util.Objects.isNull;

@Component
@Scope("prototype")
@Entity
@Table(name = "dialogs", uniqueConstraints = {@UniqueConstraint(name = "dialogs_accounts_id",
        columnNames = {"first_id", "second_id"})})
@NamedEntityGraph(name = "graph.Dialog.allProperties", includeAllAttributes = true)
public class Dialog implements Model {
    @Id
    @GeneratedValue
    private long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "first_id", foreignKey = @ForeignKey(name = "dialogs_accounts_id_fk"))
    private Account firstAccount;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "second_id", foreignKey = @ForeignKey(name = "dialogs_accounts_id_fk_2"))
    private Account secondAccount;
    @Version
    private long version;

    public Dialog() {
    }

    public Dialog(Account firstAccount, Account secondAccount) {
        this.firstAccount = firstAccount;
        this.secondAccount = secondAccount;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Account getFirstAccount() {
        return firstAccount;
    }

    public void setFirstAccount(Account firstAccount) {
        this.firstAccount = firstAccount;
    }

    public Account getSecondAccount() {
        return secondAccount;
    }

    public void setSecondAccount(Account secondAccount) {
        this.secondAccount = secondAccount;
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
        if (!(o instanceof Dialog)) {
            return false;
        }
        Dialog dialog = (Dialog) o;
        return Objects.equals(getFirstAccount(), dialog.getFirstAccount()) &&
                Objects.equals(getSecondAccount(), dialog.getSecondAccount()) ||
                Objects.equals(getFirstAccount(), dialog.getSecondAccount()) &&
                        Objects.equals(getSecondAccount(), dialog.getFirstAccount());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFirstAccount(), getSecondAccount());
    }
}
