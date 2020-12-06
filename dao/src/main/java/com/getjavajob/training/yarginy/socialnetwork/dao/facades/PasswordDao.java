package com.getjavajob.training.yarginy.socialnetwork.dao.facades;

import com.getjavajob.training.yarginy.socialnetwork.common.models.password.Password;

import java.io.Serializable;

public interface PasswordDao extends Serializable {
    Password select(Password password);

    boolean create(Password password);

    boolean update(Password password, Password storedPassword);

    Password getNullPassword();
}
