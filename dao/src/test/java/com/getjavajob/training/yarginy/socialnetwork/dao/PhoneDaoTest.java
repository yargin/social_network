package com.getjavajob.training.yarginy.socialnetwork.dao;

import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.AccountImpl;
import com.getjavajob.training.yarginy.socialnetwork.common.models.phone.Phone;
import com.getjavajob.training.yarginy.socialnetwork.common.models.phone.PhoneImpl;
import com.getjavajob.training.yarginy.socialnetwork.common.models.phone.additionaldata.PhoneType;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.AccountDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.AccountDaoImpl;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.PhoneDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.PhoneDaoImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PhoneDaoTest {
    private static final PhoneDao PHONE_DAO = new PhoneDaoImpl();
    private static final AccountDao ACCOUNT_DAO = new AccountDaoImpl();
    private static final Phone PHONE = new PhoneImpl();

    @Before
    public void initTestValues() {
        PHONE.setNumber("123321");
        Account account = new AccountImpl("test", "test", "test@test.test");
        ACCOUNT_DAO.create(account);
        account = ACCOUNT_DAO.select(account);
        PHONE.setOwner(account);
        PHONE.setType(PhoneType.PRIVATE);
    }

    @After
    public void deleteTestValues() {
        PHONE_DAO.delete(PHONE);
    }

    @Test
    public void testCreatePhone() {
        PHONE_DAO.delete(PHONE);
        assertTrue(PHONE_DAO.create(PHONE));
    }

    @Test
    public void testCreateExisting() {
        PHONE_DAO.create(PHONE);
        assertFalse(PHONE_DAO.create(PHONE));
    }

    @Test
    public void testCreateWrongOwner() {
        PHONE_DAO.delete(PHONE);
        Account wrongAccount = new AccountImpl();
        wrongAccount.setId(-1);
        PHONE.setOwner(wrongAccount);
        try {
            PHONE_DAO.create(PHONE);
        } catch (IllegalStateException e) {
            assertTrue(true);
        }
        PHONE.setOwner(ACCOUNT_DAO.select(1));
    }

    @Test
    public void testSelectPhone() {
        PHONE_DAO.create(PHONE);
        Phone actual = PHONE_DAO.select(PHONE);
        assertEquals(PHONE, actual);
    }

    @Test
    public void testSelectNonExisting() {
        assertEquals(PHONE_DAO.getNullPhone(), PHONE_DAO.select(99999999));
    }

    @Test
    public void testUpdatePhone() {
        PHONE_DAO.create(PHONE);
        PHONE.setType(PhoneType.PRIVATE);
        assertTrue(PHONE_DAO.update(PHONE, PHONE_DAO.select(PHONE)));
    }

    @Test
    public void testUpdateNonExisting() {
        Phone nonExisting = new PhoneImpl();
        nonExisting.setNumber("000000");
        assertFalse(PHONE_DAO.update(nonExisting, nonExisting));
    }

    @Test
    public void testDeletePhone() {
        PHONE_DAO.create(PHONE);
        assertTrue(PHONE_DAO.delete(PHONE));
    }

    @Test
    public void testDeleteNonExisting() {
        Phone nonExisting = new PhoneImpl();
        nonExisting.setNumber("000000");
        assertFalse(PHONE_DAO.delete(nonExisting));
    }
}
