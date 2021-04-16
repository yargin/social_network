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

import java.util.Collection;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:daoSpringConfig.xml", "classpath:daoTestOverrideSpringConfig.xml"})
public class BatchUpdateTest {
    @Autowired
    private PhoneDaoFacade phoneDaoFacade;
    @Autowired
    private AccountDaoFacade accountDaoFacade;
    private Account account = new Account("test", "test", "test@test.test");

    @Before
    public void initTestValues() {
        accountDaoFacade.create(account);
        account = accountDaoFacade.select(account);
    }

    @After
    public void deleteTestValues() {
        accountDaoFacade.delete(account);
    }

    @Test
    public void testCreateThenDelete() {
        Phone firstPhone = new Phone("100001", account);
        firstPhone.setType(PhoneType.PRIVATE);
        Phone secondPhone = new Phone("89218942", account);
        secondPhone.setType(PhoneType.PRIVATE);
        phoneDaoFacade.delete(firstPhone);
        phoneDaoFacade.delete(secondPhone);
        Collection<Phone> phones = asList(firstPhone, secondPhone);
        phoneDaoFacade.create(phones);
        Collection<Phone> allAccountPhones = phoneDaoFacade.selectPhonesByOwner(account.getId());
        assertTrue(allAccountPhones.containsAll(phones));
        assertTrue(phoneDaoFacade.delete(phones));
        phoneDaoFacade.delete(firstPhone);
        phoneDaoFacade.delete(secondPhone);
    }
}
