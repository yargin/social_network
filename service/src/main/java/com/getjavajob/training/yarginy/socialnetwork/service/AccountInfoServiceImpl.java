package com.getjavajob.training.yarginy.socialnetwork.service;

import com.getjavajob.training.yarginy.socialnetwork.common.exceptions.IncorrectData;
import com.getjavajob.training.yarginy.socialnetwork.common.exceptions.IncorrectDataException;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.phone.Phone;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.*;
import com.getjavajob.training.yarginy.socialnetwork.dao.factories.connectionpool.Transaction;
import com.getjavajob.training.yarginy.socialnetwork.service.dto.AccountInfoDTO;

import java.util.Collection;

public class AccountInfoServiceImpl implements AccountInfoService {
    private final AccountDao accountDao;
    private final PhoneDao phoneDao;
    private final TransactionManager transactionManager;

    public AccountInfoServiceImpl() {
        this(new TransactionManagerImpl(), new AccountDaoImpl(), new PhoneDaoImpl());
    }

    public AccountInfoServiceImpl(TransactionManager transactionManager, AccountDao accountDao, PhoneDao phoneDao) {
        this.transactionManager = transactionManager;
        this.accountDao = accountDao;
        this.phoneDao = phoneDao;
    }

    @Override
    public AccountInfoDTO select(Account account) {
        Account storedAccount = accountDao.select(account);
        Collection<Phone> phones = phoneDao.selectPhonesByOwner(storedAccount.getId());
        return new AccountInfoDTO(storedAccount, phones);
    }

    @Override
    public AccountInfoDTO select(long id) {
        Account storedAccount = accountDao.select(id);
        Collection<Phone> phones = phoneDao.selectPhonesByOwner(storedAccount.getId());
        return new AccountInfoDTO(storedAccount, phones);
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
            transaction.commit();
            return true;
        } catch (IncorrectDataException e) {
            throw e;
        } catch (Exception e) {
            return false;
        }
    }
}
