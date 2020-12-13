package com.getjavajob.training.yarginy.socialnetwork.dao;

import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.AccountImpl;
import com.getjavajob.training.yarginy.socialnetwork.common.models.dialog.Dialog;
import com.getjavajob.training.yarginy.socialnetwork.common.models.dialog.DialogImpl;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.AccountDaoFacade;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.DialogDaoFacade;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Collection;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:daoSpringConfig.xml", "classpath:daoOverrideSpringConfig.xml"})
public class DialogsTest {
    @Autowired
    private DialogDaoFacade dialogDaoFacade;
    @Autowired
    private AccountDaoFacade accountDaoFacade;
    private Account firstAccount = new AccountImpl("test1", "test1", "test1@test.test");
    private Account secondAccount = new AccountImpl("test2", "test2", "test2@test.test");
    private Dialog dialog = new DialogImpl(firstAccount, secondAccount);

    @Before
    public void initTestValues() {
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
        assertTrue(dialogDaoFacade.create(dialog));
        assertEquals(dialog, dialogDaoFacade.select(dialog));
        Dialog testDialog = new DialogImpl(secondAccount, firstAccount);
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
        assertEquals(dialogDaoFacade.getNullEntity(), dialogDaoFacade.select(dialog));
    }

    @Test
    public void testDeleteDialog() {
        assertTrue(dialogDaoFacade.delete(dialog));
        assertEquals(dialogDaoFacade.getNullEntity(), dialogDaoFacade.select(dialog));
        dialogDaoFacade.create(dialog);
        dialog.setSecondAccount(firstAccount);
        dialog.setFirstAccount(secondAccount);
        assertTrue(dialogDaoFacade.delete(dialog));
        assertEquals(dialogDaoFacade.getNullEntity(), dialogDaoFacade.select(dialog));
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