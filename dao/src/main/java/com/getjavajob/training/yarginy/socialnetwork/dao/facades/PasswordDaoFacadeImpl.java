package com.getjavajob.training.yarginy.socialnetwork.dao.facades;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Password;
import com.getjavajob.training.yarginy.socialnetwork.dao.models.Dao;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component("passwordDaoFacade")
public class PasswordDaoFacadeImpl implements PasswordDaoFacade {
    private final Dao<Password> passwordDao;

    public PasswordDaoFacadeImpl(@Qualifier("jpaPasswordDao") Dao<Password> passwordDao) {
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
        return passwordDao.update(password);
//        return passwordDao.update(password, storedPassword);
    }

    @Override
    public boolean delete(Password password) {
        return passwordDao.delete(password);
    }

    @Override
    public Password getNullPassword() {
        return passwordDao.getNullModel();
    }
}
