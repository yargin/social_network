package com.getjavajob.training.yarginy.socialnetwork.dao.facades;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Password;
import com.getjavajob.training.yarginy.socialnetwork.dao.models.Dao;
import com.getjavajob.training.yarginy.socialnetwork.dao.models.PasswordDao;
import org.springframework.stereotype.Component;

@Component("passwordDaoFacade")
public class PasswordDaoFacadeImpl implements PasswordDaoFacade {
    private final Dao<Password> passwordDao;

    public PasswordDaoFacadeImpl(Dao<Password> passwordDao) {
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
    public boolean update(Password password) {
        return passwordDao.update(password);
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
