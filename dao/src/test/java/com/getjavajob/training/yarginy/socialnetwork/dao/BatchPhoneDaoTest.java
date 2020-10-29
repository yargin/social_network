package com.getjavajob.training.yarginy.socialnetwork.dao;

import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.AccountImpl;
import com.getjavajob.training.yarginy.socialnetwork.common.models.phone.Phone;
import com.getjavajob.training.yarginy.socialnetwork.common.models.phone.PhoneImpl;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.AccountDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.AccountDaoImpl;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.PhoneDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.PhoneDaoImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class BatchPhoneDaoTest {
    private final AccountDao accountDao = new AccountDaoImpl();
    private final PhoneDao phoneDao = new PhoneDaoImpl();
    private Collection<Phone> testPhones = new ArrayList<>();
    private Account testAccount = new AccountImpl("test", "test@test.test");

    @Before
    public void initAccountAndPhones() {
        testAccount.setSurname("tester");
        accountDao.create(testAccount);
        testAccount = accountDao.select(testAccount);
        testPhones.add(new PhoneImpl("111-111", testAccount));
        testPhones.add(new PhoneImpl("222-222", testAccount));
        testPhones.add(new PhoneImpl("333-333", testAccount));
        testPhones.add(new PhoneImpl("444-444", testAccount));
        testPhones.add(new PhoneImpl("555-555", testAccount));
        testPhones.add(new PhoneImpl("666-666", testAccount));
    }

    @After
    public void deleteAccountAndPhones() {
        testPhones = phoneDao.selectPhonesByOwner(testAccount);
        phoneDao.delete(testPhones);
        accountDao.delete(testAccount);
    }

    @Test
    public void testDeleteManyPhonesAndOneNonExisting() {
        System.out.println(testPhones);
        assertTrue(phoneDao.create(testPhones));
        testPhones = phoneDao.selectPhonesByOwner(testAccount);
        testPhones.add(new PhoneImpl("777-777", testAccount));
        assertFalse(phoneDao.delete(testPhones));
        System.out.println("delete phones: " + phoneDao.selectPhonesByOwner(testAccount));
    }

    @Test
    public void testCreateManyPhonesAndOneExisting() {
        System.out.println(testPhones);
        phoneDao.create(testPhones.iterator().next());
        assertFalse(phoneDao.create(testPhones));
        System.out.println("create phones: " + phoneDao.selectPhonesByOwner(testAccount));
    }
}
