package com.getjavajob.training.yarginy.socialnetwork.dao;

import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.phone.Phone;
import com.getjavajob.training.yarginy.socialnetwork.common.models.phone.PhoneImpl;
import com.getjavajob.training.yarginy.socialnetwork.dao.batchmodeldao.BatchDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.factories.AbstractDbFactory;
import com.getjavajob.training.yarginy.socialnetwork.dao.factories.DbFactory;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.Dao;
import com.getjavajob.training.yarginy.socialnetwork.dao.relationsdao.onetomany.OneToManyDao;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.getjavajob.training.yarginy.socialnetwork.dao.utils.TestResultPrinter.printPassed;
import static org.junit.Assert.assertEquals;

public class AccountsPhonesTest {
    public static final String CLASS = "AccountsPhonesTest";
    public static final DbFactory FACTORY = AbstractDbFactory.getDbFactory();
    public static final Dao<Account> ACCOUNT_DAO = FACTORY.getAccountDao();
    public static final BatchDao<Phone> PHONE_DAO = FACTORY.getPhoneDao();
    public static final OneToManyDao<Account, Phone> ACCOUNTS_PHONES = FACTORY.getAccountsPhones(ACCOUNT_DAO, PHONE_DAO);

    @Test
    public void testSelectPhones() {
        Account account = ACCOUNT_DAO.select(1);
        List<Phone> expectedPhones = new ArrayList<>();
        Phone phoneToAdd = new PhoneImpl();
        phoneToAdd.setNumber("+7 (920) 123-23-32");
        expectedPhones.add(phoneToAdd);
        phoneToAdd = new PhoneImpl();
        phoneToAdd.setNumber("02");
        expectedPhones.add(phoneToAdd);
        phoneToAdd = new PhoneImpl();
        phoneToAdd.setNumber("123123");
        expectedPhones.add(phoneToAdd);
        Collection<Phone> actualPhones = ACCOUNTS_PHONES.selectMany(account);
        assertEquals(expectedPhones, actualPhones);
        printPassed(CLASS, "testSelectPhones");
    }
}
