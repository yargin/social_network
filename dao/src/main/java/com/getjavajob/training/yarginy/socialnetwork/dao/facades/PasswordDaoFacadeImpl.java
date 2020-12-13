package com.getjavajob.training.yarginy.socialnetwork.dao.facades;

import com.getjavajob.training.yarginy.socialnetwork.common.models.password.Password;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.Dao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component("passwordDaoFacade")
public class PasswordDaoFacadeImpl implements PasswordDaoFacade {
    private final Dao<Password> passwordDao;

    @Autowired
    public PasswordDaoFacadeImpl(@Qualifier("passwordDao") Dao<Password> passwordDao) {
        this.passwordDao = passwordDao;
    }

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
