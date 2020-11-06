package com.getjavajob.training.yarginy.socialnetwork.dao.facades;

import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.group.Group;

import java.util.Collection;

public interface GroupDao {
    Group select(long id);

    Group select(Group group);

    boolean create(Group group);

    boolean update(Group group, Group storedGroup);

    boolean delete(Group group);

    Collection<Group> selectAll();

    Collection<Group> selectGroupsByOwner(Account account);

    Group getNullGroup();

    Collection<Account> selectMembers(long groupId);

    boolean addMember(long groupId, long accountId);

    boolean removeMember(long groupId, long accountId);
}
