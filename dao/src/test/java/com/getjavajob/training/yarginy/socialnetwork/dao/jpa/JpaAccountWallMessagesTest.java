package com.getjavajob.training.yarginy.socialnetwork.dao.jpa;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.messages.AccountWallMessage;
import com.getjavajob.training.yarginy.socialnetwork.dao.jpa.messages.JpaAccountWallMessageDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.jpa.relationsdao.onetomany.JpaAccountWallMessagesDao;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Collection;

import static java.sql.Timestamp.valueOf;
import static java.time.LocalDateTime.of;
import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:daoSpringConfig.xml", "classpath:daoTestJpaSpringConfig.xml"})
public class JpaAccountWallMessagesTest {
    @Autowired
    private JpaAccountWallMessagesDao accountWallMessagesDao;
    @Autowired
    private JpaAccountWallMessageDao messageDao;
    @Autowired
    private JpaAccountDao accountDao;
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

    @Test
    public void testSelectMessagesByAccountWall() {
        AccountWallMessage firstMessage = new AccountWallMessage();
        firstMessage.setText("first message");
        firstMessage.setReceiver(account);
        firstMessage.setAuthor(author);
        firstMessage.setPosted(valueOf(of(2020, 2, 2, 2, 2)));
        messageDao.create(firstMessage);
        AccountWallMessage secondMessage = new AccountWallMessage();
        secondMessage.setText("secondMessage");
        secondMessage.setAuthor(account);
        secondMessage.setReceiver(account);
        secondMessage.setPosted(valueOf(of(2020, 2, 2, 2, 2)));
        messageDao.create(secondMessage);
        Collection<AccountWallMessage> messages = accountWallMessagesDao.selectMany(account.getId());
        assertEquals(asList(firstMessage, secondMessage), messages);
        messageDao.delete(firstMessage);
        messageDao.delete(secondMessage);
    }

    @Test
    public void testMessageExists() {
        AccountWallMessage message = new AccountWallMessage();
        message.setText("first message");
        message.setReceiver(account);
        message.setAuthor(author);
        message.setPosted(valueOf(of(2020, 2, 2, 2, 2)));
        assertTrue(messageDao.create(message));
        assertTrue(accountWallMessagesDao.relationExists(account.getId(), message.getId()));
        messageDao.delete(message);
    }
}
