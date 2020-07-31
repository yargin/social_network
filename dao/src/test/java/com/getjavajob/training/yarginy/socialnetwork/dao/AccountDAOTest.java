package com.getjavajob.training.yarginy.socialnetwork.dao;

import com.getjavajob.training.yarginy.socialnetwork.dao.entities.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.dao.entities.account.AccountDAO;
import com.getjavajob.training.yarginy.socialnetwork.dao.entities.account.dao.sql.AccountDAOImpl;
import com.getjavajob.training.yarginy.socialnetwork.dao.entities.account.dto.AccountImpl;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

public class AccountDAOTest {
    private Account account;
    private AccountDAO accountDAO = new AccountDAOImpl();

    public AccountDAOTest() {
        account = new AccountImpl();
        account.setEmail("asd@!asd");
        account.setName("Vasya");
        account.setSurname("Pupkin");
        account.setPhone("666");
        boolean deletion = accountDAO.deleteAccount(account);
        System.out.println(deletion);
    }

    @Test
    public void testCreateAccount() {
        boolean actual = accountDAO.createAccount(account);
        assertSame(true, actual);
    }

    @Test
    public void testCreateExistingAccount() {
        boolean actual = accountDAO.createAccount(account);
        assertSame(false, actual);
    }

    @Test
    public void testSelectAccount() {
        Account actual = accountDAO.selectAccount(account.getId());
        assertEquals(account, actual);
        actual = accountDAO.selectAccount(account.getEmail());
        assertEquals(account, actual);
    }

    @Test
    public void testSelectNonExistingAccount() {
        Account actual = accountDAO.selectAccount("non existing email");
        assertEquals(Account.getNullAccount(), actual);
        actual = accountDAO.selectAccount(123);
        assertEquals(Account.getNullAccount(), actual);
    }

    @Test
    public void testUpdateAccount() {
        String newPatronymic = "new Patronymic";
        account.setPatronymic(newPatronymic);
        boolean actual = accountDAO.updateAccount(account);
        assertSame(true, actual);
        Account storageAccount = accountDAO.selectAccount(account.getEmail());
        assertEquals(newPatronymic, storageAccount.getPatronymic());
    }

    @Test
    public void testUpdateNonExistingAccount() {
        Account nonExisting = new AccountImpl();
        boolean actual = accountDAO.updateAccount(nonExisting);
        assertSame(false, actual);
    }

    @Test
    public void testDeleteNonExisting() {
        Account nonExisting = new AccountImpl();
        boolean actual = accountDAO.deleteAccount(nonExisting);
        assertSame(false, actual);
    }

    @Test
    public void testDeleteAccount() {
        boolean actual = accountDAO.deleteAccount(account);
        assertSame(true, actual);
        Account nonExisting = Account.getNullAccount();
        assertEquals(nonExisting, accountDAO.selectAccount(account.getEmail()));
    }
}
