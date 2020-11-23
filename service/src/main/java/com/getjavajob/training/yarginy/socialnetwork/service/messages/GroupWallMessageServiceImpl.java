package com.getjavajob.training.yarginy.socialnetwork.service.messages;

import com.getjavajob.training.yarginy.socialnetwork.dao.facades.messages.GroupWallMessageDaoImpl;

public class GroupWallMessageServiceImpl extends AbstractMessageService {
    public GroupWallMessageServiceImpl() {
        super(new GroupWallMessageDaoImpl());
    }
}
