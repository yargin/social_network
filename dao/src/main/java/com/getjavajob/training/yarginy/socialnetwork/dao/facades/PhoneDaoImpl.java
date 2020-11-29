package com.getjavajob.training.yarginy.socialnetwork.dao.facades;

import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.phone.Phone;
import com.getjavajob.training.yarginy.socialnetwork.dao.batchmodeldao.BatchDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.Dao;
import com.getjavajob.training.yarginy.socialnetwork.dao.relationsdao.onetomany.OneToManyDao;

import java.util.Collection;

import static com.getjavajob.training.yarginy.socialnetwork.dao.factories.AbstractDbFactory.getDbFactory;

public class PhoneDaoImpl implements PhoneDao {
    private final BatchDao<Phone> phoneDao = getDbFactory().getPhoneDao();
    private final Dao<Account> accountDao = getDbFactory().getAccountDao();
    private final OneToManyDao<Phone> accountsPhonesDao = getDbFactory().getAccountsPhones(accountDao);

    @Override
    public Phone select(long id) {
        return phoneDao.select(id);
    }

    @Override
    public Phone select(Phone phone) {
        return phoneDao.select(phone);
    }

    @Override
    public boolean create(Phone phone) {
        return phoneDao.create(phone);
    }

    @Override
    public boolean update(Phone phone, Phone storedPhone) {
        return phoneDao.update(phone, storedPhone);
    }

    @Override
    public boolean update(Collection<Phone> storedPhones, Collection<Phone> newPhones) {
        return phoneDao.update(storedPhones, newPhones);
    }

    @Override
    public boolean delete(Phone phone) {
        return phoneDao.delete(phone);
    }

    @Override
    public boolean delete(Collection<Phone> phones) {
        return phoneDao.delete(phones);
    }

    @Override
    public Collection<Phone> selectAll() {
        return phoneDao.selectAll();
    }

    @Override
    public Collection<Phone> selectPhonesByOwner(long accountId) {
        return accountsPhonesDao.selectMany(accountId);
    }

    @Override
    public Phone getNullPhone() {
        return phoneDao.getNullEntity();
    }

    @Override
    public boolean create(Collection<Phone> phones) {
        return phoneDao.create(phones);
    }
}
