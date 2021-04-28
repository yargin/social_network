package com.getjavajob.training.yarginy.socialnetwork.dao.facades;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Phone;
import com.getjavajob.training.yarginy.socialnetwork.common.utils.TransactionPerformer;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldaos.implementations.PhoneDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.relationdaos.onetomany.implementations.AccountPhonesDao;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component("phoneDaoFacade")
public class PhoneDaoFacadeImpl implements PhoneDaoFacade {
    private final PhoneDao phoneDao;
    private final AccountPhonesDao accountsPhonesDao;
    private final TransactionPerformer transactionPerformer;

    public PhoneDaoFacadeImpl(PhoneDao phoneDao, AccountPhonesDao accountsPhonesDao,
                              TransactionPerformer transactionPerformer) {
        this.phoneDao = phoneDao;
        this.accountsPhonesDao = accountsPhonesDao;
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
