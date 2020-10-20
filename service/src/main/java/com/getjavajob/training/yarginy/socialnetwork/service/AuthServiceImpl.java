package com.getjavajob.training.yarginy.socialnetwork.service;

import com.getjavajob.training.yarginy.socialnetwork.common.exceptions.IncorrectData;
import com.getjavajob.training.yarginy.socialnetwork.common.exceptions.IncorrectDataException;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.AccountImpl;
import com.getjavajob.training.yarginy.socialnetwork.common.models.accountphoto.AccountPhoto;
import com.getjavajob.training.yarginy.socialnetwork.common.models.password.Password;
import com.getjavajob.training.yarginy.socialnetwork.common.models.password.PasswordImpl;
import com.getjavajob.training.yarginy.socialnetwork.common.models.phone.Phone;
import com.getjavajob.training.yarginy.socialnetwork.dao.batchmodeldao.BatchDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.dependedmodeldao.OwnedModelDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.TransactionManager;
import com.getjavajob.training.yarginy.socialnetwork.dao.factories.DbFactory;
import com.getjavajob.training.yarginy.socialnetwork.dao.factories.connectionpool.Transaction;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.Dao;

import java.time.LocalDate;
import java.util.Collection;

import static com.getjavajob.training.yarginy.socialnetwork.common.models.NullEntitiesFactory.getNullPassword;
import static com.getjavajob.training.yarginy.socialnetwork.common.utils.DataHandleHelper.encrypt;
import static com.getjavajob.training.yarginy.socialnetwork.dao.factories.AbstractDbFactory.getDbFactory;
import static java.util.Objects.isNull;

public class AuthServiceImpl implements AuthService {
    private final TransactionManager transactionManager;
    private final Dao<Account> accountDao;
    private final Dao<Password> passwordDao;
    private final BatchDao<Phone> phoneDao;
    private final OwnedModelDao<Account, AccountPhoto> accountPhotoDao;

    public AuthServiceImpl() {
        DbFactory dbFactory = getDbFactory();
        accountDao = dbFactory.getAccountDao();
        passwordDao = dbFactory.getPasswordDao();
        phoneDao = dbFactory.getBatchPhoneDao();
        accountPhotoDao= dbFactory.getAccountPhotoDao(accountDao);
        transactionManager = new TransactionManager();
    }

    public AuthServiceImpl(TransactionManager transactionManager, Dao<Account> accountDao, Dao<Password> passwordDao,
                           BatchDao<Phone> phoneDao, OwnedModelDao<Account, AccountPhoto> accountPhotoDao) {
        this.transactionManager = transactionManager;
        this.accountDao = accountDao;
        this.passwordDao = passwordDao;
        this.phoneDao = phoneDao;
        this.accountPhotoDao = accountPhotoDao;
    }

    @Override
    public boolean register(Account account, Collection<Phone> phones, Password password, AccountPhoto accountPhoto) {
        try (Transaction transaction = transactionManager.getTransaction()) {
            account.setRegistrationDate(LocalDate.now());
            if (!accountDao.create(account)) {
                throw new IncorrectDataException(IncorrectData.EMAIL_DUPLICATE);
            }
            if (!phoneDao.create(phones)) {
                throw new IncorrectDataException(IncorrectData.PHONE_DUPLICATE);
            }
            passwordDao.create(password);
            transaction.commit();
        } catch (IncorrectDataException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
        }
        try (Transaction transaction = transactionManager.getTransaction()) {
            if (!isNull(accountPhoto)) {
                accountPhoto.setOwner(account);
                if (!accountPhotoDao.create(accountPhoto)) {
                    throw new IncorrectDataException(IncorrectData.UPLOADING_ERROR);
                }
            }
            transaction.commit();
        } catch (IncorrectDataException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
        }
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
