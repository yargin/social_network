package com.getjavajob.training.yarginy.socialnetwork.dao;

import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.phone.Phone;
import com.getjavajob.training.yarginy.socialnetwork.common.models.phone.PhoneImpl;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.AccountDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.AccountDaoImpl;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.PhoneDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.PhoneDaoImpl;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;

import static com.getjavajob.training.yarginy.socialnetwork.dao.utils.TestResultPrinter.printPassed;
import static java.lang.Thread.sleep;
import static org.junit.Assert.assertFalse;

public class ConcurrentSelectUpdateTest {
    private static final String CLASS = "AccountDaoTest";
    private static final int PHONES_NUMBER = 4000;
    private final AccountDao accountDao = new AccountDaoImpl();
    private final PhoneDao firstPhoneDao = new PhoneDaoImpl();
    private final PhoneDao secondPhoneDao = new PhoneDaoImpl();
    private volatile boolean inFirstBlock;
    private volatile boolean inSecondBlock;

    @Before
    public void deletePhones() {
        Account account = accountDao.select(1);
        Collection<Phone> phones = new ArrayList<>();
        for (int i = 0; i < PHONES_NUMBER; i++) {
            phones.add(new PhoneImpl("" + i + i + i + i, account));
        }
        firstPhoneDao.delete(phones);
        Phone secondPhone = new PhoneImpl("123123", account);
        firstPhoneDao.delete(secondPhone);
    }

    @Test
    public void tryToDelete() {
        Account account = accountDao.select(1);
        Collection<Phone> phones = new ArrayList<>();
        for (int i = 0; i < PHONES_NUMBER; i++) {
            phones.add(new PhoneImpl("" + i + i + i + i, account));
        }
        firstPhoneDao.delete(phones);
        Phone secondPhone = new PhoneImpl("123123", account);
        firstPhoneDao.delete(secondPhone);
    }

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
        sleep(400);
        firstThread.start();
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
