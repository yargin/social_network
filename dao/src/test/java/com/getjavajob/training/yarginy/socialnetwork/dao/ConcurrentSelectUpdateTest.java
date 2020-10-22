package com.getjavajob.training.yarginy.socialnetwork.dao;

import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.phone.Phone;
import com.getjavajob.training.yarginy.socialnetwork.common.models.phone.PhoneImpl;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.AccountDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.AccountDaoImpl;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.PhoneDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.PhoneDaoImpl;
import com.getjavajob.training.yarginy.socialnetwork.dao.factories.AbstractDbFactory;
import com.getjavajob.training.yarginy.socialnetwork.dao.factories.DbFactory;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;

import static com.getjavajob.training.yarginy.socialnetwork.dao.utils.TestResultPrinter.printPassed;
import static java.lang.Thread.sleep;
import static org.junit.Assert.assertFalse;

public class ConcurrentSelectUpdateTest {
    private static final String CLASS = "AccountDaoTest";
    private static final DbFactory DB_FACTORY = AbstractDbFactory.getDbFactory();
    private static final int PHONES_NUMBER = 1000;
    private final AccountDao accountDao = new AccountDaoImpl();
    private final PhoneDao firstPhoneDao = new PhoneDaoImpl();
    private final PhoneDao secondPhoneDao = new PhoneDaoImpl();
    private volatile boolean inFirstBlock;
    private volatile boolean inSecondBlock;

    @Test
    public void testConcurrentCreate() throws InterruptedException {
        Account account = accountDao.select(1);
        Collection<Phone> phones = new ArrayList<>();
        for (int i = 0; i < PHONES_NUMBER; i++) {
            phones.add(new PhoneImpl("" + i + i + i + i, account));
        }

        Thread firstThread = new Thread(() -> {
            if (!inFirstBlock) {
                inSecondBlock = true;
                System.out.println("started");
                firstPhoneDao.create(phones);
                System.out.println("finished");
                inFirstBlock = false;
            }
        });
        firstThread.start();
        sleep(400);
        Phone secondPhone = new PhoneImpl("123123", account);
        Thread secondThread = new Thread(() -> {
            if (inSecondBlock) {
                inFirstBlock = true;
                secondPhoneDao.create(secondPhone);
                System.out.println("selected");
            }
        });
        secondThread.start();

        firstThread.join();

        firstPhoneDao.delete(phones);
        firstPhoneDao.delete(secondPhone);

        assertFalse(inFirstBlock);
        printPassed(CLASS, "testConcurrentSelectUpdate");
    }
}
