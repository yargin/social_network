package com.getjavajob.training.yarginy.socialnetwork.service;

import com.getjavajob.training.yarginy.socialnetwork.common.exceptions.IncorrectData;
import com.getjavajob.training.yarginy.socialnetwork.common.exceptions.IncorrectDataException;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.AccountImpl;
import com.getjavajob.training.yarginy.socialnetwork.common.models.password.Password;
import com.getjavajob.training.yarginy.socialnetwork.common.models.password.PasswordImpl;
import com.getjavajob.training.yarginy.socialnetwork.common.models.phone.Phone;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.*;
import com.getjavajob.training.yarginy.socialnetwork.dao.factories.connectionpool.Transaction;
import com.getjavajob.training.yarginy.socialnetwork.service.dto.AccountInfoDTO;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Collection;

import static com.getjavajob.training.yarginy.socialnetwork.common.models.NullEntitiesFactory.getNullPassword;
import static com.getjavajob.training.yarginy.socialnetwork.common.utils.DataHandleHelper.encrypt;

public class AuthServiceImpl implements AuthService {
    private final TransactionManager transactionManager;
    private final AccountDao accountDao;
    private final PasswordDao passwordDao;
    private final PhoneDao phoneDao;

    public AuthServiceImpl() {
        this(new TransactionManager(), new AccountDaoImpl(), new PasswordDaoImpl(), new PhoneDaoImpl());
    }

    public AuthServiceImpl(TransactionManager transactionManager, AccountDao accountDao, PasswordDao passwordDao,
                           PhoneDao phoneDao) {
        this.transactionManager = transactionManager;
        this.accountDao = accountDao;
        this.passwordDao = passwordDao;
        this.phoneDao = phoneDao;
    }

    @Override
    public boolean register(AccountInfoDTO accountInfoDTO, Password password) {
        return register(accountInfoDTO.getAccount(), accountInfoDTO.getPhones(), password);
    }

    @Override
    public boolean register(Account account, Collection<Phone> phones, Password password) {
        try (Transaction transaction = transactionManager.getTransaction()) {
            account.setRegistrationDate(Date.valueOf(LocalDate.now()));
            if (!accountDao.create(account)) {
                transaction.rollback();
                throw new IncorrectDataException(IncorrectData.EMAIL_DUPLICATE);
            }
            if (!phoneDao.create(phones)) {
                transaction.rollback();
                throw new IncorrectDataException(IncorrectData.PHONE_DUPLICATE);
            }
            if (!passwordDao.create(password)) {
                transaction.rollback();
                throw new IllegalStateException();
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
}
