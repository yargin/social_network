package com.getjavajob.training.yarginy.socialnetwork.service.messages;

import com.getjavajob.training.yarginy.socialnetwork.dao.jdbctemplates.facades.messages.MessageDao;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("accountWallMessageService")
public class AccountWallMessageServiceImpl extends AbstractMessageService {
    public AccountWallMessageServiceImpl(@Qualifier("accountWallMessageDaoFacade") MessageDao messageDao) {
        super(messageDao);
    }
}
