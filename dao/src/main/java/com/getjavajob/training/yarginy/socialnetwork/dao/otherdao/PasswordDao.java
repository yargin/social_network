package com.getjavajob.training.yarginy.socialnetwork.dao.otherdao;

import com.getjavajob.training.yarginy.socialnetwork.common.entities.account.Account;

/**
 * provides {@link Account} authorisation handling
 */
public interface PasswordDao {
    boolean isRegistered(String email, String password);

    Account getAccount(String email, String password);

    boolean changePassword(Account account, String oldPassword, String newPassword);
}
