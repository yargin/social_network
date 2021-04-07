package com.getjavajob.training.yarginy.socialnetwork.dao.jdbctemplates.facades;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Password;

import java.io.Serializable;

public interface PasswordDaoFacade extends Serializable {
    Password select(Password password);

    boolean create(Password password);

    boolean update(Password password, Password storedPassword);

    Password getNullPassword();
}
