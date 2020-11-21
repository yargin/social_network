package com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.dmls.messages;

import com.getjavajob.training.yarginy.socialnetwork.dao.tables.messages.AccountPrivateMessagesTable;
import com.getjavajob.training.yarginy.socialnetwork.dao.tables.messages.MessagesTable;

public class PrivateMessageDml extends AbstractMessageDml {
    @Override
    protected MessagesTable getTable() {
        return new AccountPrivateMessagesTable();
    }
}
