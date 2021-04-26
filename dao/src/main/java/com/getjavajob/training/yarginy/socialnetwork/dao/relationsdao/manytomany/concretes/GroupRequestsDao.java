package com.getjavajob.training.yarginy.socialnetwork.dao.relationsdao.manytomany.concretes;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.Group;
import com.getjavajob.training.yarginy.socialnetwork.dao.relationsdao.manytomany.GroupRequestsDaoTransactional;
import com.getjavajob.training.yarginy.socialnetwork.dao.relationsdao.manytomany.ManyToManyDelegateDaoTx;
import org.springframework.stereotype.Repository;

@Repository("groupRequestsDao")
public class GroupRequestsDao extends ManyToManyDelegateDaoTx<Account, Group> {
    public GroupRequestsDao(GroupRequestsDaoTransactional groupRequestsDaoTransactional) {
        super(groupRequestsDaoTransactional);
    }
}
