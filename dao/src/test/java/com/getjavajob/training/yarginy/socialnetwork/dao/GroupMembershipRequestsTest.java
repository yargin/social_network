package com.getjavajob.training.yarginy.socialnetwork.dao;

import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.AccountImpl;
import com.getjavajob.training.yarginy.socialnetwork.common.models.group.Group;
import com.getjavajob.training.yarginy.socialnetwork.common.models.group.GroupImpl;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.*;
import org.junit.After;
import org.junit.Before;

public class GroupMembershipRequestsTest {
    private final GroupsModeratorsDao groupsModeratorsDao = new GroupsModeratorsDaoImpl();
    private final AccountDao accountDao = new AccountDaoImpl();
    private final GroupDao groupDao = new GroupDaoImpl();
    private Account owner = new AccountImpl("firstTest", "test", "first@test.test");
    private Account moderator = new AccountImpl("secondTEst", "test", "second@test.test");
    private Group group = new GroupImpl("testGroup", owner);

    @Before
    public void initTestValues() {
        assert accountDao.create(owner);
        assert accountDao.create(moderator);
        assert groupDao.create(group);
    }

    @After
    public void deleteTestValues() {
        assert groupDao.delete(group);
        assert accountDao.delete(moderator);
        assert accountDao.delete(owner);
    }

//    @Test
//    public void testCreateFriendshipRequest() {
//        Collection<Account> accounts = accountDao.selectAll();
//        assertTrue(accountsFriendshipsDao.createRequest(firstAccount, secondAccount));
//    }
//
//    @Test
//    public void testCreateExistingRequest() {
//        assert accountsFriendshipsDao.createRequest(firstAccount, secondAccount);
//        assertFalse(accountsFriendshipsDao.createRequest(firstAccount, secondAccount));
//    }
//
//    @Test(expected = IllegalArgumentException.class)
//    public void testCreateRequestToNonExistingAccount() {
//        Account account = new AccountImpl("test", "test", "test@test.test");
//        assertFalse(accountsFriendshipsDao.createRequest(firstAccount, account));
//    }
//
//    @Test
//    public void testSelectRequests() {
//        accountsFriendshipsDao.createRequest(firstAccount, secondAccount);
//        Collection<Account> expected = new ArrayList<>();
//        expected.add(firstAccount);
//        assertEquals(expected, accountsFriendshipsDao.selectRequests(secondAccount));
//    }
}
