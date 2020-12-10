package com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.dmls.messages;

import com.getjavajob.training.yarginy.socialnetwork.dao.tables.messages.DialogsMessagesTable;
import com.getjavajob.training.yarginy.socialnetwork.dao.tables.messages.MessagesTable;
import org.springframework.stereotype.Component;

@Component("dialogMessageDml")
public class DialogMessageDml extends AbstractMessageDml {
    @Override
    protected MessagesTable getTable() {
        return new DialogsMessagesTable();
    }
}
