package com.getjavajob.training.yarginy.socialnetwork.service;


import com.getjavajob.training.yarginy.socialnetwork.common.exceptions.IncorrectData;
import com.getjavajob.training.yarginy.socialnetwork.common.exceptions.IncorrectDataException;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.phone.Phone;
import com.getjavajob.training.yarginy.socialnetwork.dao.batchmodeldao.BatchDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.TransactionManager;
import com.getjavajob.training.yarginy.socialnetwork.dao.factories.DbFactory;
import com.getjavajob.training.yarginy.socialnetwork.dao.factories.connectionpool.Transaction;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.Dao;
import com.getjavajob.training.yarginy.socialnetwork.dao.relationsdao.manytomany.selfrelated.SelfManyToManyDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.relationsdao.onetomany.OneToManyDao;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import static com.getjavajob.training.yarginy.socialnetwork.dao.factories.AbstractDbFactory.getDbFactory;
import static java.util.Objects.isNull;

public class AccountServiceImplOld implements AccountService {
    private final DbFactory dbFactory;
    private final Dao<Account> accountDao;
    private final OneToManyDao<Account, Phone> accountsPhonesDao;
    private final SelfManyToManyDao<Account> friendshipDao;
    private final BatchDao<Phone> phoneBatchDao;
    private Collection<Account> friends;

    public AccountServiceImplOld() {
        dbFactory = getDbFactory();
        accountDao = dbFactory.getAccountDao();
        friendshipDao = dbFactory.getFriendshipDao(accountDao);
        phoneBatchDao = dbFactory.getBatchPhoneDao();
        accountsPhonesDao = dbFactory.getAccountsPhones(accountDao, phoneBatchDao);
    }

    public AccountServiceImplOld(Dao<Account> accountDao, SelfManyToManyDao<Account> friendshipDao,
                                 OneToManyDao<Account, Phone> accountsPhonesDao, BatchDao<Phone> phoneBatchDao) {
        dbFactory = null;
        this.accountDao = accountDao;
        this.friendshipDao = friendshipDao;
        this.accountsPhonesDao = accountsPhonesDao;
        this.phoneBatchDao = phoneBatchDao;
    }

    public Account getAccount(int id) {
        return accountDao.select(id);
    }

    public Account getAccount(Account account) {
        return accountDao.select(account);
    }

    public boolean createAccount(Account account, Collection<Phone> phones) {
        try (Transaction transaction = TransactionManager.getTransaction()) {
            account.setRegistrationDate(LocalDate.now());
            if (!accountDao.create(account)) {
                throw new IncorrectDataException(IncorrectData.EMAIL_DUPLICATE);
            }
            if (!phoneBatchDao.create(phones)) {
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
        return phoneBatchDao.create(phone);
    }

    @Override
    public boolean removePhone(Phone phone) {
        return phoneBatchDao.delete(phone);
    }

    @Override
    public Collection<Phone> getPhones(Account account) {
        return accountsPhonesDao.selectMany(account);
    }

    @Override
    public Map<Account, Collection<Phone>> getAllWithPhones() {
        Collection<Phone> allPhones = phoneBatchDao.selectAll();
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
