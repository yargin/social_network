package com.getjavajob.training.yarginy.socialnetwork.dao;

import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.AccountImpl;
import com.getjavajob.training.yarginy.socialnetwork.dao.factories.AbstractDbFactory;
import com.getjavajob.training.yarginy.socialnetwork.dao.factories.DbFactory;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.Dao;
import com.getjavajob.training.yarginy.socialnetwork.dao.relationsdao.manytomany.selfrelated.SelfManyToManyDao;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;

import static com.getjavajob.training.yarginy.socialnetwork.dao.utils.TestResultPrinter.printPassed;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

public class FriendshipDaoTest {
    private static final DbFactory dbFactory = AbstractDbFactory.getDbFactory();
    private static final String CLASS = "AccountDaoTest";
    private static final Dao<Account> ACCOUNT_DAO = dbFactory.getAccountDao();
    private static final SelfManyToManyDao<Account> FRIENDSHIP_DAO = dbFactory.getFriendshipDao(ACCOUNT_DAO);
    private final Account friend;
    private final Account noFriends;

    public FriendshipDaoTest() {
        friend = ACCOUNT_DAO.select(1);
        noFriends = ACCOUNT_DAO.select(4);
    }

    @Test
    public void testSelectNoFriends() {
        Collection<Account> friends = FRIENDSHIP_DAO.select(noFriends);
        assertSame(true, friends.isEmpty());
        printPassed(CLASS, "testSelectNoFriends");
    }

    @Test
    public void testSelectFriends() {
        Collection<Account> friends = FRIENDSHIP_DAO.select(friend);
        Collection<Account> expected = new ArrayList<>();
        expected.add(ACCOUNT_DAO.select(2));
        expected.add(ACCOUNT_DAO.select(3));
        assertEquals(expected, friends);
        printPassed(CLASS, "testSelectFriends");
    }

    @Test
    public void testNonExistingAccount() {
        Account nonExistingAccount = new AccountImpl();
        nonExistingAccount.setId(666);
        nonExistingAccount.setEmail("email@that.doesnt.exist");
        Collection<Account> friends = FRIENDSHIP_DAO.select(nonExistingAccount);
        assertSame(true, friends.isEmpty());
        printPassed(CLASS, "testNonExistingAccount");
    }

    @Test
    public void createAndDeleteFriendship() {
        Account firstFriend = ACCOUNT_DAO.select(3);
        Account secondFriend = ACCOUNT_DAO.select(2);
        boolean created = FRIENDSHIP_DAO.create(firstFriend, secondFriend);
        assertSame(true, created);
        boolean deleted = FRIENDSHIP_DAO.delete(firstFriend, secondFriend);
        assertSame(true, deleted);
        printPassed(CLASS, "createAndDeleteFriendship");
    }

    @Test
    public void createExistingFriendship() {
        Account firstFriend = ACCOUNT_DAO.select(1);
        Account secondFriend = ACCOUNT_DAO.select(2);
        boolean created = FRIENDSHIP_DAO.create(firstFriend, secondFriend);
        assertSame(false, created);
        printPassed(CLASS, "createExistingFriendship");
    }

    @Test
    public void deleteNonExistingFriendship() {
        Account firstFriend = ACCOUNT_DAO.select(3);
        Account secondFriend = ACCOUNT_DAO.select(2);
        boolean deleted = FRIENDSHIP_DAO.delete(firstFriend, secondFriend);
        assertSame(false, deleted);
        printPassed(CLASS, "deleteNonExistingFriendship");
    }
}
