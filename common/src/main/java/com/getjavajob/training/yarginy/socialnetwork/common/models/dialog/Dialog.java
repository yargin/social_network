package com.getjavajob.training.yarginy.socialnetwork.common.models.dialog;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Model;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.Objects;

import static java.util.Objects.isNull;

@Component
@Scope("prototype")
@Entity
@Table(name = "dialogs")
public class Dialog implements Model {
    @Id
    @GeneratedValue
    private long id;
    @ManyToOne
    @JoinColumn(name = "first_id", referencedColumnName = "id")
    private Account firstAccount;
    @ManyToOne
    @JoinColumn(name = "second_id", referencedColumnName = "id")
    private Account secondAccount;

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
        if (secondAccount != null && firstAccount.getId() > secondAccount.getId()) {
            this.firstAccount = secondAccount;
            this.secondAccount = firstAccount;
        } else if (this.firstAccount != null) {
            this.secondAccount = this.firstAccount;
            this.firstAccount = firstAccount;
        } else {
            this.firstAccount = firstAccount;
        }
    }

    public Account getSecondAccount() {
        return secondAccount;
    }

    public void setSecondAccount(Account secondAccount) {
        if (firstAccount != null && firstAccount.getId() > secondAccount.getId()) {
            this.secondAccount = firstAccount;
            this.firstAccount = secondAccount;
        } else if (this.secondAccount != null) {
            this.firstAccount = this.secondAccount;
            this.secondAccount = secondAccount;
        } else {
            this.secondAccount = secondAccount;
        }
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
