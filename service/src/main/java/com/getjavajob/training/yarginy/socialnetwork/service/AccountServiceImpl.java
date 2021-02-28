package com.getjavajob.training.yarginy.socialnetwork.service;

import com.getjavajob.training.yarginy.socialnetwork.common.exceptions.DataFlowViolationException;
import com.getjavajob.training.yarginy.socialnetwork.common.exceptions.IncorrectData;
import com.getjavajob.training.yarginy.socialnetwork.common.exceptions.IncorrectDataException;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.dialog.Dialog;
import com.getjavajob.training.yarginy.socialnetwork.common.models.phone.Phone;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.AccountDaoFacade;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.DialogDaoFacade;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.FriendshipsDaoFacade;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.PhoneDaoFacade;
import com.getjavajob.training.yarginy.socialnetwork.service.infokeepers.AccountInfoKeeper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Collection;

@Service
public class AccountServiceImpl implements AccountService {
    private final AccountDaoFacade accountDaoFacade;
    private final PhoneDaoFacade phoneDaoFacade;
    private final FriendshipsDaoFacade friendshipDao;
    private final DialogDaoFacade dialogsDao;

    @Autowired
    public AccountServiceImpl(AccountDaoFacade accountDaoFacade, PhoneDaoFacade phoneDaoFacade, FriendshipsDaoFacade friendshipDao,
                              DialogDaoFacade dialogDaoFacade) {
        this.accountDaoFacade = accountDaoFacade;
        this.phoneDaoFacade = phoneDaoFacade;
        this.friendshipDao = friendshipDao;
        this.dialogsDao = dialogDaoFacade;
    }

    @Override
    public AccountInfoKeeper getAccountInfo(long id) {
        Account account = accountDaoFacade.select(id);
        Collection<Phone> phones = phoneDaoFacade.selectPhonesByOwner(id);
        return new AccountInfoKeeper(account, phones);
    }

    @Override
    public Account get(long id) {
        return accountDaoFacade.select(id);
    }

    @Override
    public Account get(Account account) {
        return accountDaoFacade.select(account);
    }

    @Override
    public boolean createAccount(Account account, Collection<Phone> phones) {
        account.setRegistrationDate(Date.valueOf(LocalDate.now()));
        if (!accountDaoFacade.create(account)) {
            throw new IncorrectDataException(IncorrectData.EMAIL_DUPLICATE);
        }
        if (!phoneDaoFacade.create(phones)) {
            throw new IncorrectDataException(IncorrectData.PHONE_DUPLICATE);
        }
        return true;
    }

    @Override
    public boolean updateAccount(Account account, Account storedAccount) {
        return accountDaoFacade.update(account, storedAccount);
    }

    @Override
    public boolean deleteAccount(Account account) {
        return accountDaoFacade.delete(account);
    }

    @Override
    public boolean addFriend(long firstId, long secondId) {
        try {
            addFriendTransactional(firstId, secondId);
        } catch (DataFlowViolationException e) {
            return false;
        }
        return true;
    }

    public void addFriendTransactional(long firstId, long secondId) {
        if (!friendshipDao.deleteRequest(firstId, secondId) || !friendshipDao.createFriendship(firstId, secondId)) {
            throw new DataFlowViolationException();
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
    public Collection<Account> getFriends(long accountId) {
        try {
            return friendshipDao.selectFriends(accountId);
        } catch (IllegalArgumentException e) {
            throw new IncorrectDataException(IncorrectData.WRONG_REQUEST);
        }
    }

    @Override
    public Collection<Phone> getPhones(long accountId) {
        return phoneDaoFacade.selectPhonesByOwner(accountId);
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
    public boolean isRequester(long requester, long receiver) {
        return friendshipDao.isRequester(requester, receiver);
    }

    @Override
    public Collection<Account> getFriendshipRequests(long receiver) {
        return friendshipDao.selectRequests(receiver);
    }

    @Override
    public Collection<Dialog> getDialogs(long accountId) {
        return dialogsDao.selectDialogsByAccount(accountId);
    }
}
