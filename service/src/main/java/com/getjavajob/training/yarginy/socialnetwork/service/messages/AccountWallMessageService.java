package com.getjavajob.training.yarginy.socialnetwork.service.messages;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.messages.AccountWallMessage;
import com.getjavajob.training.yarginy.socialnetwork.dao.jdbctemplates.facades.messages.AccountWallMessageDaoFacade;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collection;

import static java.util.Objects.isNull;

@Service("accountWallMessageService")
public class AccountWallMessageService implements MessageService<Account, AccountWallMessage> {
    private final AccountWallMessageDaoFacade messageDao;

    public AccountWallMessageService(AccountWallMessageDaoFacade messageDao) {
        this.messageDao = messageDao;
    }

    @Override
    public boolean addMessage(AccountWallMessage message) {
        if (isNull(message.getImage()) && isNull(message.getText())) {
            return false;
        }
        message.setPosted(Timestamp.valueOf(LocalDateTime.now().withNano(0)));
        return messageDao.create(message);
    }

    @Override
    public boolean deleteMessage(AccountWallMessage message) {
        return messageDao.delete(message);
    }

    @Override
    public Collection<AccountWallMessage> selectMessages(long accountId) {
        return messageDao.getMessages(accountId);
    }
}
