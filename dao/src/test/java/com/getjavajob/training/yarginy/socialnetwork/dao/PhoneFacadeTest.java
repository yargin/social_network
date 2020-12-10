package com.getjavajob.training.yarginy.socialnetwork.dao;

import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.AccountImpl;
import com.getjavajob.training.yarginy.socialnetwork.common.models.phone.Phone;
import com.getjavajob.training.yarginy.socialnetwork.common.models.phone.PhoneImpl;
import com.getjavajob.training.yarginy.socialnetwork.common.models.phone.additionaldata.PhoneType;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.AccountFacade;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.PhoneFacade;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

public class PhoneFacadeTest {
    static {
        TestDataSourceInitializer.initDataSource();
    }

    private static final Phone PHONE = new PhoneImpl();
    @Autowired
    private PhoneFacade phoneFacade;
    @Autowired
    private AccountFacade accountDao;

    @Before
    public void initTestValues() {
        PHONE.setNumber("123321");
        Account account = new AccountImpl("test", "test", "test@test.test");
        accountDao.create(account);
        account = accountDao.select(account);
        PHONE.setOwner(account);
        PHONE.setType(PhoneType.PRIVATE);
    }

    @After
    public void deleteTestValues() {
        phoneFacade.delete(PHONE);
    }

    @Test
    public void testCreatePhone() {
        phoneFacade.delete(PHONE);
        assertTrue(phoneFacade.create(PHONE));
    }

    @Test
    public void testCreateExisting() {
        phoneFacade.create(PHONE);
        assertFalse(phoneFacade.create(PHONE));
    }

    @Test
    public void testCreateWrongOwner() {
        phoneFacade.delete(PHONE);
        Account wrongAccount = new AccountImpl();
        wrongAccount.setId(-1);
        PHONE.setOwner(wrongAccount);
        try {
            phoneFacade.create(PHONE);
        } catch (IllegalStateException e) {
            assertTrue(true);
        }
        PHONE.setOwner(accountDao.select(1));
    }

    @Test
    public void testSelectPhone() {
        phoneFacade.create(PHONE);
        Phone actual = phoneFacade.select(PHONE);
        assertEquals(PHONE, actual);
    }

    @Test
    public void testSelectNonExisting() {
        assertEquals(phoneFacade.getNullPhone(), phoneFacade.select(99999999));
    }

    @Test
    public void testUpdatePhone() {
        phoneFacade.create(PHONE);
        PHONE.setType(PhoneType.PRIVATE);
        assertTrue(phoneFacade.update(PHONE, phoneFacade.select(PHONE)));
    }

    @Test
    public void testUpdateNonExisting() {
        Phone nonExisting = new PhoneImpl();
        nonExisting.setNumber("000000");
        assertFalse(phoneFacade.update(nonExisting, nonExisting));
    }

    @Test
    public void testDeletePhone() {
        phoneFacade.create(PHONE);
        assertTrue(phoneFacade.delete(PHONE));
    }

    @Test
    public void testDeleteNonExisting() {
        Phone nonExisting = new PhoneImpl();
        nonExisting.setNumber("000000");
        assertFalse(phoneFacade.delete(nonExisting));
    }
}
