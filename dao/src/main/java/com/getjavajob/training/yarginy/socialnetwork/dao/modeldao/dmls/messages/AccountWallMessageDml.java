package com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.dmls.messages;

import com.getjavajob.training.yarginy.socialnetwork.dao.tables.messages.AccountWallMessagesTable;
import com.getjavajob.training.yarginy.socialnetwork.dao.tables.messages.MessagesTable;
import org.springframework.stereotype.Component;

@Component("accountWallMessageDml")
public class AccountWallMessageDml extends AbstractMessageDml {
    @Override
    protected MessagesTable getTable() {
        return new AccountWallMessagesTable();
    }
}
