package com.getjavajob.training.yarginy.socialnetwork.service.messages;

import com.getjavajob.training.yarginy.socialnetwork.dao.facades.messages.MessageDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("accountWallMessageService")
public class AccountWallMessageServiceImpl extends AbstractMessageService {
    @Autowired
    public AccountWallMessageServiceImpl(@Qualifier("accountWallMessageFacade") MessageDao messageDao) {
        super(messageDao);
    }
}
