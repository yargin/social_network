package com.getjavajob.training.yarginy.socialnetwork.dao;

import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.AccountImpl;
import com.getjavajob.training.yarginy.socialnetwork.common.models.dialog.Dialog;
import com.getjavajob.training.yarginy.socialnetwork.common.models.dialog.DialogImpl;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.AccountFacade;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.DialogFacade;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;

import static org.junit.Assert.*;

public class DialogsTest {
    static {
        TestDataSourceInitializer.initDataSource();
    }

    @Autowired
    private DialogFacade dialogFacade;
    @Autowired
    private AccountFacade accountFacade;
    private Account firstAccount = new AccountImpl("test1", "test1", "test1@test.test");
    private Account secondAccount = new AccountImpl("test2", "test2", "test2@test.test");
    private Dialog dialog = new DialogImpl(firstAccount, secondAccount);

    @Before
    public void initTestValues() {
        accountFacade.create(firstAccount);
        firstAccount = accountFacade.select(firstAccount);
        accountFacade.create(secondAccount);
        secondAccount = accountFacade.select(secondAccount);
        dialog.setFirstAccount(firstAccount);
        dialog.setSecondAccount(secondAccount);
        dialogFacade.create(dialog);
        dialog = dialogFacade.select(dialog);
    }

    @After
    public void destroyTestValues() {
        dialogFacade.delete(dialog);
        accountFacade.delete(firstAccount);
        accountFacade.delete(secondAccount);
    }

    @Test
    public void testCreateDialog() {
        dialogFacade.delete(dialog);
        assertTrue(dialogFacade.create(dialog));
        assertEquals(dialog, dialogFacade.select(dialog));
        Dialog testDialog = new DialogImpl(secondAccount, firstAccount);
        assertFalse(dialogFacade.create(testDialog));
    }

    @Test
    public void testCreateExistingDialog() {
        assertFalse(dialogFacade.create(dialog));
    }

    @Test
    public void testSelectDialog() {
        Dialog actualDialog = dialogFacade.select(dialog);
        assertEquals(dialog, actualDialog);
    }

    @Test
    public void testSelectNonExistingDialog() {
        dialogFacade.delete(dialog);
        assertEquals(dialogFacade.getNullEntity(), dialogFacade.select(dialog));
    }

    @Test
    public void testDeleteDialog() {
        assertTrue(dialogFacade.delete(dialog));
        assertEquals(dialogFacade.getNullEntity(), dialogFacade.select(dialog));
        dialogFacade.create(dialog);
        dialog.setSecondAccount(firstAccount);
        dialog.setFirstAccount(secondAccount);
        assertTrue(dialogFacade.delete(dialog));
        assertEquals(dialogFacade.getNullEntity(), dialogFacade.select(dialog));
    }

    @Test
    public void testDeleteNonExistingDialog() {
        dialogFacade.delete(dialog);
        assertFalse(dialogFacade.delete(dialog));
    }

    @Test
    public void testSelectDialogsByAccount() {
        Collection<Dialog> firstAccountDialogs = dialogFacade.selectDialogsByAccount(firstAccount.getId());
        Collection<Dialog> secondAccountDialogs = dialogFacade.selectDialogsByAccount(secondAccount.getId());
        assertEquals(firstAccountDialogs, secondAccountDialogs);
    }

    @Test
    public void testDialogExists() {
        assertTrue(dialogFacade.isTalker(firstAccount.getId(), dialog.getId()));
        assertTrue(dialogFacade.isTalker(secondAccount.getId(), dialog.getId()));
    }
}