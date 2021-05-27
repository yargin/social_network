package com.getjavajob.training.yarginy.socialnetwork.service;

import com.getjavajob.training.yarginy.socialnetwork.common.exceptions.IncorrectDataException;
import com.getjavajob.training.yarginy.socialnetwork.common.models.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.Dialog;
import com.getjavajob.training.yarginy.socialnetwork.common.models.Phone;
import com.getjavajob.training.yarginy.socialnetwork.common.models.additionaldata.Role;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.AccountDaoFacade;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.DialogDaoFacade;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.FriendshipsDaoFacade;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.PhoneDaoFacade;
import com.getjavajob.training.yarginy.socialnetwork.dao.utils.TransactionPerformer;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Objects;

import static com.getjavajob.training.yarginy.socialnetwork.common.exceptions.IncorrectData.EMAIL_DUPLICATE;
import static com.getjavajob.training.yarginy.socialnetwork.common.exceptions.IncorrectData.PHONE_DUPLICATE;
import static com.getjavajob.training.yarginy.socialnetwork.common.exceptions.IncorrectData.WRONG_REQUEST;
import static java.sql.Date.valueOf;
import static java.time.LocalDate.now;

@Service
public class AccountServiceImpl implements AccountService {
    private final AccountDaoFacade accountDaoFacade;
    private final PhoneDaoFacade phoneDaoFacade;
    private final FriendshipsDaoFacade friendshipDao;
    private final DialogDaoFacade dialogsDao;
    private final TransactionPerformer transactionPerformer;
    private final AccountServiceTransactional serviceTransactional;

    public AccountServiceImpl(AccountDaoFacade accountDaoFacade, PhoneDaoFacade phoneDaoFacade,
                              FriendshipsDaoFacade friendshipDao, DialogDaoFacade dialogDaoFacade,
                              TransactionPerformer transactionPerformer,
                              AccountServiceTransactional serviceTransactional) {
        this.accountDaoFacade = accountDaoFacade;
        this.phoneDaoFacade = phoneDaoFacade;
        this.friendshipDao = friendshipDao;
        this.dialogsDao = dialogDaoFacade;
        this.transactionPerformer = transactionPerformer;
        this.serviceTransactional = serviceTransactional;
    }

    @Override
    public Account get(long id) {
        return accountDaoFacade.select(id);
    }

    @Override
    public Account getFullInfo(long id) {
        return accountDaoFacade.selectFullInfo(id);
    }

    @Override
    public Account get(Account account) {
        return accountDaoFacade.select(account);
    }

    @Override
    public boolean createAccount(Account account, Collection<Phone> phones) {
        account.setRegistrationDate(valueOf(now()));
        if (!accountDaoFacade.create(account)) {
            throw new IncorrectDataException(EMAIL_DUPLICATE);
        }
        if (!phoneDaoFacade.create(phones)) {
            throw new IncorrectDataException(PHONE_DUPLICATE);
        }
        return true;
    }

    @Override
    public boolean updateAccount(Account account, Collection<Phone> phones, Collection<Phone> storedPhones) {
        if (!accountDaoFacade.update(account)) {
            throw new IncorrectDataException(EMAIL_DUPLICATE);
        }
        if (!phoneDaoFacade.update(phones, storedPhones)) {
            throw new IncorrectDataException(PHONE_DUPLICATE);
        }
        return true;
    }

    @Override
    @PreAuthorize("hasAuthority('ADMIN')")
    public boolean deleteAccount(Account account) {
        return accountDaoFacade.delete(account);
    }

    @Override
    @PreAuthorize("hasAuthority('ADMIN')")
    public boolean setRole(Account account, Role role) {
        if (!Objects.equals(account.getRole(), role)) {
            return accountDaoFacade.setRole(account, role);
        }
        return true;
    }

    @Override
    public boolean addFriend(long firstId, long secondId) {
        return transactionPerformer.transactionPerformed(serviceTransactional::addFriendTransactional, firstId, secondId);
    }

    @Override
    public boolean removeFriend(long firstId, long secondId) {
        try {
            return friendshipDao.removeFriendship(firstId, secondId);
        } catch (IllegalArgumentException e) {
            throw new IncorrectDataException(WRONG_REQUEST);
        }
    }

    @Override
    public boolean isFriend(long firstId, long secondId) {
        try {
            return friendshipDao.areFriends(firstId, secondId);
        } catch (IllegalArgumentException e) {
            throw new IncorrectDataException(WRONG_REQUEST);
        }
    }

    @Override
    public Collection<Account> getFriends(long accountId) {
        try {
            return friendshipDao.selectFriends(accountId);
        } catch (IllegalArgumentException e) {
            throw new IncorrectDataException(WRONG_REQUEST);
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
            throw new IncorrectDataException(WRONG_REQUEST);
        }
    }

    @Override
    public boolean deleteFriendshipRequest(long requester, long receiver) {
        try {
            return friendshipDao.deleteRequest(requester, receiver);
        } catch (IllegalArgumentException e) {
            throw new IncorrectDataException(WRONG_REQUEST);
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
