package com.getjavajob.training.yarginy.socialnetwork.dao;

import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.AccountImpl;
import com.getjavajob.training.yarginy.socialnetwork.common.models.accountphoto.AccountPhoto;
import com.getjavajob.training.yarginy.socialnetwork.common.models.accountphoto.AccountPhotoImpl;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.AccountDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.AccountDaoImpl;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.AccountPhotoDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.AccountPhotoDaoImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;

import static com.getjavajob.training.yarginy.socialnetwork.dao.utils.TestResultPrinter.printPassed;
import static org.junit.Assert.*;

public class AccountPhotoDaoTest {
    private static final String CLASS = "AccountPhotoDaoTest";
    private static final long TEST_ID = 999999999999L;
    private final AccountPhotoDao accountPhotoDao = new AccountPhotoDaoImpl();
    private final AccountDao accountDao = new AccountDaoImpl();
    private Account account = new AccountImpl("testName", "test@test.com");
    private AccountPhoto accountPhoto = new AccountPhotoImpl();

    @Before
    public void assignTestValues() throws IOException {
        account.setSurname("testSurname");
        accountDao.create(account);
        account = accountDao.select(account);
        accountPhoto.setOwner(account);
        Path pathToFile = Paths.get("src","test","resources", "testphoto", "test1.png");
        File file = pathToFile.toFile();
        try (InputStream inputStream = new FileInputStream(file)) {
            accountPhoto.setPhoto(inputStream);
        }
        accountPhotoDao.create(accountPhoto);
    }

    @After
    public void deleteAssignedValues() {
        accountPhotoDao.delete(accountPhoto);
    }

    @Test
    public void testSelect() throws IOException {
        accountPhoto = accountPhotoDao.select(account);
        AccountPhoto testAccountPhoto = new AccountPhotoImpl();
        testAccountPhoto.setOwner(account);
        setPhoto(testAccountPhoto, "test1.png");
        assertEquals(accountPhoto, testAccountPhoto);
        printPassed(CLASS, "testSelect");
    }

    @Test
    public void testUpdate() throws IOException {
//        setPhoto(accountPhoto, "test2.png");
//        assertTrue(accountPhotoDao.update(accountPhoto));
//        AccountPhoto testAccountPhoto = accountPhotoDao.select(account);
//        assertEquals(accountPhoto, testAccountPhoto);
//        printPassed(CLASS, "testUpdate");
    }

    @Test
    public void testUpdateNonExisting() {
        Account testAccount = account;
        testAccount.setId(TEST_ID);
        accountPhoto.setOwner(testAccount);
        assertFalse(accountPhotoDao.update(accountPhoto));
        printPassed(CLASS, "testUpdateNonExisting");
    }

    @Test
    public void testCreate() throws IOException {
        assert accountPhotoDao.delete(accountPhoto);
        accountPhoto.setOwner(account);
        setPhoto(accountPhoto, "test1.png");
        assertTrue(accountPhotoDao.create(accountPhoto));
        AccountPhoto testAccountPhoto = new AccountPhotoImpl();
        testAccountPhoto.setOwner(account);
        setPhoto(testAccountPhoto, "test1.png");
        assertEquals(testAccountPhoto, accountPhoto);
        printPassed(CLASS, "testCreate");
    }

    @Test
    public void testCreateExisting() {
        AccountPhoto testAccountPhoto = new AccountPhotoImpl();
        testAccountPhoto.setOwner(account);
        assertFalse(accountPhotoDao.create(testAccountPhoto));
        printPassed(CLASS, "testCreateExisting");
    }

    @Test
    public void testDelete() {
        assertTrue(accountPhotoDao.delete(accountPhoto));
        assertEquals(accountPhotoDao.select(account), accountPhotoDao.getNullAccountPhoto());
        printPassed(CLASS, "testDelete");
    }

    @Test
    public void testDeleteNonExisting() {
        Account testAccount = new AccountImpl("testAccount", "test@test.test");
        testAccount.setId(TEST_ID);
        AccountPhoto testAccountPhoto = new AccountPhotoImpl();
        testAccountPhoto.setOwner(testAccount);
        assertFalse(accountPhotoDao.delete(testAccountPhoto));
        printPassed(CLASS, "testDeleteNonExisting");
    }

    private void setPhoto(AccountPhoto accountPhoto, String fileName) throws IOException {
        Path pathToFile = Paths.get("src","test","resources", "testphoto", fileName);
        File file = pathToFile.toFile();
        try (InputStream inputStream = new FileInputStream(file)) {
            accountPhoto.setPhoto(inputStream);
        }
    }
}
