package com.getjavajob.training.yarginy.socialnetwork.dao.models.concretedaos;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Group;
import com.getjavajob.training.yarginy.socialnetwork.dao.models.AbstractTxDelegateDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.models.tx.GroupDaoTx;
import org.springframework.stereotype.Repository;

@Repository("groupDao")
public class GroupDao extends AbstractTxDelegateDao<Group> {
    public GroupDao(GroupDaoTx groupDaoTx) {
        super(groupDaoTx);
    }
}
