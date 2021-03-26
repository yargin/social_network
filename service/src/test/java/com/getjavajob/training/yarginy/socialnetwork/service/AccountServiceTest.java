package com.getjavajob.training.yarginy.socialnetwork.service;

import com.getjavajob.training.yarginy.socialnetwork.common.exceptions.IncorrectDataException;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.phone.Phone;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.AccountDaoFacade;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.FriendshipsDaoFacade;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.PhoneDaoFacade;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AccountServiceTest {
    @Mock
    private AccountDaoFacade accountDaoFacade;
    @Mock
    private FriendshipsDaoFacade friendsDao;
    @Mock
    private PhoneDaoFacade phoneDaoFacade;
    @InjectMocks
    private AccountServiceImpl accountService;
    private Account account;
    private Account storedAccount;
    private Collection<Phone> phones;
    private Collection<Phone> storedPhones;

    @Before
    public void init() {
        account = new Account("Petr", "email@gjj.ru");
        account.setId(555);
        phones = asList(new Phone("123321", account), new Phone("123123", account));
    }

    @Test
    public void testGetAccount() {
        when(accountDaoFacade.select(1)).thenReturn(account);
        Account actualAccount = accountService.get(1);
        assertEquals(account, actualAccount);
        when(accountDaoFacade.select(account)).thenReturn(account);
        actualAccount = accountService.get(account);
        assertEquals(account, actualAccount);
        when(accountDaoFacade.select(1)).thenReturn(account);
        actualAccount = accountService.get(1);
        assertEquals(account, actualAccount);
    }

    @Test
    public void testCreateAccount() {
        when(accountDaoFacade.create(account)).thenReturn(true);
        when(phoneDaoFacade.create(phones)).thenReturn(true);
        assertTrue(accountService.createAccount(account, phones));
    }

    @Test(expected = IncorrectDataException.class)
    public void testCreateExistingAccount() {
        when(accountDaoFacade.create(account)).thenReturn(false);
        accountService.createAccount(account, phones);
    }

    @Test(expected = IncorrectDataException.class)
    public void testCreateExistingPhone() {
        when(phoneDaoFacade.create(phones)).thenReturn(false);
        accountService.createAccount(account, phones);
    }

    @Test
    public void testUpdateAccount() {
        when(accountDaoFacade.update(account, storedAccount)).thenReturn(true);
        when(phoneDaoFacade.update(phones, storedPhones)).thenReturn(true);
        assertTrue(accountService.updateAccount(account, storedAccount, phones, storedPhones));
    }

    @Test(expected = IncorrectDataException.class)
    public void testUpdateAccountFail() {
        when(accountDaoFacade.update(account, storedAccount)).thenReturn(false);
        when(phoneDaoFacade.update(phones, storedPhones)).thenReturn(true);
        accountService.updateAccount(account, storedAccount, phones, storedPhones);
    }

    @Test
    public void testDeleteAccount() {
        when(accountDaoFacade.delete(account)).thenReturn(true);
        assertTrue(accountService.deleteAccount(account));
        when(accountDaoFacade.delete(account)).thenReturn(false);
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
        Phone phone = new Phone();
        phone.setNumber("111-111");
        when(phoneDaoFacade.create(phone)).thenReturn(true);
        assertTrue(phoneDaoFacade.create(phone));
    }

    @Test
    public void testRemovePhone() {
        Phone phone = new Phone();
        phone.setNumber("111-111");
        when(phoneDaoFacade.create(phone)).thenReturn(true);
        assertTrue(phoneDaoFacade.create(phone));
    }

    @Test
    public void testGetPhones() {
        Phone firstPhone = new Phone();
        firstPhone.setNumber("11333");
        Phone secondPhone = new Phone();
        secondPhone.setNumber("33111");
        Collection<Phone> phones = asList(firstPhone, secondPhone);
        when(phoneDaoFacade.selectPhonesByOwner(account.getId())).thenReturn(phones);
        Collection<Phone> actualPhones = accountService.getPhones(account.getId());
        assertEquals(phones, actualPhones);
    }
}
