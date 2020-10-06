package com.getjavajob.training.yarginy.socialnetwork.service;


import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.phone.Phone;
import com.getjavajob.training.yarginy.socialnetwork.dao.batchmodeldao.BatchDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.factories.DbFactory;
import com.getjavajob.training.yarginy.socialnetwork.dao.factories.connector.Transaction;
import com.getjavajob.training.yarginy.socialnetwork.dao.factories.connector.TransactionManager;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.Dao;
import com.getjavajob.training.yarginy.socialnetwork.dao.relationsdao.manytomany.selfrelated.SelfManyToManyDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.relationsdao.onetomany.OneToManyDao;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import static com.getjavajob.training.yarginy.socialnetwork.dao.factories.AbstractDbFactory.getDbFactory;
import static java.util.Objects.isNull;

public class AccountServiceImpl implements AccountService {
    private final DbFactory dbFactory;
    private final Dao<Account> accountDao;
    private final Dao<Phone> phoneDao;
    private final OneToManyDao<Account, Phone> accountsPhonesDao;
    private final SelfManyToManyDao<Account> friendshipDao;
    private final BatchDao<Phone> phoneBatchDao;
    private final TransactionManager transactionManager;
    private Collection<Account> friends;

    public AccountServiceImpl() {
        dbFactory = getDbFactory();
        accountDao = dbFactory.getAccountDao();
        friendshipDao = dbFactory.getFriendshipDao(accountDao);
        phoneDao = dbFactory.getPhoneDao();
        accountsPhonesDao = dbFactory.getAccountsPhones(accountDao, phoneDao);
        transactionManager = dbFactory.getTransactionManager();
        phoneBatchDao = dbFactory.getBatchPhoneDao();
    }

    public AccountServiceImpl(Dao<Account> accountDao, SelfManyToManyDao<Account> friendshipDao, Dao<Phone> phoneDao,
                              OneToManyDao<Account, Phone> accountsPhonesDao, TransactionManager transactionManager,
                              BatchDao<Phone> phoneBatchDao) {
        dbFactory = null;
        this.accountDao = accountDao;
        this.friendshipDao = friendshipDao;
        this.phoneDao = phoneDao;
        this.accountsPhonesDao = accountsPhonesDao;
        this.transactionManager = transactionManager;
        this.phoneBatchDao = phoneBatchDao;
    }

    public Account getAccount(int id) {
        return accountDao.select(id);
    }

    public Account getAccount(Account account) {
        return accountDao.select(account);
    }

    public boolean createAccount(Account account, Collection<Phone> phones) {
        //todo
        try (Transaction transaction = transactionManager.getTransaction()) {
            boolean creation = accountDao.create(account);
            phoneBatchDao.create(phones);
            transaction.commit();
            return creation;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean updateAccount(Account account) {
        return accountDao.update(account);
    }

    public boolean deleteAccount(Account account) {
        return accountDao.delete(account);
    }

    public boolean addFriend(Account account, Account friend) {
        if (friendshipDao.create(account, friend)) {
            if (!isNull(friends) && !friends.add(friend)) {
                friends = null;
            }
            return true;
        }
        return false;
    }

    public boolean removeFriend(Account account, Account friend) {
        if (friendshipDao.delete(account, friend)) {
            if (!isNull(friends) && !friends.remove(friend)) {
                friends = null;
            }
            return true;
        }
        return false;
    }

    public Collection<Account> getFriends(Account account) {
        if (isNull(friends)) {
            friends = friendshipDao.select(account);
        }
        return friends;
    }

    @Override
    public Collection<Account> getAll(Account account) {
        return accountDao.selectAll();
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
        return accountsPhonesDao.selectMany(account);
    }

    @Override
    public Map<Account, Collection<Phone>> getAllWithPhones() {
        Collection<Phone> allPhones = phoneDao.selectAll();
        Map<Account, Collection<Phone>> accountsPhones = new HashMap<>();
        allPhones.forEach(a -> {
            Collection<Phone> phones;
            if (accountsPhones.containsKey(a.getOwner())) {
                phones = accountsPhones.get(a.getOwner());
            } else {
                phones = new HashSet<>();
                accountsPhones.put(a.getOwner(), phones);
            }
            phones.add(a);
        });
        return accountsPhones;
    }
}
