package com.getjavajob.training.yarginy.socialnetwork.dao.accountsingroups;

import com.getjavajob.training.yarginy.socialnetwork.dao.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.dao.group.Group;

import java.util.List;

public interface AccountsInGroupsDao {
    List<Account> selectAccounts(int groupId);

    //make static
//  List<Account> selectAccounts(Group group, Connection connection);

    List<Group> selectGroups(int accountId);

    //make static
//  List<Group> selectGroups(Account account, Connection connection);

    List<Account> updateAccounts(int groupId);

    //make static
//  List<Account> updateAccounts(Group group, Connection connection);

    List<Group> updateGroups(int accountId);

    //make static
//    List<Group> updateGroups(Account account, Connection connection);
}
