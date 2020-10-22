package com.getjavajob.training.yarginy.socialnetwork.dao.facades;

import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.phone.Phone;

import java.util.Collection;

public interface PhoneDao {
    Phone select(long id);

    Phone select(Phone phone);

    boolean create(Phone phone);

    boolean update(Phone phone);

    boolean delete(Phone phone);

    boolean delete(Collection<Phone> phones);

    Collection<Phone> selectAll();

    Collection<Phone> selectPhonesByOwner(Account account);

    Phone getNullPhone();

    boolean create(Collection<Phone> phones);
}
