package com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.dmls.messages;

import com.getjavajob.training.yarginy.socialnetwork.dao.tables.messages.DialogsMessagesTable;
import com.getjavajob.training.yarginy.socialnetwork.dao.tables.messages.MessagesTable;

public class DialogMessageDml extends AbstractMessageDml {
    @Override
    protected MessagesTable getTable() {
        return new DialogsMessagesTable();
    }
}
