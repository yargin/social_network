package com.getjavajob.training.yarginy.socialnetwork.service;

import com.getjavajob.training.yarginy.socialnetwork.common.exceptions.IncorrectData;
import com.getjavajob.training.yarginy.socialnetwork.common.exceptions.IncorrectDataException;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.accountphoto.AccountPhoto;
import com.getjavajob.training.yarginy.socialnetwork.common.models.phone.Phone;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.*;
import com.getjavajob.training.yarginy.socialnetwork.dao.factories.connectionpool.Transaction;
import com.getjavajob.training.yarginy.socialnetwork.service.dto.AccountInfoDTO;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Map;

public class AccountServiceImpl implements AccountService {
    private final TransactionManager transactionManager;
    private final AccountDao accountDao;
    private final PhoneDao phoneDao;
    private final FriendshipsDao friendshipDao;
    private final AccountPhotoDao accountPhotoDao;

    public AccountServiceImpl() {
        this(new AccountDaoImpl(), new PhoneDaoImpl(), new FriendshipsDaoImpl(), new AccountPhotoDaoImpl(),
                new TransactionManager());
    }

    public AccountServiceImpl(AccountDao accountDao, PhoneDao phoneDao, FriendshipsDao friendshipDao,
                              AccountPhotoDao accountPhotoDao, TransactionManager transactionManager) {
        this.accountDao = accountDao;
        this.phoneDao = phoneDao;
        this.friendshipDao = friendshipDao;
        this.transactionManager = transactionManager;
        this.accountPhotoDao = accountPhotoDao;
    }

    @Override
    public AccountInfoDTO getAccountInfo(long id) {
        Account account = accountDao.select(id);
        Collection<Phone> phones = phoneDao.selectPhonesByOwner(id);
        AccountPhoto accountPhoto = accountPhotoDao.select(account);
        return new AccountInfoDTO(account, accountPhoto, phones);
    }

    @Override
    public Account getAccount(long id) {
        return accountDao.select(id);
    }

    @Override
    public Account getAccount(Account account) {
        return accountDao.select(account);
    }

    @Override
    public boolean createAccount(Account account, Collection<Phone> phones) {
        try (Transaction transaction = transactionManager.getTransaction()) {
            account.setRegistrationDate(Date.valueOf(LocalDate.now()));
            if (!accountDao.create(account)) {
                throw new IncorrectDataException(IncorrectData.EMAIL_DUPLICATE);
            }
            if (!phoneDao.create(phones)) {
                throw new IncorrectDataException(IncorrectData.PHONE_DUPLICATE);
            }
            transaction.commit();
        } catch (IncorrectDataException e) {
            throw e;
        } catch (Exception e) {
            throw new IllegalStateException("couldn't start transaction");
        }
        return true;
    }

    @Override
    public boolean updateAccount(Account account, Account storedAccount) {
        return accountDao.update(account, storedAccount);
    }

    @Override
    public boolean deleteAccount(Account account) {
        return accountDao.delete(account);
    }

    @Override
    public boolean addFriend(long firstId, long secondId) {
        try (Transaction transaction = transactionManager.getTransaction()) {
            if (!friendshipDao.deleteRequest(firstId, secondId)) {
                throw new IncorrectDataException(IncorrectData.WRONG_REQUEST);
            }
            if (!friendshipDao.createFriendship(firstId, secondId)) {
                transaction.rollback();
                throw new IncorrectDataException(IncorrectData.WRONG_REQUEST);
            }
            transaction.commit();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean removeFriend(long firstId, long secondId) {
        try {
            return friendshipDao.removeFriendship(firstId, secondId);
        } catch (IllegalArgumentException e) {
            throw new IncorrectDataException(IncorrectData.WRONG_REQUEST);
        }
    }

    @Override
    public boolean isFriend(long firstId, long secondId) {
        try {
            return friendshipDao.areFriends(firstId, secondId);
        } catch (IllegalArgumentException e) {
            throw new IncorrectDataException(IncorrectData.WRONG_REQUEST);
        }
    }

    @Override
    public Collection<Account> getAll(Account account) {
        return accountDao.selectAll();
    }

    @Override
    public Collection<Account> getFriends(long accountId) {
        try {
            return friendshipDao.selectFriends(accountId);
        } catch (IllegalArgumentException e) {
            throw new IncorrectDataException(IncorrectData.WRONG_REQUEST);
        }
    }

    @Override
    public boolean addPhone(Account account, Phone phone) {
        phone.setOwner(account);
        return phoneDao.create(phone);
    }

    @Override
    public boolean removePhone(Phone phone) {
        return phoneDao.delete(phone);
    }

    @Override
    public Collection<Phone> getPhones(long accountId) {
        return phoneDao.selectPhonesByOwner(accountId);
    }

    @Override
    public Map<Account, Collection<Phone>> getAllWithPhones() {
        return null;
    }

    @Override
    public boolean updatePhones(Collection<Phone> phones, Account account) {
        return false;
    }

    @Override
    public boolean createFriendshipRequest(long requester, long receiver) {
        try {
            return friendshipDao.createRequest(requester, receiver);
        } catch (IllegalArgumentException e) {
            throw new IncorrectDataException(IncorrectData.WRONG_REQUEST);
        }
    }

    @Override
    public boolean deleteFriendshipRequest(long requester, long receiver) {
        try {
            return friendshipDao.deleteRequest(requester, receiver);
        } catch (IllegalArgumentException e) {
            throw new IncorrectDataException(IncorrectData.WRONG_REQUEST);
        }
    }

    @Override
    public Collection<Account> getFriendshipRequests(long receiver) {
        return friendshipDao.selectRequests(receiver);
    }
}
