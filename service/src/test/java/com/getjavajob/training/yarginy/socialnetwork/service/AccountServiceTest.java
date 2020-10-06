package com.getjavajob.training.yarginy.socialnetwork.service;

import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.AccountImpl;
import com.getjavajob.training.yarginy.socialnetwork.common.models.phone.Phone;
import com.getjavajob.training.yarginy.socialnetwork.common.models.phone.PhoneImpl;
import com.getjavajob.training.yarginy.socialnetwork.dao.batchmodeldao.BatchDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.factories.connector.TransactionManager;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.Dao;
import com.getjavajob.training.yarginy.socialnetwork.dao.relationsdao.manytomany.selfrelated.SelfManyToManyDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.relationsdao.onetomany.OneToManyDao;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static com.getjavajob.training.yarginy.socialnetwork.service.utils.TestResultPrinter.printPassed;
import static java.util.Arrays.asList;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AccountServiceTest {
    public static final String CLASS = "AccountServiceTest";
    private final Dao<Account> accountDao = mock(Dao.class);
    private final Dao<Phone> phoneDao = mock(Dao.class);
    private final SelfManyToManyDao<Account> friendsDao = mock(SelfManyToManyDao.class);
    private final OneToManyDao<Account, Phone> accountsPhonesDao = mock(OneToManyDao.class);
    private final TransactionManager transaction = mock(TransactionManager.class);
    private final BatchDao<Phone> phoneBatchDao = mock(BatchDao.class);
    private final AccountService accountService = new AccountServiceImpl(accountDao, friendsDao, phoneDao,
            accountsPhonesDao, transaction, phoneBatchDao);
    private Account account;
    private Collection<Phone> phones;

    @Before
    public void init() {
        account = new AccountImpl("Petr", "email@gjj.ru");
        account.setId(555);
        phones = asList(new PhoneImpl("123321", account), new PhoneImpl("123123", account));
    }

    @Test
    public void testGetAccount() {
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
        when(accountDao.create(account)).thenReturn(true);
        assertTrue(accountService.createAccount(account, phones));
        when(accountDao.create(account)).thenReturn(false);
        assertFalse(accountService.createAccount(account, phones));
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
        when(accountsPhonesDao.selectMany(account)).thenReturn(phones);
        assertEquals(phones, accountService.getPhones(account));
        printPassed(CLASS, "testGetPhones");
    }

    @Test
    public void testGetAllWithPhones() {
        Map<Account, Collection<Phone>> expected = new HashMap<>();
        //all phones for mock
        Collection<Phone> phones = new HashSet<>();

        Account account = new AccountImpl("Vladimir", "rise@communism.com");
        account.setId(1);
        Phone phone = new PhoneImpl();
        phone.setOwner(account);
        phone.setNumber("02");
        phones.add(phone);
        Collection<Phone> accountPhones = new HashSet<>();
        accountPhones.add(phone);
        phone = new PhoneImpl();
        phone.setOwner(account);
        phone.setNumber("+7 (920) 123-23-32");
        phones.add(phone);
        accountPhones.add(phone);
        expected.put(account, accountPhones);

        account = new AccountImpl("dracula", "drink@blood.com");
        account.setId(2);
        phone = new PhoneImpl();
        phone.setOwner(account);
        phone.setNumber("8 (921) 1234321");
        phones.add(phone);
        accountPhones = new HashSet<>();
        accountPhones.add(phone);
        phone = new PhoneImpl();
        phone.setOwner(account);
        phone.setNumber("14-1414-14");
        accountPhones.add(phone);
        phones.add(phone);
        expected.put(account, accountPhones);

        account = new AccountImpl("Alan", "robot@power.com");
        account.setId(3);
        phone = new PhoneImpl();
        phone.setOwner(account);
        phone.setNumber("444-444-444");
        phones.add(phone);
        accountPhones = new HashSet<>();
        accountPhones.add(phone);
        phone = new PhoneImpl();
        phone.setOwner(account);
        phone.setNumber("+9 812 123 321");
        phones.add(phone);
        accountPhones.add(phone);
        expected.put(account, accountPhones);

        account = new AccountImpl("Petr", "popovp@gmail.com");
        account.setId(3);
        phone = new PhoneImpl();
        phone.setOwner(account);
        phone.setNumber("89218942");
        phones.add(phone);
        accountPhones = new HashSet<>();
        accountPhones.add(phone);
        expected.put(account, accountPhones);

        when(phoneDao.selectAll()).thenReturn(phones);
        assertEquals(expected, accountService.getAllWithPhones());
        printPassed(CLASS, "testGetAllWithPhones");
    }
}
