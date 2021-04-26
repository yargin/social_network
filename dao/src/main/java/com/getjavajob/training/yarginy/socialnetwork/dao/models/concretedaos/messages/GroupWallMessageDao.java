package com.getjavajob.training.yarginy.socialnetwork.dao.models.concretedaos.messages;

import com.getjavajob.training.yarginy.socialnetwork.common.models.messages.GroupWallMessage;
import com.getjavajob.training.yarginy.socialnetwork.dao.models.DelegateDaoTx;
import com.getjavajob.training.yarginy.socialnetwork.dao.models.transactionals.messages.GroupWallMessageDaoTransactional;
import org.springframework.stereotype.Repository;

@Repository("groupWallMessageDao")
public class GroupWallMessageDao extends DelegateDaoTx<GroupWallMessage> {
    public GroupWallMessageDao(GroupWallMessageDaoTransactional groupWallMessageDaoTransactional) {
        super(groupWallMessageDaoTransactional);
    }
}
