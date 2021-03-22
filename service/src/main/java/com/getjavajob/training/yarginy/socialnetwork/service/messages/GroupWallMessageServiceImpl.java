package com.getjavajob.training.yarginy.socialnetwork.service.messages;

import com.getjavajob.training.yarginy.socialnetwork.dao.facades.messages.GroupWallMessageDaoFacade;
import org.springframework.stereotype.Service;

@Service("groupWallMessageService")
public class GroupWallMessageServiceImpl extends AbstractMessageService {
    public GroupWallMessageServiceImpl(GroupWallMessageDaoFacade groupWallMessageDao) {
        super(groupWallMessageDao);
    }
}
