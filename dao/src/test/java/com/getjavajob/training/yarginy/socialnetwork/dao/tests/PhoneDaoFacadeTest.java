package com.getjavajob.training.yarginy.socialnetwork.dao.tests;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.Phone;
import com.getjavajob.training.yarginy.socialnetwork.common.models.additionaldata.PhoneType;
import com.getjavajob.training.yarginy.socialnetwork.dao.jdbctemplates.facades.AccountDaoFacade;
import com.getjavajob.training.yarginy.socialnetwork.dao.jdbctemplates.facades.PhoneDaoFacade;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:daoSpringConfig.xml", "classpath:daoTestH2OverrideSpringConfig.xml"})
public class PhoneDaoFacadeTest {
    private final Phone phone = new Phone();
    @Autowired
    private PhoneDaoFacade phoneDaoFacade;
    @Autowired
    private AccountDaoFacade accountDao;

    @Before
    public void initTestValues() {
        phone.setNumber("123321");
        Account account = new Account("test", "test", "test@test.test");
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
        Account wrongAccount = new Account();
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
        Phone nonExisting = new Phone();
        nonExisting.setNumber("000000");
        Phone anotherNonExisting = new Phone();
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
        Phone nonExisting = new Phone();
        nonExisting.setNumber("000000");
        assertFalse(phoneDaoFacade.delete(nonExisting));
    }
}
