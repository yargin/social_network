package com.getjavajob.training.yarginy.socialnetwork.dao;

import com.getjavajob.training.yarginy.socialnetwork.common.entities.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.entities.account.AccountImpl;
import com.getjavajob.training.yarginy.socialnetwork.common.entities.phone.Phone;
import com.getjavajob.training.yarginy.socialnetwork.common.entities.phone.PhoneImpl;
import com.getjavajob.training.yarginy.socialnetwork.common.entities.phone.additionaldata.PhoneType;
import com.getjavajob.training.yarginy.socialnetwork.dao.entities.Dao;
import com.getjavajob.training.yarginy.socialnetwork.dao.factories.AbstractDbFactory;
import com.getjavajob.training.yarginy.socialnetwork.dao.factories.DbFactory;
import org.junit.BeforeClass;
import org.junit.Test;

import static com.getjavajob.training.yarginy.socialnetwork.dao.utils.TestResultPrinter.printPassed;
import static org.junit.Assert.*;

public class PhoneDaoTest {
    private static final DbFactory DB_FACTORY = AbstractDbFactory.getDbFactory();
    private static final String CLASS = "GroupDaoTest";
    private static final Dao<Phone> PHONE_DAO = DB_FACTORY.getPhoneDao();
    private static final Dao<Account> ACCOUNT_DAO = DB_FACTORY.getAccountDao();
    private static final Phone phone = new PhoneImpl();

    @BeforeClass
    public static void initParams() {
        phone.setNumber("123321");
        phone.setOwner(ACCOUNT_DAO.select(1));
        phone.setType(PhoneType.PRIVATE);
    }

    @Test
    public void testCreatePhone() {
        PHONE_DAO.delete(phone);
        assertTrue(PHONE_DAO.create(phone));
        printPassed(CLASS, "testCreatePhone");
    }

    @Test
    public void testCreateExisting() {
        PHONE_DAO.create(phone);
        assertFalse(PHONE_DAO.create(phone));
        printPassed(CLASS, "testCreateExisting");
    }

    @Test
    public void testCreateWrongOwner() {
        PHONE_DAO.delete(phone);
        Account wrongAccount = new AccountImpl();
        wrongAccount.setId(-1);
        phone.setOwner(wrongAccount);
        try {
            PHONE_DAO.create(phone);
        } catch (IllegalStateException e) {
            assertTrue(true);
        }
        phone.setOwner(ACCOUNT_DAO.select(1));
        printPassed(CLASS, "testCreateWrongOwner");
    }

    @Test
    public void testSelectPhone() {
        PHONE_DAO.create(phone);
        Phone actual = PHONE_DAO.select(phone.getIdentifier());
        assertEquals(phone, actual);
        printPassed(CLASS, "testSelectPhone");
    }

    @Test
    public void testSelectNonExisting() {
        assertEquals(PHONE_DAO.getNullEntity(), PHONE_DAO.select("00"));
        printPassed(CLASS, "testSelectNonExisting");
    }

    @Test
    public void testUpdatePhone() {
        PHONE_DAO.create(phone);
        phone.setType(PhoneType.PRIVATE);
        assertTrue(PHONE_DAO.update(phone));
        printPassed(CLASS, "testUpdatePhone");
    }

    @Test
    public void testUpdateNonExisting() {
        Phone nonExisting = new PhoneImpl();
        nonExisting.setNumber("000000");
        assertFalse(PHONE_DAO.update(nonExisting));
        printPassed(CLASS, "testUpdateNonExisting");
    }

    @Test
    public void testDeletePhone() {
        PHONE_DAO.create(phone);
        assertTrue(PHONE_DAO.delete(phone));
        printPassed(CLASS, "testDeletePhone");
    }

    @Test
    public void testDeleteNonExisting() {
        Phone nonExisting = new PhoneImpl();
        nonExisting.setNumber("000000");
        assertFalse(PHONE_DAO.delete(nonExisting));
        printPassed(CLASS, "testDeleteNonExisting");
    }
}
