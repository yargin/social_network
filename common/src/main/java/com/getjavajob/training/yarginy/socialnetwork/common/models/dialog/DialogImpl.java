package com.getjavajob.training.yarginy.socialnetwork.common.models.dialog;

import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;

import java.util.Objects;

import static java.util.Objects.isNull;

public class DialogImpl implements Dialog {
    private long id;
    private Account firstAccount;
    private Account secondAccount;

    public DialogImpl() {
    }

    public DialogImpl(Account firstAccount, Account secondAccount) {
        this.firstAccount = firstAccount;
        this.secondAccount = secondAccount;
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
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
    public boolean equals(Object o) {
        if (isNull(o)) {
            return false;
        }
        if (this == o) return true;
        if (!(o instanceof Dialog)) return false;
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
