package com.getjavajob.training.yarginy.socialnetwork.dao.group;

import com.getjavajob.training.yarginy.socialnetwork.dao.account.Account;

import java.util.List;

public interface GroupDao {
    Group selectGroup(int id);

    Group selectGroup(String name);

    boolean createGroup(Group group);

    boolean updateGroup(Group group);

    boolean deleteGroup(Group group);

    List<Account> selectMembers(Group group);
}
