package com.getjavajob.training.yarginy.socialnetwork.dao;

import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.AccountImpl;
import com.getjavajob.training.yarginy.socialnetwork.common.models.phone.Phone;
import com.getjavajob.training.yarginy.socialnetwork.common.models.phone.PhoneImpl;
import com.getjavajob.training.yarginy.socialnetwork.dao.batchmodeldao.BatchDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.AccountDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.AccountDaoImpl;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.PhoneDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.PhoneDaoImpl;
import com.getjavajob.training.yarginy.socialnetwork.dao.factories.AbstractDbFactory;
import com.getjavajob.training.yarginy.socialnetwork.dao.factories.DbFactory;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.Dao;
import com.getjavajob.training.yarginy.socialnetwork.dao.relationsdao.onetomany.OneToManyDao;
import org.junit.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.getjavajob.training.yarginy.socialnetwork.dao.utils.TestResultPrinter.printPassed;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class AccountsPhonesTest {
    private static final String CLASS = "AccountsPhonesTest";
    private static final AccountDao ACCOUNT_DAO = new AccountDaoImpl();
    private static final PhoneDao PHONE_DAO = new PhoneDaoImpl();
    private final Collection<Phone> phones = new ArrayList<>();
    private final Account account = new AccountImpl("test", "test@test.test");

    @Before
    public void initTestValues() {
        account.setSurname("testtest");
        ACCOUNT_DAO.create(account);
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
        Collection<Phone> actualPhones = PHONE_DAO.selectPhonesByOwner(account);
        assertEquals(phones, actualPhones);
        printPassed(CLASS, "testSelectPhones");
    }
}
