package com.getjavajob.training.yarginy.socialnetwork.dao;

import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.AccountImpl;
import com.getjavajob.training.yarginy.socialnetwork.common.models.phone.Phone;
import com.getjavajob.training.yarginy.socialnetwork.common.models.phone.PhoneImpl;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.AccountFacade;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.AccountFacadeImpl;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.PhoneFacade;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.PhoneFacadeImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class BatchPhoneFacadeTest {
    static {
        TestDataSourceInitializer.initDataSource();
    }

    private final AccountFacade accountFacade = new AccountFacadeImpl();
    private final PhoneFacade phoneFacade = new PhoneFacadeImpl();
    private Collection<Phone> testPhones = new ArrayList<>();
    private Account testAccount = new AccountImpl("test", "test@test.test");

    @Before
    public void initAccountAndPhones() {
        testAccount.setSurname("tester");
        accountFacade.create(testAccount);
        testAccount = accountFacade.select(testAccount);
        testPhones.add(new PhoneImpl("111-111", testAccount));
        testPhones.add(new PhoneImpl("222-222", testAccount));
        testPhones.add(new PhoneImpl("333-333", testAccount));
        testPhones.add(new PhoneImpl("444-444", testAccount));
        testPhones.add(new PhoneImpl("555-555", testAccount));
        testPhones.add(new PhoneImpl("666-666", testAccount));
    }

    @After
    public void deleteAccountAndPhones() {
        testPhones = phoneFacade.selectPhonesByOwner(testAccount.getId());
        phoneFacade.delete(testPhones);
        accountFacade.delete(testAccount);
    }

    @Test
    public void testDeleteManyPhonesAndOneNonExisting() {
        phoneFacade.create(testPhones);
        testPhones = phoneFacade.selectPhonesByOwner(testAccount.getId());
        testPhones.add(new PhoneImpl("777-777", testAccount));
        assertTrue(phoneFacade.delete(testPhones));
    }

    @Test
    public void testCreateManyPhonesAndOneExisting() {
        phoneFacade.create(testPhones.iterator().next());
        assertFalse(phoneFacade.create(testPhones));
    }
}
