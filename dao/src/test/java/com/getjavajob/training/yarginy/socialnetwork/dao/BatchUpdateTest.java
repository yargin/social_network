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

import java.util.Collection;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertTrue;

public class BatchUpdateTest {
    static {
        TestDataSourceInitializer.initDataSource();
    }

    private final AccountDao accountDao = new AccountDaoImpl();
    private final PhoneDao phoneDao = new PhoneDaoImpl();
    private Account account = new AccountImpl("test", "test", "test@test.test");

    @Before
    public void initTestValues() {
        accountDao.create(account);
        account = accountDao.select(account);
    }

    @After
    public void deleteTestValues() {
        accountDao.delete(account);
    }

    @Test
    public void testCreateThenDelete() {
        Phone firstPhone = new PhoneImpl("100001", account);
        firstPhone.setType(PhoneType.PRIVATE);
        Phone secondPhone = new PhoneImpl("89218942", account);
        secondPhone.setType(PhoneType.PRIVATE);
        phoneDao.delete(firstPhone);
        phoneDao.delete(secondPhone);
        Collection<Phone> phones = asList(firstPhone, secondPhone);
        phoneDao.create(phones);
        Collection<Phone> allAccountPhones = phoneDao.selectPhonesByOwner(account.getId());
        assertTrue(allAccountPhones.containsAll(phones));
        assertTrue(phoneDao.delete(phones));
        phoneDao.delete(firstPhone);
        phoneDao.delete(secondPhone);
    }
}
