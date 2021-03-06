package com.getjavajob.training.yarginy.socialnetwork.service;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.Dialog;
import com.getjavajob.training.yarginy.socialnetwork.common.models.messages.DialogMessage;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.DialogDaoFacade;
import com.getjavajob.training.yarginy.socialnetwork.service.messages.DialogMessagesService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DialogServiceImpl implements DialogService {
    private final DialogDaoFacade dialogDaoFacade;
    private final DialogMessagesService messageService;

    public DialogServiceImpl(DialogDaoFacade dialogDaoFacade, DialogMessagesService messageService) {
        this.dialogDaoFacade = dialogDaoFacade;
        this.messageService = messageService;
    }

    @Override
    public Dialog get(long dialogId) {
        return dialogDaoFacade.select(dialogId);
    }

    @Override
    public Dialog get(Dialog dialog) {
        return dialogDaoFacade.select(dialog);
    }

    @Override
    @Transactional
    public boolean create(Dialog dialog, DialogMessage message) {
        if (!dialogDaoFacade.create(dialog)) {
            throw new IllegalStateException();
        }
        message.setReceiver(dialogDaoFacade.select(dialog));
        if (!messageService.addMessage(message)) {
            throw new IllegalStateException();
        }
        return true;
    }

    @Override
    public boolean delete(Dialog dialog) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Dialog getByTalkers(long firstAccountId, long secondAccountId) {
        Dialog dialog = new Dialog();
        Account firstAccount = new Account(firstAccountId);
        dialog.setFirstAccount(firstAccount);
        Account secondAccount = new Account(secondAccountId);
        dialog.setSecondAccount(secondAccount);
        return dialogDaoFacade.select(dialog);
    }

    @Override
    public boolean isTalker(long accountId, long dialogId) {
        return dialogDaoFacade.isTalker(accountId, dialogId);
    }

    @Override
    public Dialog getNullDialog() {
        return dialogDaoFacade.getNullDialog();
    }
}
