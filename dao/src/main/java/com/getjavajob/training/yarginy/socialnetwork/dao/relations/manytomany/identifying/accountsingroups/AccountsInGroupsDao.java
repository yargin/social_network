package com.getjavajob.training.yarginy.socialnetwork.dao.relations.manytomany.identifying.accountsingroups;

import com.getjavajob.training.yarginy.socialnetwork.dao.entities.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.dao.entities.group.Group;
import com.getjavajob.training.yarginy.socialnetwork.dao.relations.manytomany.identifying.ManyToManyDao;

import java.util.Collection;

public interface AccountsInGroupsDao extends ManyToManyDao<Account, Group> {
    @Override
    Collection<Account> selectBySecond(Group group);

    @Override
    Collection<Group> selectByFirst(Account account);

    @Override
    boolean create(Account account, Group group);

    @Override
    boolean delete(Account account, Group group);
}
