package com.getjavajob.training.yarginy.socialnetwork.dao.facades;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.Group;

import java.io.Serializable;
import java.util.Collection;

public interface GroupDaoFacade extends Serializable {
    Group select(long id);

    Group select(Group group);

    Group getNullModel();

    boolean create(Group group);

    boolean update(Group group, Group storedGroup);

    boolean delete(Group group);

    Collection<Group> selectAll();

    Collection<Group> selectGroupsByOwner(long accountId);

    boolean isOwner(long accountId, long groupId);

    Group getNullGroup();

    Collection<Account> selectMembers(long groupId);

    boolean addMember(long accountId, long groupId);

    boolean removeMember(long accountId, long groupId);
}
