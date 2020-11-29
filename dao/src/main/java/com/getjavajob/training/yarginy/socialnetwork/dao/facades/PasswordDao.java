package com.getjavajob.training.yarginy.socialnetwork.dao.facades;

import com.getjavajob.training.yarginy.socialnetwork.common.models.password.Password;

public interface PasswordDao {
    Password select(Password password);

    boolean create(Password password);

    boolean update(Password password, Password storedPassword);

    Password getNullPassword();
}
