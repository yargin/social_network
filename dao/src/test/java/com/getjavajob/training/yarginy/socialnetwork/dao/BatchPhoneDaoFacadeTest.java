package com.getjavajob.training.yarginy.socialnetwork.dao;

import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.AccountImpl;
import com.getjavajob.training.yarginy.socialnetwork.common.models.phone.Phone;
import com.getjavajob.training.yarginy.socialnetwork.common.models.phone.PhoneImpl;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.AccountDaoFacade;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.PhoneDaoFacade;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Collection;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:daoSpringConfig.xml", "classpath:daoOverrideSpringConfig.xml"})
public class BatchPhoneDaoFacadeTest {
    @Autowired
    private PhoneDaoFacade phoneDaoFacade;
    @Autowired
    private AccountDaoFacade accountDaoFacade;
    private Collection<Phone> testPhones = new ArrayList<>();
    private Account testAccount = new AccountImpl("test", "test@test.test");

    @Before
    public void initAccountAndPhones() {
        testAccount.setSurname("tester");
        accountDaoFacade.create(testAccount);
        testAccount = accountDaoFacade.select(testAccount);
        testPhones.add(new PhoneImpl("111-111", testAccount));
        testPhones.add(new PhoneImpl("222-222", testAccount));
        testPhones.add(new PhoneImpl("333-333", testAccount));
        testPhones.add(new PhoneImpl("444-444", testAccount));
        testPhones.add(new PhoneImpl("555-555", testAccount));
        testPhones.add(new PhoneImpl("666-666", testAccount));
    }

    @After
    public void deleteAccountAndPhones() {
        testPhones = phoneDaoFacade.selectPhonesByOwner(testAccount.getId());
        phoneDaoFacade.delete(testPhones);
        accountDaoFacade.delete(testAccount);
    }

    @Test
    public void testDeleteManyPhonesAndOneNonExisting() {
        phoneDaoFacade.create(testPhones);
        testPhones = phoneDaoFacade.selectPhonesByOwner(testAccount.getId());
        testPhones.add(new PhoneImpl("777-777", testAccount));
        assertTrue(phoneDaoFacade.delete(testPhones));
    }

    @Test
    public void testCreateManyPhonesAndOneExisting() {
        phoneDaoFacade.create(testPhones.iterator().next());
        assertFalse(phoneDaoFacade.create(testPhones));
    }
}
