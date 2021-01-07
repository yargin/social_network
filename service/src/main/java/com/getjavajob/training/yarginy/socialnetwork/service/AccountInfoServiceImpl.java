package com.getjavajob.training.yarginy.socialnetwork.service;

import com.getjavajob.training.yarginy.socialnetwork.common.exceptions.IncorrectData;
import com.getjavajob.training.yarginy.socialnetwork.common.exceptions.IncorrectDataException;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.phone.Phone;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.AccountDaoFacade;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.PhoneDaoFacade;
import com.getjavajob.training.yarginy.socialnetwork.service.aaa.AccountInfoKeeper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class AccountInfoServiceImpl implements AccountInfoService {
    private final AccountDaoFacade accountDaoFacade;
    private final PhoneDaoFacade phoneDaoFacade;


    @Autowired
    public AccountInfoServiceImpl(AccountDaoFacade accountDaoFacade, PhoneDaoFacade phoneDaoFacade) {
        this.accountDaoFacade = accountDaoFacade;
        this.phoneDaoFacade = phoneDaoFacade;
    }

    @Override
    public AccountInfoKeeper select(Account account) {
        Account storedAccount = accountDaoFacade.select(account);
        Collection<Phone> phones = phoneDaoFacade.selectPhonesByOwner(storedAccount.getId());
        return new AccountInfoKeeper(storedAccount, phones);
    }

    @Override
    public AccountInfoKeeper select(long id) {
        Account storedAccount = accountDaoFacade.select(id);
        Collection<Phone> phones = phoneDaoFacade.selectPhonesByOwner(storedAccount.getId());
        return new AccountInfoKeeper(storedAccount, phones);
    }

    @Override
    public boolean update(AccountInfoKeeper accountInfo, AccountInfoKeeper storedAccountInfo) {
        Account account = accountInfo.getAccount();
        if (!accountDaoFacade.update(account, storedAccountInfo.getAccount())) {
            throw new IncorrectDataException(IncorrectData.EMAIL_DUPLICATE);
        }
        if (!phoneDaoFacade.update(accountInfo.getPhones(), storedAccountInfo.getPhones())) {
            throw new IncorrectDataException(IncorrectData.PHONE_DUPLICATE);
        }
        return true;
    }
}
