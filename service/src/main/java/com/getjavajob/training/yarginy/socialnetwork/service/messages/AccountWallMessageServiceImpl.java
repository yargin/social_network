package com.getjavajob.training.yarginy.socialnetwork.service.messages;

import com.getjavajob.training.yarginy.socialnetwork.dao.facades.messages.AccountWallMessageDaoImpl;
import org.springframework.stereotype.Service;

@Service
public class AccountWallMessageServiceImpl extends AbstractMessageService {
    public AccountWallMessageServiceImpl() {
        super(new AccountWallMessageDaoImpl());
    }
}
