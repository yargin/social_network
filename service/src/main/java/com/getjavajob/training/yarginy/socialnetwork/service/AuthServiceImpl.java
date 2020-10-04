package com.getjavajob.training.yarginy.socialnetwork.service;

import com.getjavajob.training.yarginy.socialnetwork.common.exceptions.IncorrectData;
import com.getjavajob.training.yarginy.socialnetwork.common.exceptions.IncorrectDataException;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.AccountImpl;
import com.getjavajob.training.yarginy.socialnetwork.common.models.password.Password;
import com.getjavajob.training.yarginy.socialnetwork.common.models.password.PasswordImpl;
import com.getjavajob.training.yarginy.socialnetwork.common.models.phone.Phone;
import com.getjavajob.training.yarginy.socialnetwork.dao.batchmodeldao.BatchDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.factories.DbFactory;
import com.getjavajob.training.yarginy.socialnetwork.dao.factories.connector.Transaction;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.Dao;

import java.time.LocalDate;
import java.util.Collection;

import static com.getjavajob.training.yarginy.socialnetwork.common.models.NullEntitiesFactory.getNullPassword;
import static com.getjavajob.training.yarginy.socialnetwork.common.utils.DataHandleHelper.encrypt;
import static com.getjavajob.training.yarginy.socialnetwork.dao.factories.AbstractDbFactory.getDbFactory;

public class AuthServiceImpl implements AuthService {
    private final Dao<Account> accountDao;
    private final Dao<Password> passwordDao;
    private final BatchDao<Phone> phoneDao;
    private final Transaction transaction;

    public AuthServiceImpl() {
        DbFactory dbFactory = getDbFactory();
        accountDao = dbFactory.getAccountDao();
        passwordDao = dbFactory.getPasswordDao();
        phoneDao = dbFactory.getBatchPhoneDao();
        transaction = dbFactory.getTransaction();
    }

    @Override
    public boolean register(Account account, Collection<Phone> phones, Password password) {
        //make autocloseable???
        //change to manager.startTransaction
        transaction.begin();
        account.setRegistrationDate(LocalDate.now());
        accountDao.create(account);
        phoneDao.create(phones);
        passwordDao.create(password);
        transaction.commit();
        //change to close()
        transaction.end();
        return true;
    }

    @Override
    public Account login(String email, String password) {
        Password passwordObject = new PasswordImpl();
        Account account = new AccountImpl();
        account.setEmail(email);
        passwordObject.setAccount(account);
        passwordObject.setPassword(password);
        passwordObject = passwordDao.select(passwordObject);
        if (passwordObject.equals(getNullPassword())) {
            throw new IncorrectDataException(IncorrectData.WRONG_EMAIL);
        }
        if (!passwordObject.getPassword().equals(encrypt(password))) {
            throw new IncorrectDataException(IncorrectData.WRONG_PASSWORD);
        }
        return passwordObject.getAccount();
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
