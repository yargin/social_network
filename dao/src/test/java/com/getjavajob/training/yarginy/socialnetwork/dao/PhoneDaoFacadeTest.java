package com.getjavajob.training.yarginy.socialnetwork.dao;

import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.AccountImpl;
import com.getjavajob.training.yarginy.socialnetwork.common.models.phone.Phone;
import com.getjavajob.training.yarginy.socialnetwork.common.models.phone.PhoneImpl;
import com.getjavajob.training.yarginy.socialnetwork.common.models.phone.additionaldata.PhoneType;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.AccountDaoFacade;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.PhoneDaoFacade;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:daoSpringConfig.xml", "classpath:daoOverrideSpringConfig.xml"})
public class PhoneDaoFacadeTest {
    private static final Phone PHONE = new PhoneImpl();
    @Autowired
    private PhoneDaoFacade phoneDaoFacade;
    @Autowired
    private AccountDaoFacade accountDao;

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
        phoneDaoFacade.delete(PHONE);
    }

    @Test
    public void testCreatePhone() {
        phoneDaoFacade.delete(PHONE);
        assertTrue(phoneDaoFacade.create(PHONE));
    }

    @Test
    public void testCreateExisting() {
        phoneDaoFacade.create(PHONE);
        assertFalse(phoneDaoFacade.create(PHONE));
    }

    @Test
    public void testCreateWrongOwner() {
        phoneDaoFacade.delete(PHONE);
        Account wrongAccount = new AccountImpl();
        wrongAccount.setId(-1);
        PHONE.setOwner(wrongAccount);
        try {
            phoneDaoFacade.create(PHONE);
        } catch (IllegalStateException e) {
            assertTrue(true);
        }
        PHONE.setOwner(accountDao.select(1));
    }

    @Test
    public void testSelectPhone() {
        phoneDaoFacade.create(PHONE);
        Phone actual = phoneDaoFacade.select(PHONE);
        assertEquals(PHONE, actual);
    }

    @Test
    public void testSelectNonExisting() {
        assertEquals(phoneDaoFacade.getNullPhone(), phoneDaoFacade.select(99999999));
    }

    @Test
    public void testUpdatePhone() {
        phoneDaoFacade.create(PHONE);
        PHONE.setType(PhoneType.PRIVATE);
        assertTrue(phoneDaoFacade.update(PHONE, phoneDaoFacade.select(PHONE)));
    }

    @Test
    public void testUpdateNonExisting() {
        Phone nonExisting = new PhoneImpl();
        nonExisting.setNumber("000000");
        assertFalse(phoneDaoFacade.update(nonExisting, nonExisting));
    }

    @Test
    public void testDeletePhone() {
        phoneDaoFacade.create(PHONE);
        assertTrue(phoneDaoFacade.delete(PHONE));
    }

    @Test
    public void testDeleteNonExisting() {
        Phone nonExisting = new PhoneImpl();
        nonExisting.setNumber("000000");
        assertFalse(phoneDaoFacade.delete(nonExisting));
    }
}
