package com.getjavajob.training.yarginy.socialnetwork.dao.relationsdao.manytomany.concretes;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.Group;
import com.getjavajob.training.yarginy.socialnetwork.dao.relationsdao.manytomany.GroupModeratorsDaoTransactional;
import com.getjavajob.training.yarginy.socialnetwork.dao.relationsdao.manytomany.ManyToManyDelegateDaoTx;
import org.springframework.stereotype.Repository;

@Repository("groupModeratorsDao")
public class GroupModeratorsDao extends ManyToManyDelegateDaoTx<Account, Group> {
    public GroupModeratorsDao(GroupModeratorsDaoTransactional groupModeratorsDaoTransactional) {
        super(groupModeratorsDaoTransactional);
    }
}
