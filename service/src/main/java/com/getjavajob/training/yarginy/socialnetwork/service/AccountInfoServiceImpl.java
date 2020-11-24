package com.getjavajob.training.yarginy.socialnetwork.service;

import com.getjavajob.training.yarginy.socialnetwork.common.exceptions.IncorrectData;
import com.getjavajob.training.yarginy.socialnetwork.common.exceptions.IncorrectDataException;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.accountphoto.AccountPhoto;
import com.getjavajob.training.yarginy.socialnetwork.common.models.phone.Phone;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.*;
import com.getjavajob.training.yarginy.socialnetwork.dao.factories.connectionpool.Transaction;
import com.getjavajob.training.yarginy.socialnetwork.service.dto.AccountInfoDTO;

import java.util.Collection;

import static com.getjavajob.training.yarginy.socialnetwork.common.models.NullEntitiesFactory.getNullAccountPhoto;
import static java.util.Objects.isNull;

public class AccountInfoServiceImpl implements AccountInfoService {
    private final AccountDao accountDao;
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
        Collection<Phone> phones = phoneDao.selectPhonesByOwner(storedAccount.getId());
        AccountPhoto accountPhoto = accountPhotoDao.select(storedAccount);
        return new AccountInfoDTO(storedAccount, accountPhoto, phones);
    }

    @Override
    public AccountInfoDTO select(long id) {
        Account storedAccount = accountDao.select(id);
        Collection<Phone> phones = phoneDao.selectPhonesByOwner(storedAccount.getId());
        AccountPhoto accountPhoto = accountPhotoDao.select(storedAccount);
        return new AccountInfoDTO(storedAccount, accountPhoto, phones);
    }

    @Override
    public boolean update(AccountInfoDTO accountInfo, AccountInfoDTO storedAccountInfo) {
        try (Transaction transaction = transactionManager.getTransaction()) {
            Account account = accountInfo.getAccount();
            if (!accountDao.update(account, storedAccountInfo.getAccount())) {
                throw new IncorrectDataException(IncorrectData.EMAIL_DUPLICATE);
            }

            if (!phoneDao.update(storedAccountInfo.getPhones(), accountInfo.getPhones())) {
                throw new IncorrectDataException(IncorrectData.PHONE_DUPLICATE);
            }

            AccountPhoto accountPhoto = accountInfo.getAccountPhoto();
            if (!isNull(accountPhoto)) {
                accountPhoto.setOwner(account);
                if (getNullAccountPhoto().equals(storedAccountInfo.getAccountPhoto())) {
                    if (!accountPhotoDao.create(accountPhoto)) {
                        throw new IncorrectDataException(IncorrectData.UPLOADING_ERROR);
                    }
                } else {
                    if (!accountPhotoDao.update(accountPhoto, storedAccountInfo.getAccountPhoto())) {
                        throw new IncorrectDataException(IncorrectData.UPLOADING_ERROR);
                    }
                }
            }
            transaction.commit();
            return true;
        } catch (IncorrectDataException e) {
            throw e;
        } catch (Exception e) {
            return false;
        }
    }
}
