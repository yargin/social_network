package com.getjavajob.training.yarginy.socialnetwork.dao.models.concretedaos;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Group;
import com.getjavajob.training.yarginy.socialnetwork.dao.models.DelegateDaoTx;
import com.getjavajob.training.yarginy.socialnetwork.dao.models.transactionals.GroupDaoTransactional;
import org.springframework.stereotype.Repository;

@Repository("groupDao")
public class GroupDao extends DelegateDaoTx<Group> {
    public GroupDao(GroupDaoTransactional groupDaoTransactional) {
        super(groupDaoTransactional);
    }
}
