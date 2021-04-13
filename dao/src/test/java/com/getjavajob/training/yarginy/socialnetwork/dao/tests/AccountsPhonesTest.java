package com.getjavajob.training.yarginy.socialnetwork.dao.tests;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.Phone;
import com.getjavajob.training.yarginy.socialnetwork.dao.jdbctemplates.facades.AccountDaoFacade;
import com.getjavajob.training.yarginy.socialnetwork.dao.jdbctemplates.facades.PhoneDaoFacade;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:daoSpringConfig.xml", "classpath:daoTestJpaSpringConfig.xml"})
public class AccountsPhonesTest {
    @Autowired
    private AccountDaoFacade accountDaoFacade;
    @Autowired
    private PhoneDaoFacade phoneDaoFacade;
    private final Collection<Phone> phones = new ArrayList<>();
    private Account account = new Account("test", "testtest", "test@test.test");

    @Before
    public void initTestValues() {
        accountDaoFacade.create(account);
        account = accountDaoFacade.select(account);
        phones.add(new Phone("11111111111111111111111", account));
        phones.add(new Phone("22222222222222222222222", account));
    }

    @After
    public void deleteTestValues() {
        accountDaoFacade.delete(account);
        phoneDaoFacade.delete(phones);
    }

    @Test
    public void testSelectPhones() {
        assertTrue(phoneDaoFacade.create(phones));
        Collection<Phone> actualPhones = phoneDaoFacade.selectPhonesByOwner(account.getId());
        assertEquals(phones, actualPhones);
    }
}
