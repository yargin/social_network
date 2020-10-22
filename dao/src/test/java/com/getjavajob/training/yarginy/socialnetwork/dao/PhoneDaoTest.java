package com.getjavajob.training.yarginy.socialnetwork.dao;

import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.AccountImpl;
import com.getjavajob.training.yarginy.socialnetwork.common.models.phone.Phone;
import com.getjavajob.training.yarginy.socialnetwork.common.models.phone.PhoneImpl;
import com.getjavajob.training.yarginy.socialnetwork.common.models.phone.additionaldata.PhoneType;
import com.getjavajob.training.yarginy.socialnetwork.dao.batchmodeldao.BatchDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.factories.AbstractDbFactory;
import com.getjavajob.training.yarginy.socialnetwork.dao.factories.DbFactory;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.Dao;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static com.getjavajob.training.yarginy.socialnetwork.dao.utils.TestResultPrinter.printPassed;
import static org.junit.Assert.*;

public class PhoneDaoTest {
    private static final DbFactory DB_FACTORY = AbstractDbFactory.getDbFactory();
    private static final String CLASS = "GroupDaoTest";
    private static final BatchDao<Phone> PHONE_DAO = DB_FACTORY.getPhoneDao();
    private static final Dao<Account> ACCOUNT_DAO = DB_FACTORY.getAccountDao();
    private static final Phone PHONE = new PhoneImpl();

    @BeforeClass
    public static void initParams() {
        PHONE.setNumber("123321");
        PHONE.setOwner(ACCOUNT_DAO.select(1));
        PHONE.setType(PhoneType.PRIVATE);
    }

    @AfterClass
    public static void clearDb() {
        PHONE_DAO.delete(PHONE);
    }

    @Test
    public void testCreatePhone() {
        PHONE_DAO.delete(PHONE);
        assertTrue(PHONE_DAO.create(PHONE));
        printPassed(CLASS, "testCreatePhone");
    }

    @Test
    public void testCreateExisting() {
        PHONE_DAO.create(PHONE);
        assertFalse(PHONE_DAO.create(PHONE));
        printPassed(CLASS, "testCreateExisting");
    }

    @Test
    public void testCreateWrongOwner() {
        PHONE_DAO.delete(PHONE);
        Account wrongAccount = new AccountImpl();
        wrongAccount.setId(-1);
        PHONE.setOwner(wrongAccount);
        try {
            PHONE_DAO.create(PHONE);
        } catch (IllegalStateException e) {
            assertTrue(true);
        }
        PHONE.setOwner(ACCOUNT_DAO.select(1));
        printPassed(CLASS, "testCreateWrongOwner");
    }

    @Test
    public void testSelectPhone() {
        PHONE_DAO.create(PHONE);
        Phone actual = PHONE_DAO.select(PHONE);
        assertEquals(PHONE, actual);
        printPassed(CLASS, "testSelectPhone");
    }

    @Test
    public void testSelectNonExisting() {
        assertEquals(PHONE_DAO.getNullEntity(), PHONE_DAO.select(55));
        printPassed(CLASS, "testSelectNonExisting");
    }

    @Test
    public void testUpdatePhone() {
        PHONE_DAO.create(PHONE);
        PHONE.setType(PhoneType.PRIVATE);
        assertTrue(PHONE_DAO.update(PHONE));
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
        PHONE_DAO.create(PHONE);
        assertTrue(PHONE_DAO.delete(PHONE));
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
