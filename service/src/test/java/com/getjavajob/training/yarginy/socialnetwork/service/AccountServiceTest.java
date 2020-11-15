package com.getjavajob.training.yarginy.socialnetwork.service;

import com.getjavajob.training.yarginy.socialnetwork.common.exceptions.IncorrectDataException;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.AccountImpl;
import com.getjavajob.training.yarginy.socialnetwork.common.models.phone.Phone;
import com.getjavajob.training.yarginy.socialnetwork.common.models.phone.PhoneImpl;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.*;
import com.getjavajob.training.yarginy.socialnetwork.dao.factories.connectionpool.Transaction;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.getjavajob.training.yarginy.socialnetwork.service.utils.TestResultPrinter.printPassed;
import static java.util.Arrays.asList;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AccountServiceTest {
    private static final String CLASS = "AccountServiceTest";
    private final AccountDao accountDao = mock(AccountDao.class);
    private final FriendshipsDao friendsDao = mock(FriendshipsDao.class);
    private final PhoneDao phoneDao = mock(PhoneDao.class);
    private final Transaction transaction = mock(Transaction.class);
    private final TransactionManager transactionManager = mock(TransactionManager.class);
    private final AccountPhotoDao accountPhotoDao = mock(AccountPhotoDao.class);
    private final AccountService accountService = new AccountServiceImpl(accountDao, phoneDao, friendsDao,
            accountPhotoDao, transactionManager);
    private Account account;
    private Account storedAccount;
    private Collection<Phone> phones;

    @Before
    public void init() {
        account = new AccountImpl("Petr", "email@gjj.ru");
        account.setId(555);
        phones = asList(new PhoneImpl("123321", account), new PhoneImpl("123123", account));
    }

    @Test
    public void testGetAccount() {
        when(transactionManager.getTransaction()).thenReturn(transaction);
        when(accountDao.select(1)).thenReturn(account);
        Account actualAccount = accountService.getAccount(1);
        assertEquals(account, actualAccount);
        when(accountDao.select(account)).thenReturn(account);
        actualAccount = accountService.getAccount(account);
        assertEquals(account, actualAccount);
        when(accountDao.select(1)).thenReturn(account);
        actualAccount = accountService.getAccount(1);
        assertEquals(account, actualAccount);
        printPassed(CLASS, "testGetAccount");
    }

    @Test
    public void testCreateAccount() {
        when(transactionManager.getTransaction()).thenReturn(transaction);
        when(accountDao.create(account)).thenReturn(true);
        when(phoneDao.create(phones)).thenReturn(true);
        assertTrue(accountService.createAccount(account, phones));
        printPassed(CLASS, "testCreateAccount");
    }

    @Test(expected = IncorrectDataException.class)
    public void testCreateExistingAccount() {
        when(transactionManager.getTransaction()).thenReturn(transaction);
        when(accountDao.create(account)).thenReturn(false);
        accountService.createAccount(account, phones);
        printPassed(CLASS, "IncorrectDataException");
    }

    @Test(expected = IncorrectDataException.class)
    public void testCreateExistingPhone() {
        when(transactionManager.getTransaction()).thenReturn(transaction);
        when(phoneDao.create(phones)).thenReturn(false);
        accountService.createAccount(account, phones);
        printPassed(CLASS, "testCreateExistingPhone");
    }

    @Test
    public void testUpdateAccount() {
        when(transactionManager.getTransaction()).thenReturn(transaction);
        when(accountDao.update(account, storedAccount)).thenReturn(true);
        assertTrue(accountService.updateAccount(account, storedAccount));
        when(accountDao.update(account, storedAccount)).thenReturn(false);
        assertFalse(accountService.updateAccount(account, storedAccount));
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
        when(friendsDao.deleteRequest(account.getId(), 13)).thenReturn(true);
        when(friendsDao.createFriendship(account.getId(), 13)).thenReturn(false);
        assertFalse(accountService.addFriend(account.getId(), 13));
//        when(friendsDao.deleteRequest(account.getId(), 11)).thenReturn(true);
//        when(friendsDao.createFriendship(account.getId(), 11)).thenReturn(true);
//        assertTrue(accountService.addFriend(account.getId(), 11));
        printPassed(CLASS, "testAddFriend");
    }

    @Test
    public void testRemoveFriend() {
        when(friendsDao.removeFriendship(account.getId(), 14)).thenReturn(true);
        assertTrue(accountService.removeFriend(account.getId(), 14));
        when(friendsDao.removeFriendship(account.getId(), 11)).thenReturn(false);
        assertFalse(accountService.removeFriend(account.getId(), 11));
        printPassed(CLASS, "testRemoveFriend");
    }

    @Test
    public void testGetFriends() {
        List<Account> emptyFriends = new ArrayList<>();
        assertEquals(emptyFriends, accountService.getFriends(account.getId()));
        printPassed(CLASS, "testGetFriends");
    }

    @Test
    public void testAddPhone() {
        Phone phone = new PhoneImpl();
        phone.setNumber("111-111");
        when(phoneDao.create(phone)).thenReturn(true);
        assertTrue(phoneDao.create(phone));
        printPassed(CLASS, "testAddPhone");
    }

    @Test
    public void testRemovePhone() {
        Phone phone = new PhoneImpl();
        phone.setNumber("111-111");
        when(phoneDao.create(phone)).thenReturn(true);
        assertTrue(phoneDao.create(phone));
        printPassed(CLASS, "testAddPhone");
    }

    @Test
    public void testGetPhones() {
        Phone firstPhone = new PhoneImpl();
        firstPhone.setNumber("11333");
        Phone secondPhone = new PhoneImpl();
        secondPhone.setNumber("33111");
        Collection<Phone> phones = asList(firstPhone, secondPhone);
        when(phoneDao.selectPhonesByOwner(account.getId())).thenReturn(phones);
        Collection<Phone> actualPhones = accountService.getPhones(account.getId());
        System.out.println(actualPhones);
        assertEquals(phones, actualPhones);
        printPassed(CLASS, "testGetPhones");
    }
}
