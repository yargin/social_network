package com.getjavajob.training.yarginy.socialnetwork.dao.models.group;

import com.getjavajob.training.yarginy.socialnetwork.dao.models.EntityDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.models.account.Account;

import java.util.Collection;

public interface GroupDao extends EntityDao<Group> {
    Collection<Account> selectMembers(Group group);
}
