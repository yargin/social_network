package com.getjavajob.training.yarginy.socialnetwork.service;

import com.getjavajob.training.yarginy.socialnetwork.common.exceptions.IncorrectData;
import com.getjavajob.training.yarginy.socialnetwork.common.exceptions.IncorrectDataException;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.phone.Phone;
import com.getjavajob.training.yarginy.socialnetwork.dao.factories.connector.Transaction;
import com.getjavajob.training.yarginy.socialnetwork.dao.factories.connector.TransactionManager;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.facades.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import static java.util.Objects.isNull;

public class AccountServiceImpl implements AccountService {
    private final AccountDao accountDao;
    private final PhoneDao phoneDao;
    private final FriendshipDao friendshipDao;
    private final AccountsInGroupsDao accountsInGroupsDao;
    private final TransactionManager transactionManager;
    private Collection<Account> friends = new ArrayList<>();

    public AccountServiceImpl() {
        accountDao = new AccountDaoImpl();
        phoneDao = new PhoneDaoImpl();
        friendshipDao = new FriendshipDaoImpl();
        accountsInGroupsDao = new AccountsInGroupsDaoImpl();
        transactionManager = TransactionManagerFactory.getTransactionManager();
    }

    public AccountServiceImpl(AccountDao accountDao, PhoneDao phoneDao, FriendshipDao friendshipDao,
                              AccountsInGroupsDao accountsInGroupsDao, TransactionManager transactionManager) {
        this.accountDao = accountDao;
        this.phoneDao = phoneDao;
        this.friendshipDao = friendshipDao;
        this.accountsInGroupsDao = accountsInGroupsDao;
        this.transactionManager = transactionManager;
    }

    @Override
    public Account getAccount(int id) {
        return accountDao.select(id);
    }

    @Override
    public Account getAccount(Account account) {
        return accountDao.select(account);
    }

    @Override
    public boolean createAccount(Account account, Collection<Phone> phones) {
        try (Transaction transaction = transactionManager.getTransaction()) {
            account.setRegistrationDate(LocalDate.now());
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
    public boolean updateAccount(Account account) {
        return accountDao.update(account);
    }

    @Override
    public boolean deleteAccount(Account account) {
        return accountDao.delete(account);
    }

    @Override
    public boolean addFriend(Account account, Account friend) {
        if (friendshipDao.createFriendship(account, friend)) {
            if (!isNull(friends) && !friends.add(friend)) {
                friends = null;
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean removeFriend(Account account, Account friend) {
        if (friendshipDao.removeFriendship(account, friend)) {
            if (!isNull(friends) && !friends.remove(friend)) {
                friends = null;
            }
            return true;
        }
        return false;
    }

    @Override
    public Collection<Account> getAll(Account account) {
        return accountDao.selectAll();
    }

    @Override
    public Collection<Account> getFriends(Account account) {
        return friendshipDao.selectFriends(account);
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
    public Collection<Phone> getPhones(Account account) {
        return phoneDao.selectPhonesByOwner(account);
    }

    @Override
    public Map<Account, Collection<Phone>> getAllWithPhones() {
        return null;
    }
}
