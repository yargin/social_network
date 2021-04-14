package com.getjavajob.training.yarginy.socialnetwork.dao.tests;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.Phone;
import com.getjavajob.training.yarginy.socialnetwork.common.models.additionaldata.PhoneType;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.AccountDaoFacade;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.PhoneDaoFacade;
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
@ContextConfiguration(locations = {"classpath:daoSpringConfig.xml", "classpath:daoTestJpaSpringConfig.xml"})
public class PhoneDaoFacadeTest {
    private Phone phone = new Phone();
    @Autowired
    private PhoneDaoFacade phoneDaoFacade;
    @Autowired
    private AccountDaoFacade accountDao;
    private Account account = new Account("test", "test", "test@test.test");

    @Before
    public void initTestValues() {
        phone.setNumber("123321");
        accountDao.create(account);
        account = accountDao.select(account);
        phone.setOwner(account);
        phone.setType(PhoneType.PRIVATE);
        phoneDaoFacade.create(phone);
    }

    @After
    public void deleteTestValues() {
        accountDao.delete(account);
        phoneDaoFacade.delete(phone);
    }

    @Test
    public void testCreatePhone() {
        phoneDaoFacade.delete(phone);
        Phone newPhone = new Phone(phone.getNumber(), phone.getOwner());
        assertTrue(phoneDaoFacade.create(newPhone));
    }

    @Test
    public void testCreateExisting() {
        assertFalse(phoneDaoFacade.create(phone));
    }

    @Test
    public void testCreateWrongOwner() {
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
        Phone actual = phoneDaoFacade.select(phone);
        assertEquals(phone, actual);
    }

    @Test
    public void testSelectNonExisting() {
        assertEquals(phoneDaoFacade.getNullPhone(), phoneDaoFacade.select(99999999));
    }

    @Test
    public void testUpdatePhone() {
        phone.setType(PhoneType.WORK);
        assertTrue(phoneDaoFacade.update(phone, phoneDaoFacade.select(phone)));
    }

    @Test
    public void testUpdateNonExisting() {
        Phone nonExisting = new Phone("000000", phone.getOwner());
        Phone anotherNonExisting = new Phone("00000", phone.getOwner());
        assertFalse(phoneDaoFacade.update(nonExisting, anotherNonExisting));
    }

    @Test
    public void testDeletePhone() {
        assertTrue(phoneDaoFacade.delete(phone));
    }

    @Test
    public void testDeleteNonExisting() {
        Phone nonExisting = new Phone();
        nonExisting.setNumber("000000");
        assertFalse(phoneDaoFacade.delete(nonExisting));
    }
}
