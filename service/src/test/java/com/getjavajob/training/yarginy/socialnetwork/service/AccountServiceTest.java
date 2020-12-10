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

import static java.util.Arrays.asList;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AccountServiceTest {
    private final AccountFacade accountFacade = mock(AccountFacade.class);
    private final FriendshipsFacade friendsDao = mock(FriendshipsFacade.class);
    private final PhoneFacade phoneFacade = mock(PhoneFacade.class);
    private final DialogFacade dialogFacade = mock(DialogFacade.class);
    private final Transaction transaction = mock(Transaction.class);
    private final TransactionManager transactionManager = mock(TransactionManager.class);
    private final AccountService accountService = new AccountServiceImpl(accountFacade, phoneFacade, friendsDao,
            dialogFacade);
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
//        when(transactionManager.getTransaction()).thenReturn(transaction);
        when(accountFacade.select(1)).thenReturn(account);
        Account actualAccount = accountService.get(1);
        assertEquals(account, actualAccount);
        when(accountFacade.select(account)).thenReturn(account);
        actualAccount = accountService.get(account);
        assertEquals(account, actualAccount);
        when(accountFacade.select(1)).thenReturn(account);
        actualAccount = accountService.get(1);
        assertEquals(account, actualAccount);
    }

    @Test
    public void testCreateAccount() {
//        when(transactionManager.getTransaction()).thenReturn(transaction);
        when(accountFacade.create(account)).thenReturn(true);
        when(phoneFacade.create(phones)).thenReturn(true);
        assertTrue(accountService.createAccount(account, phones));
    }

    @Test(expected = IncorrectDataException.class)
    public void testCreateExistingAccount() {
//        when(transactionManager.getTransaction()).thenReturn(transaction);
        when(accountFacade.create(account)).thenReturn(false);
        accountService.createAccount(account, phones);
    }

    @Test(expected = IncorrectDataException.class)
    public void testCreateExistingPhone() {
//        when(transactionManager.getTransaction()).thenReturn(transaction);
        when(phoneFacade.create(phones)).thenReturn(false);
        accountService.createAccount(account, phones);
    }

    @Test
    public void testUpdateAccount() {
//        when(transactionManager.getTransaction()).thenReturn(transaction);
        when(accountFacade.update(account, storedAccount)).thenReturn(true);
        assertTrue(accountService.updateAccount(account, storedAccount));
        when(accountFacade.update(account, storedAccount)).thenReturn(false);
        assertFalse(accountService.updateAccount(account, storedAccount));
    }

    @Test
    public void testDeleteAccount() {
        when(accountFacade.delete(account)).thenReturn(true);
        assertTrue(accountService.deleteAccount(account));
        when(accountFacade.delete(account)).thenReturn(false);
        assertFalse(accountService.deleteAccount(account));
    }

    @Test
    public void testAddFriend() {
        when(friendsDao.deleteRequest(account.getId(), 13)).thenReturn(true);
        when(friendsDao.createFriendship(account.getId(), 13)).thenReturn(false);
        assertFalse(accountService.addFriend(account.getId(), 13));
    }

    @Test
    public void testRemoveFriend() {
        when(friendsDao.removeFriendship(account.getId(), 14)).thenReturn(true);
        assertTrue(accountService.removeFriend(account.getId(), 14));
        when(friendsDao.removeFriendship(account.getId(), 11)).thenReturn(false);
        assertFalse(accountService.removeFriend(account.getId(), 11));
    }

    @Test
    public void testGetFriends() {
        List<Account> emptyFriends = new ArrayList<>();
        assertEquals(emptyFriends, accountService.getFriends(account.getId()));
    }

    @Test
    public void testAddPhone() {
        Phone phone = new PhoneImpl();
        phone.setNumber("111-111");
        when(phoneFacade.create(phone)).thenReturn(true);
        assertTrue(phoneFacade.create(phone));
    }

    @Test
    public void testRemovePhone() {
        Phone phone = new PhoneImpl();
        phone.setNumber("111-111");
        when(phoneFacade.create(phone)).thenReturn(true);
        assertTrue(phoneFacade.create(phone));
    }

    @Test
    public void testGetPhones() {
        Phone firstPhone = new PhoneImpl();
        firstPhone.setNumber("11333");
        Phone secondPhone = new PhoneImpl();
        secondPhone.setNumber("33111");
        Collection<Phone> phones = asList(firstPhone, secondPhone);
        when(phoneFacade.selectPhonesByOwner(account.getId())).thenReturn(phones);
        Collection<Phone> actualPhones = accountService.getPhones(account.getId());
        assertEquals(phones, actualPhones);
    }
}
