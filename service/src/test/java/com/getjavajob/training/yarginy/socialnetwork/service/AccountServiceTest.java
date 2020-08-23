package com.getjavajob.training.yarginy.socialnetwork.service;

import com.getjavajob.trainig.yarginy.socialnetwork.service.AccountService;
import com.getjavajob.trainig.yarginy.socialnetwork.service.AccountServiceImpl;
import com.getjavajob.training.yarginy.socialnetwork.common.entities.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.entities.account.AccountImpl;
import com.getjavajob.training.yarginy.socialnetwork.common.entities.phone.Phone;
import com.getjavajob.training.yarginy.socialnetwork.dao.entities.Dao;
import com.getjavajob.training.yarginy.socialnetwork.dao.relations.manytomany.selfrelated.SelfManyToManyDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.relations.onetomany.OneToManyDao;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static com.getjavajob.training.yarginy.socialnetwork.service.utils.TestResultPrinter.printPassed;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AccountServiceTest {
    public static final String CLASS = "AccountServiceTest";
    private final Dao<Account> accountDao = mock(Dao.class);
    private final Dao<Phone> phoneDao = mock(Dao.class);
    private final SelfManyToManyDao<Account> friendsDao = mock(SelfManyToManyDao.class);
    private final OneToManyDao<Account, Phone> accountsPhonesDao = mock(OneToManyDao.class);
    private final AccountService accountService = new AccountServiceImpl(accountDao, friendsDao, phoneDao,
            accountsPhonesDao);
    private Account account;

    @Before
    public void init() {
        account = new AccountImpl("Petr", "email@gjj.ru");
    }

    @Test
    public void testGetAccount() {
        when(accountDao.select(1)).thenReturn(account);
        Account actualAccount = accountService.getAccount(1);
        assertEquals(account, actualAccount);
        when(accountDao.select("email@gjj.ru")).thenReturn(account);
        actualAccount = accountService.getAccount("email@gjj.ru");
        assertEquals(account, actualAccount);
        when(accountDao.select(1)).thenReturn(account);
        actualAccount = accountService.getAccount(1);
        assertEquals(account, actualAccount);
        printPassed(CLASS, "testGetAccount");
    }

    @Test
    public void testCreateAccount() {
        when(accountDao.create(account)).thenReturn(true);
        assertTrue(accountService.createAccount(account));
        when(accountDao.create(account)).thenReturn(false);
        assertFalse(accountService.createAccount(account));
        printPassed(CLASS, "testCreateAccount");
    }

    @Test
    public void testUpdateAccount() {
        when(accountDao.update(account)).thenReturn(true);
        assertTrue(accountService.updateAccount(account));
        when(accountDao.update(account)).thenReturn(false);
        assertFalse(accountService.updateAccount(account));
        printPassed(CLASS, "testUpdateAccount");
    }

    @Test
    public void testDeleteAccount() {
        when(accountDao.delete(account)).thenReturn(true);
        assertTrue(accountService.deleteAccount(account));
        when(accountDao.delete(account)).thenReturn(false);
        assertFalse(accountService.deleteAccount(account));
        printPassed(CLASS, "testDeleteAccount");
    }

    @Test
    public void testAddFriend() {
        when(friendsDao.create(account, new AccountImpl("friend", "first@mail.com"))).thenReturn(false);
        assertFalse(accountService.addFriend(account, new AccountImpl("friend", "first@mail.com")));
        when(friendsDao.create(account, new AccountImpl("third", "third@mail.com"))).thenReturn(true);
        assertTrue(accountService.addFriend(account, new AccountImpl("friend", "third@mail.com")));
        printPassed(CLASS, "testAddFriend");
    }

    @Test
    public void testRemoveFriend() {
        when(friendsDao.delete(account, new AccountImpl("friend", "first@mail.com"))).thenReturn(true);
        assertTrue(accountService.removeFriend(account, new AccountImpl("friend", "first@mail.com")));
        when(friendsDao.delete(account, new AccountImpl("third", "third@mail.com"))).thenReturn(false);
        assertFalse(accountService.removeFriend(account, new AccountImpl("friend", "third@mail.com")));
        printPassed(CLASS, "testRemoveFriend");
    }

    @Test
    public void testGetFriends() {
        List<Account> emptyFriends = new ArrayList<>();
        assertEquals(emptyFriends, accountService.getFriends(account));
        printPassed(CLASS, "testGetFriends");
    }

    //todo
    @Test
    public void testAddPhone() {

    }

    @Test
    public void testRemovePhone() {

    }

    @Test
    public void testGetPhones() {

    }

    @Test
    public void testGetAllWithPhones() {

    }
}
