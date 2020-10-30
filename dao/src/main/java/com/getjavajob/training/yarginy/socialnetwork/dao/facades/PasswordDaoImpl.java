package com.getjavajob.training.yarginy.socialnetwork.dao.facades;

import com.getjavajob.training.yarginy.socialnetwork.common.models.password.Password;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.Dao;

import static com.getjavajob.training.yarginy.socialnetwork.dao.factories.AbstractDbFactory.getDbFactory;

public class PasswordDaoImpl implements PasswordDao {
    private final Dao<Password> passwordDao = getDbFactory().getPasswordDao();

    @Override
    public Password select(Password password) {
        return passwordDao.select(password);
    }

    @Override
    public boolean create(Password password) {
        return passwordDao.create(password);
    }

    @Override
    public boolean update(Password password, Password storedPassword) {
        return passwordDao.update(password, storedPassword);
    }

    @Override
    public Password getNullPassword() {
        return passwordDao.getNullEntity();
    }
}
