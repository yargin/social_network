package com.getjavajob.training.yarginy.socialnetwork.dao.jpa;

import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.group.Group;
import com.getjavajob.training.yarginy.socialnetwork.dao.jdbctemplates.modeldao.AccountDao;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:daoSpringConfig.xml", "classpath:daoTestJpaSpringConfig.xml"})
public class GroupDaoTest {
    private final Account owner= new Account("testName", "testSurname", "testEmail");
    private final Group group = new Group("testName", owner);
    @Autowired
    private JpaGroupDao groupDao;
    @Autowired
    private AccountDao accountDao;

    @Before
    public void initValue() {
//        groupDao.delete(group);
//        groupDao.create(group);
    }

    @Test
    public void testCreateGroup() {
        accountDao.create(owner);
        group.setOwner(owner);
        group.setName("testName");
        assertTrue(groupDao.create(group));
        assertTrue(group.getId() > 0);
        //todo
        assertFalse(groupDao.create(new Group("testName", owner)));
    }

    @Test
    public void testUpdateGroup() {
        Group group = new Group();
    }
}
