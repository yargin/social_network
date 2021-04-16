package com.getjavajob.training.yarginy.socialnetwork.dao.tests.jpa;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.messages.AccountWallMessage;
import com.getjavajob.training.yarginy.socialnetwork.dao.models.messages.AccountWallMessageDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.models.AccountDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.relationsdao.onetomany.AccountWallMessagesDao;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Collection;

import static java.sql.Timestamp.valueOf;
import static java.time.LocalDateTime.of;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:daoSpringConfig.xml", "classpath:daoTestOverrideSpringConfig.xml"})
public class JpaAccountWallMessagesTest {
    @Autowired
    private AccountWallMessagesDao accountWallMessagesDao;
    @Autowired
    private AccountWallMessageDao messageDao;
    @Autowired
    private AccountDao accountDao;
    private Account account = new Account("testName", "testSurname", "testEmail");
    private Account author = new Account("testAuthor", "testAuthor", "testAuthor");

    @Before
    public void initValues() {
        if (!accountDao.create(account)) {
            account = accountDao.select(account);
        }
        if (!accountDao.create(author)) {
            author = accountDao.select(author);
        }
    }

    @After
    public void deleteTestValues() {
        accountDao.delete(account);
        accountDao.delete(author);
    }

    @Test
    public void testSelectMessagesByAccountWall() {
        AccountWallMessage firstMessage = new AccountWallMessage();
        firstMessage.setText("first message");
        firstMessage.setReceiver(account);
        firstMessage.setAuthor(author);
        firstMessage.setDate(valueOf(of(2020, 2, 2, 2, 2)));
        messageDao.create(firstMessage);
        AccountWallMessage secondMessage = new AccountWallMessage();
        secondMessage.setText("secondMessage");
        secondMessage.setAuthor(account);
        secondMessage.setReceiver(account);
        secondMessage.setDate(valueOf(of(2020, 2, 2, 2, 2)));
        messageDao.create(secondMessage);
        Collection<AccountWallMessage> messages = accountWallMessagesDao.selectMany(account.getId());
        assertEquals(2, messages.size());
        messageDao.delete(firstMessage);
        messageDao.delete(secondMessage);
    }

    @Test
    public void testMessageExists() {
        AccountWallMessage message = new AccountWallMessage();
        message.setText("first message");
        message.setReceiver(account);
        message.setAuthor(author);
        message.setDate(valueOf(of(2020, 2, 2, 2, 2)));
        assertTrue(messageDao.create(message));
        assertTrue(accountWallMessagesDao.relationExists(account.getId(), message.getId()));
        messageDao.delete(message);
    }
}
