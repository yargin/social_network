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
    private final Phone phone = new PhoneImpl();
    @Autowired
    private PhoneDaoFacade phoneDaoFacade;
    @Autowired
    private AccountDaoFacade accountDao;

    @Before
    public void initTestValues() {
        phone.setNumber("123321");
        Account account = new AccountImpl("test", "test", "test@test.test");
        accountDao.create(account);
        account = accountDao.select(account);
        phone.setOwner(account);
        phone.setType(PhoneType.PRIVATE);
    }

    @After
    public void deleteTestValues() {
        phoneDaoFacade.delete(phoneDaoFacade.select(phone));
    }

    @Test
    public void testCreatePhone() {
        phoneDaoFacade.delete(phoneDaoFacade.select(phone));
        assertTrue(phoneDaoFacade.create(phone));
    }

    @Test
    public void testCreateExisting() {
        phoneDaoFacade.create(phone);
        assertFalse(phoneDaoFacade.create(phone));
    }

    @Test
    public void testCreateWrongOwner() {
        phoneDaoFacade.delete(phoneDaoFacade.select(phone));
        Account wrongAccount = new AccountImpl();
        wrongAccount.setId(-1);
        phone.setOwner(wrongAccount);
        try {
            phoneDaoFacade.create(phone);
        } catch (IllegalArgumentException e) {
            assertTrue(true);
        }
        phone.setOwner(accountDao.select(1));
    }

    @Test
    public void testSelectPhone() {
        phoneDaoFacade.create(phone);
        Phone actual = phoneDaoFacade.select(phone);
        assertEquals(phone, actual);
    }

    @Test
    public void testSelectNonExisting() {
        assertEquals(phoneDaoFacade.getNullPhone(), phoneDaoFacade.select(99999999));
    }

    @Test
    public void testUpdatePhone() {
        phoneDaoFacade.create(phone);
        phone.setType(PhoneType.WORK);
        assertTrue(phoneDaoFacade.update(phone, phoneDaoFacade.select(phone)));
    }

    @Test
    public void testUpdateNonExisting() {
        Phone nonExisting = new PhoneImpl();
        nonExisting.setNumber("000000");
        Phone anotherNonExisting = new PhoneImpl();
        anotherNonExisting.setNumber("00000");
        assertFalse(phoneDaoFacade.update(nonExisting, anotherNonExisting));
    }

    @Test
    public void testDeletePhone() {
        phoneDaoFacade.create(phone);
        assertTrue(phoneDaoFacade.delete(phoneDaoFacade.select(phone)));
    }

    @Test
    public void testDeleteNonExisting() {
        Phone nonExisting = new PhoneImpl();
        nonExisting.setNumber("000000");
        assertFalse(phoneDaoFacade.delete(nonExisting));
    }
}
