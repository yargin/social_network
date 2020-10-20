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

import static java.lang.Thread.sleep;
import static org.junit.Assert.assertFalse;

public class ConcurrentSelectUpdateTest {
    private static final String CLASS = "AccountDaoTest";
    private static final DbFactory DB_FACTORY = AbstractDbFactory.getDbFactory();
    private static final int PHONES_NUMBER = 10000;
    private final AccountDao accountDao = new AccountDaoImpl();
    private final PhoneDao firstPhoneDao = new PhoneDaoImpl();
    private final PhoneDao secondPhoneDao = new PhoneDaoImpl();
    private volatile boolean inSelectBlock;
    private volatile boolean inCreateBlock;

    @Test
    public void testSelectUpdate() throws InterruptedException {
        Account account = accountDao.select(1);
        Collection<Phone> phones = new ArrayList<>();
        for (int i = 0; i < PHONES_NUMBER; i++) {
            phones.add(new PhoneImpl("" + i + i + i + i, account));
        }

        Thread createThread = new Thread(() -> {
            if (!inSelectBlock) {
                inCreateBlock = true;
                System.out.println("started");
                firstPhoneDao.create(phones);
                System.out.println("finished");
                inSelectBlock = false;
            }
        });
        createThread.start();
        sleep(400);
        Thread selectThread = new Thread(() -> {
            if (inCreateBlock) {
                inSelectBlock = true;
                secondPhoneDao.select(555);
                System.out.println("selected");
            }
        });
        selectThread.start();

        createThread.join();

        for (Phone phone: phones) {
            firstPhoneDao.delete(phone);
        }

        assertFalse(inSelectBlock);
    }


}
