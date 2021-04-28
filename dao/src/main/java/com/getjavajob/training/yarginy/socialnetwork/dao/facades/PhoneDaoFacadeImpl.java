package com.getjavajob.training.yarginy.socialnetwork.dao.facades;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Phone;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.utils.TransactionPerformer;
import com.getjavajob.training.yarginy.socialnetwork.dao.newdaos.modeldaos.implementations.NewPhoneDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.newdaos.relationdaos.onetomany.implementations.NewAccountPhonesDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component("phoneDaoFacade")
public class PhoneDaoFacadeImpl implements PhoneDaoFacade {
    private NewPhoneDao phoneDao;
    private NewAccountPhonesDao accountsPhonesDao;
    private TransactionPerformer transactionPerformer;

    @Autowired
    public void setPhoneDao(NewPhoneDao phoneDao) {
        this.phoneDao = phoneDao;
    }

    @Autowired
    public void setAccountsPhonesDao(NewAccountPhonesDao accountsPhonesDao) {
        this.accountsPhonesDao = accountsPhonesDao;
    }

    @Autowired
    public void setTransactionPerformer(TransactionPerformer transactionPerformer) {
        this.transactionPerformer = transactionPerformer;
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
        return transactionPerformer.transactionPerformed(phoneDao::create, phone);
    }

    @Override
    public boolean update(Phone phone) {
        return transactionPerformer.transactionPerformed(phoneDao::update, phone);
    }

    @Override
    public boolean update(Collection<Phone> storedPhones, Collection<Phone> newPhones) {
        return transactionPerformer.transactionPerformed(phoneDao::update, storedPhones, newPhones);
    }

    @Override
    public boolean delete(Phone phone) {
        return transactionPerformer.transactionPerformed(phoneDao::delete, phone);
    }

    @Override
    public boolean delete(Collection<Phone> phones) {
        return transactionPerformer.transactionPerformed(phoneDao::delete, phones);
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
        return transactionPerformer.transactionPerformed(phoneDao::create, phones);
    }
}
