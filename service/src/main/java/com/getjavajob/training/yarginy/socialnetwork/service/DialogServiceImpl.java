package com.getjavajob.training.yarginy.socialnetwork.service;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.Dialog;
import com.getjavajob.training.yarginy.socialnetwork.common.models.messages.Message;
import com.getjavajob.training.yarginy.socialnetwork.dao.jdbctemplates.facades.DialogDaoFacade;
import com.getjavajob.training.yarginy.socialnetwork.service.messages.MessageService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class DialogServiceImpl implements DialogService {
    private final DialogDaoFacade dialogDaoFacade;
    private final MessageService messageService;

    public DialogServiceImpl(DialogDaoFacade dialogDaoFacade, @Qualifier("dialogMessageService") MessageService
            messageService) {
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
    public boolean create(Dialog dialog, Message<Dialog> message) {
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
        return dialogDaoFacade.getNullModel();
    }
}
