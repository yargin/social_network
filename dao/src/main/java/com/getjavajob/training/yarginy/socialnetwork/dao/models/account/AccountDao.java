package com.getjavajob.training.yarginy.socialnetwork.dao.models.account;

import com.getjavajob.training.yarginy.socialnetwork.dao.models.EntityDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.models.group.Group;

import java.util.Collection;

/**
 * provides operations with {@link Account}
 */
public interface AccountDao extends EntityDao<Account> {
    Collection<Group> selectOwnedGroups(Account account);

    Collection<Group> selectJoinedGroups(Account account);

    // needed or not?? or in business
    @Deprecated
    boolean deleteOwnedGroup(Account account, Group group);

    boolean joinGroup(Account account, Group group);

    boolean leaveGroup(Account account, Group group);
}
