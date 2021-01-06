package com.getjavajob.training.yarginy.socialnetwork.common.models.password;

import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;

import java.util.Objects;

public class PasswordImpl implements Password {
    private Account account;
    private String password;

    @Override
    public Account getAccount() {
        return account;
    }

    @Override
    public void setAccount(Account account) {
        this.account = account;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
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
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PasswordImpl password1 = (PasswordImpl) o;
        return Objects.equals(account, password1.account) && Objects.equals(password, password1.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(account, password);
    }
}
