package com.getjavajob.training.yarginy.socialnetwork.dao;

import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.AccountImpl;
import com.getjavajob.training.yarginy.socialnetwork.common.models.phone.Phone;
import com.getjavajob.training.yarginy.socialnetwork.common.models.phone.PhoneImpl;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.AccountFacade;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.PhoneFacade;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class AccountsPhonesTest {
    static {
        TestDataSourceInitializer.initDataSource();
    }

    @Autowired
    private AccountFacade accountFacade;
    @Autowired
    private PhoneFacade phoneFacade;
    private final Collection<Phone> phones = new ArrayList<>();
    private Account account = new AccountImpl("test", "testtest", "test@test.test");

    @Before
    public void initTestValues() {
        accountFacade.create(account);
        account = accountFacade.select(account);
        phones.add(new PhoneImpl("11111111111111111111111", account));
        phones.add(new PhoneImpl("22222222222222222222222", account));
    }

    @After
    public void deleteTestValues() {
        accountFacade.delete(account);
        phoneFacade.delete(phones);
    }

    @Test
    public void testSelectPhones() {
        assertTrue(phoneFacade.create(phones));
        Collection<Phone> actualPhones = phoneFacade.selectPhonesByOwner(account.getId());
        assertEquals(phones, actualPhones);
    }
}
