package com.getjavajob.training.yarginy.socialnetwork.dao.tests.jpa;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.messages.AccountWallMessage;
import com.getjavajob.training.yarginy.socialnetwork.dao.models.messages.AccountWallMessageDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.models.AccountDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:daoSpringConfig.xml", "classpath:daoTestJpaSpringConfig.xml"})
public class JpaAccountWallMessageDaoTest {
    @Autowired
    private AccountWallMessageDao accountWallMessageDao;
    @Autowired
    private AccountDao accountDao;

    @Test
    public void testCreateAndSelectByAltMessage() {
        Account author = new Account("testAuthor", "testAuthor", "testAuthor");
        Account receiver = new Account("testReceiver", "testReceiver", "testReceiver");
        accountDao.create(author);
        accountDao.create(receiver);
        AccountWallMessage message = new AccountWallMessage();
        message.setAuthor(author);
        message.setReceiver(receiver);
        message.setPosted(Timestamp.valueOf(LocalDateTime.of(2020, 12, 12, 12, 12)));
        message.setText("some message");
        accountWallMessageDao.create(message);
        assertEquals(message, accountWallMessageDao.select(message));
    }
}
