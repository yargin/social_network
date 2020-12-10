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

import java.util.Collection;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertTrue;

public class BatchUpdateTest {
    static {
        TestDataSourceInitializer.initDataSource();
    }

    @Autowired
    private PhoneFacade phoneFacade;
    @Autowired
    private AccountFacade accountFacade;
    private Account account = new AccountImpl("test", "test", "test@test.test");

    @Before
    public void initTestValues() {
        accountFacade.create(account);
        account = accountFacade.select(account);
    }

    @After
    public void deleteTestValues() {
        accountFacade.delete(account);
    }

    @Test
    public void testCreateThenDelete() {
        Phone firstPhone = new PhoneImpl("100001", account);
        firstPhone.setType(PhoneType.PRIVATE);
        Phone secondPhone = new PhoneImpl("89218942", account);
        secondPhone.setType(PhoneType.PRIVATE);
        phoneFacade.delete(firstPhone);
        phoneFacade.delete(secondPhone);
        Collection<Phone> phones = asList(firstPhone, secondPhone);
        phoneFacade.create(phones);
        Collection<Phone> allAccountPhones = phoneFacade.selectPhonesByOwner(account.getId());
        assertTrue(allAccountPhones.containsAll(phones));
        assertTrue(phoneFacade.delete(phones));
        phoneFacade.delete(firstPhone);
        phoneFacade.delete(secondPhone);
    }
}
