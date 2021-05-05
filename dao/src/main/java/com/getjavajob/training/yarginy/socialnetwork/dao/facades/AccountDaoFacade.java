package com.getjavajob.training.yarginy.socialnetwork.dao.facades;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.Group;
import com.getjavajob.training.yarginy.socialnetwork.common.models.Phone;

import java.io.Serializable;
import java.util.Collection;

public interface AccountDaoFacade extends Serializable {
    Account select(long id);

    Account selectFullInfo(long id);

    Account select(Account account);

    Account getNullModel();

    boolean create(Account account);

    boolean update(Account account);

    boolean delete(Account account);

    Collection<Account> selectAll();

    Collection<Group> getOwnedGroups(long accountId);

    Collection<Group> getGroupMembership(long accountId);

    Collection<Phone> getPhones(long accountId);

    Collection<Account> getFriends(long accountId);
}
