package com.getjavajob.training.yarginy.socialnetwork.dao.facades;

import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.phone.Phone;
import com.getjavajob.training.yarginy.socialnetwork.dao.batchmodeldao.BatchDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.Dao;
import com.getjavajob.training.yarginy.socialnetwork.dao.relationsdao.onetomany.OneToManyDao;

import java.util.Collection;

import static com.getjavajob.training.yarginy.socialnetwork.dao.factories.AbstractDbFactory.getDbFactory;

public class PhoneDaoImpl implements PhoneDao {
    private final BatchDao<Phone> phoneDao = getDbFactory().getBatchPhoneDao();
    private final Dao<Account> accountDao = getDbFactory().getAccountDao();
    private final OneToManyDao<Account, Phone> accountsPhonesDao = getDbFactory().getAccountsPhones(accountDao,
            phoneDao);

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
    public boolean update(Phone phone) {
        return phoneDao.update(phone);
    }

    @Override
    public boolean delete(Phone phone) {
        return phoneDao.delete(phone);
    }

    @Override
    public Collection<Phone> selectAll() {
        return phoneDao.selectAll();
    }

    @Override
    public Collection<Phone> selectPhonesByOwner(Account account) {
        return accountsPhonesDao.selectMany(account);
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
