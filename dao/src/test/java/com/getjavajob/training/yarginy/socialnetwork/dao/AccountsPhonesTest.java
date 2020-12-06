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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class AccountsPhonesTest {
    static {
        TestDataSourceInitializer.initDataSource();
    }

    private static final AccountDao ACCOUNT_DAO = new AccountDaoImpl();
    private static final PhoneDao PHONE_DAO = new PhoneDaoImpl();
    private final Collection<Phone> phones = new ArrayList<>();
    private Account account = new AccountImpl("test", "testtest", "test@test.test");

    @Before
    public void initTestValues() {
        ACCOUNT_DAO.create(account);
        account = ACCOUNT_DAO.select(account);
        phones.add(new PhoneImpl("11111111111111111111111", account));
        phones.add(new PhoneImpl("22222222222222222222222", account));
    }

    @After
    public void deleteTestValues() {
        ACCOUNT_DAO.delete(account);
        PHONE_DAO.delete(phones);
    }

    @Test
    public void testSelectPhones() {
        assertTrue(PHONE_DAO.create(phones));
        Collection<Phone> actualPhones = PHONE_DAO.selectPhonesByOwner(account.getId());
        assertEquals(phones, actualPhones);
    }
}
