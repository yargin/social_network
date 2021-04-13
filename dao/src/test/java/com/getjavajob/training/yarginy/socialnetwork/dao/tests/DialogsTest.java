package com.getjavajob.training.yarginy.socialnetwork.dao.tests;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.Dialog;
import com.getjavajob.training.yarginy.socialnetwork.dao.jdbctemplates.facades.AccountDaoFacade;
import com.getjavajob.training.yarginy.socialnetwork.dao.jdbctemplates.facades.DialogDaoFacade;
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
public class DialogsTest {
    @Autowired
    private DialogDaoFacade dialogDaoFacade;
    @Autowired
    private AccountDaoFacade accountDaoFacade;
    private Account firstAccount = new Account("test1", "test1", "test1@test.test");
    private Account secondAccount = new Account("test2", "test2", "test2@test.test");
    private Dialog dialog = new Dialog(firstAccount, secondAccount);

    @Before
    public void initTestValues() {
        dialogDaoFacade.delete(dialog);
        accountDaoFacade.delete(firstAccount);
        accountDaoFacade.delete(secondAccount);
        accountDaoFacade.create(firstAccount);
        firstAccount = accountDaoFacade.select(firstAccount);
        accountDaoFacade.create(secondAccount);
        secondAccount = accountDaoFacade.select(secondAccount);
        dialog.setFirstAccount(firstAccount);
        dialog.setSecondAccount(secondAccount);
        dialogDaoFacade.create(dialog);
        dialog = dialogDaoFacade.select(dialog);
    }

    @After
    public void destroyTestValues() {
        dialogDaoFacade.delete(dialog);
        accountDaoFacade.delete(firstAccount);
        accountDaoFacade.delete(secondAccount);
    }

    @Test
    public void testCreateDialog() {
        dialogDaoFacade.delete(dialog);
        assertTrue(dialogDaoFacade.create(new Dialog(firstAccount, secondAccount)));
        assertEquals(dialog, dialogDaoFacade.select(dialog));
        Dialog testDialog = new Dialog(secondAccount, firstAccount);
        assertFalse(dialogDaoFacade.create(testDialog));
    }

    @Test
    public void testCreateExistingDialog() {
        assertFalse(dialogDaoFacade.create(dialog));
    }

    @Test
    public void testSelectDialog() {
        Dialog actualDialog = dialogDaoFacade.select(dialog);
        assertEquals(dialog, actualDialog);
    }

    @Test
    public void testSelectNonExistingDialog() {
        dialogDaoFacade.delete(dialog);
        assertEquals(dialogDaoFacade.getNullModel(), dialogDaoFacade.select(dialog));
    }

    @Test
    public void testDeleteDialog() {
        assertTrue(dialogDaoFacade.delete(dialog));
        assertEquals(dialogDaoFacade.getNullModel(), dialogDaoFacade.select(dialog));
    }

    @Test
    public void testDeleteNonExistingDialog() {
        dialogDaoFacade.delete(dialog);
        assertFalse(dialogDaoFacade.delete(dialog));
    }

    @Test
    public void testSelectDialogsByAccount() {
        Collection<Dialog> firstAccountDialogs = dialogDaoFacade.selectDialogsByAccount(firstAccount.getId());
        Collection<Dialog> secondAccountDialogs = dialogDaoFacade.selectDialogsByAccount(secondAccount.getId());
        assertEquals(firstAccountDialogs, secondAccountDialogs);
    }

    @Test
    public void testDialogExists() {
        assertTrue(dialogDaoFacade.isTalker(firstAccount.getId(), dialog.getId()));
        assertTrue(dialogDaoFacade.isTalker(secondAccount.getId(), dialog.getId()));
    }
}