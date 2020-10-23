package com.getjavajob.training.yarginy.socialnetwork.service;

import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.accountphoto.AccountPhoto;
import com.getjavajob.training.yarginy.socialnetwork.common.models.phone.Phone;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.*;
import com.getjavajob.training.yarginy.socialnetwork.dao.factories.connectionpool.Transaction;
import com.getjavajob.training.yarginy.socialnetwork.service.dto.AccountInfoDTO;

import java.util.Collection;

public class AccountInfoServiceImpl implements AccountInfoService {
    private final AccountDao accountDao ;
    private final PhoneDao phoneDao;
    private final AccountPhotoDao accountPhotoDao;
    private final TransactionManager transactionManager;

    public AccountInfoServiceImpl() {
        this(new TransactionManager(), new AccountDaoImpl(), new PhoneDaoImpl(), new AccountPhotoDaoImpl());
    }

    public AccountInfoServiceImpl(TransactionManager transactionManager, AccountDao accountDao, PhoneDao phoneDao,
                                  AccountPhotoDao accountPhotoDao) {
        this.transactionManager = transactionManager;
        this.accountDao = accountDao;
        this.phoneDao = phoneDao;
        this.accountPhotoDao = accountPhotoDao;
    }

    @Override
    public AccountInfoDTO select(Account account) {
        Account storedAccount = accountDao.select(account);
        Collection<Phone> phones = phoneDao.selectPhonesByOwner(storedAccount);
        AccountPhoto accountPhoto = accountPhotoDao.select(storedAccount);
        return new AccountInfoDTO(storedAccount, accountPhoto, phones);
    }

    @Override
    public AccountInfoDTO select(long id) {
        Account storedAccount = accountDao.select(id);
        Collection<Phone> phones = phoneDao.selectPhonesByOwner(storedAccount);
        AccountPhoto accountPhoto = accountPhotoDao.select(storedAccount);
        return new AccountInfoDTO(storedAccount, accountPhoto, phones);
    }

    @Override
    public boolean update(AccountInfoDTO accountInfoDTO) {
        try (Transaction transaction = transactionManager.getTransaction()) {
            Account account = accountInfoDTO.getAccount();
            if (accountDao.update(account)) {
                throw new Exception();
            }
            if (phoneDao.update(accountInfoDTO.getPhones(), account)) {
                throw new Exception();
            }
            if (accountPhotoDao.update(accountInfoDTO.getAccountPhoto())) {
                throw new Exception();
            }
            transaction.commit();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
