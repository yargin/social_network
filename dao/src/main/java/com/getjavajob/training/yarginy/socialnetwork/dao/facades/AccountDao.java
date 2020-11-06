package com.getjavajob.training.yarginy.socialnetwork.dao.facades;

import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.group.Group;
import com.getjavajob.training.yarginy.socialnetwork.common.models.phone.Phone;

import java.util.Collection;

public interface AccountDao {
    Account select(long id);

    Account select(Account account);

    boolean create(Account account);

    boolean update(Account account, Account storedAccount);

    boolean delete(Account account);

    Collection<Account> selectAll();

    Account getNullAccount();

    Collection<Group> getOwnedGroups(Account account);

    Collection<Group> getGroupMembership(long accountId);

    Collection<Phone> getPhones(Account account);

    Collection<Account> getFriends(long accountId);
}
