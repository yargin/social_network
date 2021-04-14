package com.getjavajob.training.yarginy.socialnetwork.dao.tests.jpa;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.Dialog;
import com.getjavajob.training.yarginy.socialnetwork.dao.models.AccountDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.models.DialogDao;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:daoSpringConfig.xml", "classpath:daoTestJpaSpringConfig.xml"})
public class JpaDialogDaoTest {
    private final Account firstAccount= new Account("testFirstName", "testFirstSurname", "testFirstEmail");
    private final Account secondAccount= new Account("testSecondName", "testSecondSurname", "testSecondEmail");
    private final Account thirdAccount= new Account("testThirdName", "testThirdSurname", "testThirdEmail");
    private final Dialog dialog = new Dialog(firstAccount, secondAccount);
    @Autowired
    private DialogDao dialogDao;
    @Autowired
    private AccountDao accountDao;

    @Before
    public void initValues() {
        accountDao.create(firstAccount);
        accountDao.create(secondAccount);
        accountDao.create(thirdAccount);
        dialogDao.create(dialog);
    }

    @After
    public void deleteValues() {
        dialogDao.delete(dialog);
        accountDao.delete(firstAccount);
        accountDao.delete(secondAccount);
        accountDao.delete(thirdAccount);
    }

    @Test
    public void testSelectAll() {
        Collection<Dialog> dialogs = dialogDao.selectAll();
        assertTrue(dialogs.size() > 0);
    }

    @Test
    public void testGetDialogById() {
        Dialog selectedDialog = dialogDao.select(dialog.getId());
        assertEquals(dialog, selectedDialog);
        selectedDialog = dialogDao.select(22);
        assertEquals(dialogDao.getNullModel(), selectedDialog);
    }

    @Test
    public void testGetDialogByIdentifier() {
        assertEquals(dialog, dialogDao.select(dialog));
        assertEquals(dialogDao.getNullModel(), dialogDao.select(new Dialog(thirdAccount, secondAccount)));
    }

    @Test
    public void testDeleteDialog() {
        assertTrue(dialogDao.delete(dialog));
        Collection<Dialog> dialogs = dialogDao.selectAll();
        assertEquals(0, dialogs.size());
        assertFalse(dialogDao.delete(dialog));
    }

    @Test
    public void testCreateDialog() {
        assertFalse(dialogDao.create(dialog));
        Dialog newDialog = new Dialog(thirdAccount, secondAccount);
        assertTrue(dialogDao.create(newDialog));
        assertEquals(newDialog, dialogDao.select(newDialog));
        assertFalse(dialogDao.create(newDialog));
        assertFalse(dialogDao.create(new Dialog(thirdAccount, secondAccount)));
        dialogDao.delete(newDialog);
    }
}
