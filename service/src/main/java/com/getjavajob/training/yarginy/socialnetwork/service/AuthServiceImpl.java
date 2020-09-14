package com.getjavajob.training.yarginy.socialnetwork.service;

import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.password.Password;
import com.getjavajob.training.yarginy.socialnetwork.common.models.phone.Phone;
import com.getjavajob.training.yarginy.socialnetwork.dao.factories.DbFactory;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.Dao;

import java.util.Collection;

import static com.getjavajob.training.yarginy.socialnetwork.dao.factories.AbstractDbFactory.getDbFactory;

public class AuthServiceImpl implements AuthService {
    private final DbFactory dbFactory;
    private final Dao<Account> accountDao;
    private final Dao<Password> passwordDao;
    private final Dao<Phone> phoneDao;

    public AuthServiceImpl() {
        dbFactory = getDbFactory();
        accountDao = dbFactory.getAccountDao();
        passwordDao = dbFactory.getPasswordDao();
        phoneDao = dbFactory.getPhoneDao();
    }

    @Override
    public boolean register(Account account, Collection<Phone> phones, Password password) {
        boolean transactionChain;
        transactionChain = accountDao.create(account);
        transactionChain = passwordDao.create(password);
//        transactionChain = phoneDao.create();
        return transactionChain;
    }

    @Override
    public Account login(Password password) {
        return null;
    }

    @Override
    public boolean logout(Account account) {
        return false;
    }

    @Override
    public boolean delete(Account account) {
        return false;
    }
}
