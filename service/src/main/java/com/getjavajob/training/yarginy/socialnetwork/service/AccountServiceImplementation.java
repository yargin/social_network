package com.getjavajob.training.yarginy.socialnetwork.service;

import com.getjavajob.training.yarginy.socialnetwork.common.exceptions.IncorrectData;
import com.getjavajob.training.yarginy.socialnetwork.common.exceptions.IncorrectDataException;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.phone.Phone;
import com.getjavajob.training.yarginy.socialnetwork.dao.factories.connector.Transaction;
import com.getjavajob.training.yarginy.socialnetwork.dao.factories.connector.TransactionManager;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.facades.*;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Map;

public class AccountServiceImplementation implements AccountService {
    private final AccountDao accountDao = new AccountDaoImpl();
    private final PhoneDao phoneDao = new PhoneDaoImpl();
    private final FriendshipDao friendshipDao = new FriendshipDaoImpl();
    private final AccountsInGroupsDao accountsInGroupsDao = new AccountsInGroupsDaoImpl();
    private final TransactionManager transactionManager = TransactionManagerFactory.getTransactionManager();

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
        return false;
    }

    @Override
    public boolean deleteAccount(Account account) {
        return false;
    }

    @Override
    public boolean addFriend(Account account, Account friend) {
        return false;
    }

    @Override
    public boolean removeFriend(Account account, Account friend) {
        return false;
    }

    @Override
    public Collection<Account> getAll(Account account) {
        return null;
    }

    @Override
    public Collection<Account> getFriends(Account account) {
        return null;
    }

    @Override
    public boolean addPhone(Account account, Phone phone) {
        return false;
    }

    @Override
    public boolean removePhone(Phone phone) {
        return false;
    }

    @Override
    public Collection<Phone> getPhones(Account account) {
        return null;
    }

    @Override
    public Map<Account, Collection<Phone>> getAllWithPhones() {
        return null;
    }
}
