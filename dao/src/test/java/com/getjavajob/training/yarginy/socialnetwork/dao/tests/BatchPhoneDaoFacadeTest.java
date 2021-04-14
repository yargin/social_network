package com.getjavajob.training.yarginy.socialnetwork.dao.tests;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.Phone;
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
@ContextConfiguration(locations = {"classpath:daoSpringConfig.xml", "classpath:daoTestJpaSpringConfig.xml"})
public class BatchPhoneDaoFacadeTest {
    @Autowired
    private PhoneDaoFacade phoneDaoFacade;
    @Autowired
    private AccountDaoFacade accountDaoFacade;
    private Collection<Phone> testPhones = new ArrayList<>();
    private Account testAccount = new Account("test", "test@test.test");

    @Before
    public void initAccountAndPhones() {
        testAccount.setSurname("tester");
        accountDaoFacade.create(testAccount);
        testAccount = accountDaoFacade.select(testAccount);
        testPhones.add(new Phone("111-111", testAccount));
        testPhones.add(new Phone("222-222", testAccount));
        testPhones.add(new Phone("333-333", testAccount));
        testPhones.add(new Phone("444-444", testAccount));
        testPhones.add(new Phone("555-555", testAccount));
        testPhones.add(new Phone("666-666", testAccount));
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
        testPhones.add(new Phone("777-777", testAccount));
        assertFalse(phoneDaoFacade.delete(testPhones));
    }

    @Test
    public void testCreateManyPhonesAndOneExisting() {
        phoneDaoFacade.create(testPhones.iterator().next());
        assertFalse(phoneDaoFacade.create(testPhones));
    }
}
