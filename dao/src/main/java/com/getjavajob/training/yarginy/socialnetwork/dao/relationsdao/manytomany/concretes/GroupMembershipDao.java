package com.getjavajob.training.yarginy.socialnetwork.dao.relationsdao.manytomany.concretes;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.Group;
import com.getjavajob.training.yarginy.socialnetwork.dao.relationsdao.manytomany.GroupMembershipDaoTransactional;
import com.getjavajob.training.yarginy.socialnetwork.dao.relationsdao.manytomany.ManyToManyDelegateDaoTx;
import org.springframework.stereotype.Repository;

@Repository("groupMembershipDao")
public class GroupMembershipDao extends ManyToManyDelegateDaoTx<Account, Group> {
    public GroupMembershipDao(GroupMembershipDaoTransactional groupMembershipDaoTransactional) {
        super(groupMembershipDaoTransactional);
    }
}
