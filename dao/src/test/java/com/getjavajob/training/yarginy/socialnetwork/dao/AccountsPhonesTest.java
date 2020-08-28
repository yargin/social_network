package com.getjavajob.training.yarginy.socialnetwork.dao;

import com.getjavajob.training.yarginy.socialnetwork.common.entities.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.entities.phone.Phone;
import com.getjavajob.training.yarginy.socialnetwork.common.entities.phone.PhoneImpl;
import com.getjavajob.training.yarginy.socialnetwork.dao.entities.Dao;
import com.getjavajob.training.yarginy.socialnetwork.dao.factories.AbstractDbFactory;
import com.getjavajob.training.yarginy.socialnetwork.dao.factories.DbFactory;
import com.getjavajob.training.yarginy.socialnetwork.dao.relations.onetomany.OneToManyDao;
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
    public static final Dao<Phone> PHONE_DAO = FACTORY.getPhoneDao();
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
        Collection<Phone> actualPhones = ACCOUNTS_PHONES.selectMany(account);
        assertEquals(expectedPhones, actualPhones);
        printPassed(CLASS, "testSelectPhones");
    }

    @Test
    public void testSelectAccount() {
        Account expectedAccount = ACCOUNT_DAO.select(1);
        //todo: select by identifier??
        Phone phone = PHONE_DAO.select("+7 (920) 123-23-32");
        Account actualAccount = ACCOUNTS_PHONES.selectOne(phone);
        assertEquals(expectedAccount, actualAccount);
        printPassed(CLASS, "testSelectAccount");
    }
}
