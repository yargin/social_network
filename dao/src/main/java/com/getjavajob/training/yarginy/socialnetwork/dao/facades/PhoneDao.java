package com.getjavajob.training.yarginy.socialnetwork.dao.facades;

import com.getjavajob.training.yarginy.socialnetwork.common.models.phone.Phone;

import java.io.Serializable;
import java.util.Collection;

public interface PhoneDao extends Serializable {
    Phone select(long id);

    Phone select(Phone phone);

    boolean create(Phone phone);

    boolean update(Phone phone, Phone storedPhone);

    boolean delete(Phone phone);

    boolean update(Collection<Phone> newPhones, Collection<Phone> storedPhones);

    boolean delete(Collection<Phone> phones);

    Collection<Phone> selectAll();

    Collection<Phone> selectPhonesByOwner(long accountId);

    Phone getNullPhone();

    boolean create(Collection<Phone> phones);
}
