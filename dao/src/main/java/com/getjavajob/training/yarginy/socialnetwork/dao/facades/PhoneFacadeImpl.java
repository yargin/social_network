package com.getjavajob.training.yarginy.socialnetwork.dao.facades;

import com.getjavajob.training.yarginy.socialnetwork.common.models.phone.Phone;
import com.getjavajob.training.yarginy.socialnetwork.dao.batchmodeldao.BatchDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.relationsdao.onetomany.OneToManyDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component("phoneDaoFacade")
public class PhoneFacadeImpl implements PhoneFacade {
    private BatchDao<Phone> phoneDao;
    private OneToManyDao<Phone> accountsPhonesDao;

    @Autowired
    public void setPhoneDao(BatchDao<Phone> phoneDao) {
        this.phoneDao = phoneDao;
    }

    @Autowired
    public void setAccountsPhonesDao(@Qualifier("accountPhonesDao") OneToManyDao<Phone> accountsPhonesDao) {
        this.accountsPhonesDao = accountsPhonesDao;
    }

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
