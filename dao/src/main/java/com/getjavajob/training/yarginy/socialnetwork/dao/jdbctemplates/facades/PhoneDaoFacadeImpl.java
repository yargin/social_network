package com.getjavajob.training.yarginy.socialnetwork.dao.jdbctemplates.facades;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Phone;
import com.getjavajob.training.yarginy.socialnetwork.dao.jdbctemplates.modeldao.batch.BatchDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.jdbctemplates.relationsdao.onetomany.OneToManyDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.jpa.JpaBatchDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.jpa.JpaDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.jpa.relationsdao.onetomany.JpaOneToManyDao;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component("phoneDaoFacade")
public class PhoneDaoFacadeImpl implements PhoneDaoFacade {
    private final JpaBatchDao<Phone> phoneDao;
    private final JpaOneToManyDao<Phone> accountsPhonesDao;

    public PhoneDaoFacadeImpl(@Qualifier("jpaPhoneDao") JpaBatchDao<Phone> phoneDao, JpaOneToManyDao<Phone> accountsPhonesDao) {
        this.phoneDao = phoneDao;
        this.accountsPhonesDao = accountsPhonesDao;
    }

    @Override
    public Phone select(long phoneId) {
        return phoneDao.select(phoneId);
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
        return phoneDao.update(phone);
//        return phoneDao.update(phone, storedPhone);
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
        return phoneDao.getNullModel();
    }

    @Override
    public boolean create(Collection<Phone> phones) {
        return phoneDao.create(phones);
    }
}
