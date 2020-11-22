package com.getjavajob.training.yarginy.socialnetwork.dao;

import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.AccountImpl;
import com.getjavajob.training.yarginy.socialnetwork.common.models.dialog.Dialog;
import com.getjavajob.training.yarginy.socialnetwork.common.models.dialog.DialogImpl;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.AccountDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.AccountDaoImpl;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.DialogDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.DialogDaoImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;

import static org.junit.Assert.*;

public class DialogsTest {
    private final DialogDao dialogDao = new DialogDaoImpl();
    private final AccountDao accountDao = new AccountDaoImpl();
    private Account firstAccount = new AccountImpl("test1", "test1", "test1@test.test");
    private Account secondAccount = new AccountImpl("test2", "test2", "test2@test.test");
    private Dialog dialog = new DialogImpl(firstAccount, secondAccount);

    @Before
    public void initTestValues() {
        accountDao.create(firstAccount);
        firstAccount = accountDao.select(firstAccount);
        accountDao.create(secondAccount);
        secondAccount = accountDao.select(secondAccount);
        dialog.setFirstAccount(firstAccount);
        dialog.setSecondAccount(secondAccount);
        dialogDao.create(dialog);
        dialog = dialogDao.select(dialog);
    }

    @After
    public void destroyTestValues() {
        dialogDao.delete(dialog);
        accountDao.delete(firstAccount);
        accountDao.delete(secondAccount);
    }

    @Test
    public void testCreateDialog() {
        dialogDao.delete(dialog);
        assertTrue(dialogDao.create(dialog));
        assertEquals(dialog, dialogDao.select(dialog));
        Dialog testDialog = new DialogImpl(secondAccount, firstAccount);
        assertFalse(dialogDao.create(testDialog));
    }

    @Test
    public void testCreateExistingDialog() {
        assertFalse(dialogDao.create(dialog));
    }

    @Test
    public void testSelectDialog() {
        Dialog actualDialog = dialogDao.select(dialog);
        assertEquals(dialog, actualDialog);
    }

    @Test
    public void testSelectNonExistingDialog() {
        dialogDao.delete(dialog);
        assertEquals(dialogDao.getNullEntity(), dialogDao.select(dialog));
    }

    @Test
    public void testDeleteDialog() {
        assertTrue(dialogDao.delete(dialog));
        assertEquals(dialogDao.getNullEntity(), dialogDao.select(dialog));
        dialogDao.create(dialog);
        dialog.setSecondAccount(firstAccount);
        dialog.setFirstAccount(secondAccount);
        assertTrue(dialogDao.delete(dialog));
        assertEquals(dialogDao.getNullEntity(), dialogDao.select(dialog));
    }

    @Test
    public void testDeleteNonExistingDialog() {
        dialogDao.delete(dialog);
        assertFalse(dialogDao.delete(dialog));
    }

    @Test
    public void testSelectDialogsByAccount() {
        Collection<Dialog> firstAccountDialogs = dialogDao.selectDialogsByAccount(firstAccount.getId());
        Collection<Dialog> secondAccountDialogs = dialogDao.selectDialogsByAccount(secondAccount.getId());
        assertEquals(firstAccountDialogs, secondAccountDialogs);
    }

    @Test
    public void testDialogExists() {
        assertTrue(dialogDao.isTalker(firstAccount.getId(), dialog.getId()));
        assertTrue(dialogDao.isTalker(secondAccount.getId(), dialog.getId()));
    }
}