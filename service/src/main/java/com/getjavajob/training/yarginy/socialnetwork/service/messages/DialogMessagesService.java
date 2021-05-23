package com.getjavajob.training.yarginy.socialnetwork.service.messages;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.Dialog;
import com.getjavajob.training.yarginy.socialnetwork.common.models.messages.DialogMessage;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.messages.DialogMessageDaoFacadeImpl;
import com.getjavajob.training.yarginy.socialnetwork.service.AccountService;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collection;

import static java.util.Objects.isNull;

@Service
public class DialogMessagesService implements MessageService<Dialog, DialogMessage> {
    private final DialogMessageDaoFacadeImpl messageDao;
    private final AccountService accountService;

    public DialogMessagesService(DialogMessageDaoFacadeImpl messageDao, AccountService accountService) {
        this.messageDao = messageDao;
        this.accountService = accountService;
    }

    @Override
    public boolean addMessage(DialogMessage message) {
        if (isNull(message.getImage()) && isNull(message.getText())) {
            return false;
        }
        message.setDate(Timestamp.valueOf(LocalDateTime.now().withNano(0)));
        Account author = accountService.get(message.getAuthor().getId());
        boolean messageCreated = messageDao.create(message);
        message.setAuthor(author);
        return messageCreated;
    }

    @Override
    public boolean deleteMessage(DialogMessage message) {
        return messageDao.delete(message);
    }

    @Override
    public Collection<DialogMessage> selectMessages(long dialogId) {
        return messageDao.getMessages(dialogId);
    }
}
