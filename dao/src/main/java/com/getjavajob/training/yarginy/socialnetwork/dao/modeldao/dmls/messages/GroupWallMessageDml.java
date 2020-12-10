package com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.dmls.messages;

import com.getjavajob.training.yarginy.socialnetwork.dao.tables.messages.GroupWallMessagesTable;
import com.getjavajob.training.yarginy.socialnetwork.dao.tables.messages.MessagesTable;
import org.springframework.stereotype.Component;

@Component("groupWallMessageDml")
public class GroupWallMessageDml extends AbstractMessageDml {
    @Override
    protected MessagesTable getTable() {
        return new GroupWallMessagesTable();
    }
}
