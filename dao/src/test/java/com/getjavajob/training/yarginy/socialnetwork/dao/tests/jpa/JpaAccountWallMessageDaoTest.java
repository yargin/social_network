package com.getjavajob.training.yarginy.socialnetwork.dao.tests.jpa;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.messages.AccountWallMessage;
import com.getjavajob.training.yarginy.socialnetwork.dao.configuration.TestDaoOverrideConfig;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.AccountDaoFacadeImpl;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.messages.AccountWallMessageDaoFacadeImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestDaoOverrideConfig.class})
public class JpaAccountWallMessageDaoTest {
    @Autowired
    private AccountWallMessageDaoFacadeImpl accountWallMessageDao;
    @Autowired
    private AccountDaoFacadeImpl accountDao;

    @Test
    public void testCreateAndSelectByAltMessage() {
        Account author = new Account("testAuthor", "testAuthor", "testAuthor");
        Account receiver = new Account("testReceiver", "testReceiver", "testReceiver");
        accountDao.create(author);
        accountDao.create(receiver);
        AccountWallMessage message = new AccountWallMessage();
        message.setAuthor(author);
        message.setReceiver(receiver);
        message.setDate(Timestamp.valueOf(LocalDateTime.of(2020, 12, 12, 12, 12)));
        message.setText("some message");
        assertTrue(accountWallMessageDao.create(message));
    }
}
