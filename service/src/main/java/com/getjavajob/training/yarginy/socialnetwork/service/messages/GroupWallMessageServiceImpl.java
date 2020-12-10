package com.getjavajob.training.yarginy.socialnetwork.service.messages;

import com.getjavajob.training.yarginy.socialnetwork.dao.facades.messages.GroupWallMessageDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("groupWallMessageService")
public class GroupWallMessageServiceImpl extends AbstractMessageService {
    @Autowired
    public GroupWallMessageServiceImpl(GroupWallMessageDao groupWallMessageDao) {
        super(groupWallMessageDao);
    }
}
