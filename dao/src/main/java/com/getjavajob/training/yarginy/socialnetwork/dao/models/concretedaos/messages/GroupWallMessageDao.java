package com.getjavajob.training.yarginy.socialnetwork.dao.models.concretedaos.messages;

import com.getjavajob.training.yarginy.socialnetwork.common.models.messages.GroupWallMessage;
import com.getjavajob.training.yarginy.socialnetwork.dao.models.AbstractTxDelegateDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.models.tx.messages.GroupWallMessageDaoTx;
import org.springframework.stereotype.Repository;

@Repository("groupWallMessageDao")
public class GroupWallMessageDao extends AbstractTxDelegateDao<GroupWallMessage> {
    public GroupWallMessageDao(GroupWallMessageDaoTx groupWallMessageDaoTx) {
        super(groupWallMessageDaoTx);
    }
}
