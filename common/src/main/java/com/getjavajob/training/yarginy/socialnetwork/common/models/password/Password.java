package com.getjavajob.training.yarginy.socialnetwork.common.models.password;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Entity;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;

/**
 * special {@link Entity} that doesn't have id number, so {@link #getId()} & {@link #setId(long)} throw
 * {@link UnsupportedOperationException}
 */
public interface Password extends Entity {
    Account getAccount();

    void setAccount(Account account);

    String getPassword();

    /**
     * checks & encrypts password before assignment
     *
     * @param password to check, encrypt & assign
     */
    void setPassword(String password);
}
