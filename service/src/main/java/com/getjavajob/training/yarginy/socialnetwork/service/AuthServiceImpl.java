package com.getjavajob.training.yarginy.socialnetwork.service;

import com.getjavajob.training.yarginy.socialnetwork.common.exceptions.IncorrectData;
import com.getjavajob.training.yarginy.socialnetwork.common.exceptions.IncorrectDataException;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.AccountImpl;
import com.getjavajob.training.yarginy.socialnetwork.common.models.password.Password;
import com.getjavajob.training.yarginy.socialnetwork.common.models.password.PasswordImpl;
import com.getjavajob.training.yarginy.socialnetwork.common.models.phone.Phone;
import com.getjavajob.training.yarginy.socialnetwork.common.utils.DataHandleHelper;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.AccountDaoFacade;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.PasswordDaoFacade;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.PhoneDaoFacade;
import com.getjavajob.training.yarginy.socialnetwork.service.aaa.AccountInfoKeeper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Collection;

import static com.getjavajob.training.yarginy.socialnetwork.common.models.NullEntitiesFactory.getNullPassword;

@Service("authService")
public class AuthServiceImpl implements AuthService {
    private final AccountDaoFacade accountDaoFacade;
    private final PasswordDaoFacade passwordDaoFacade;
    private final PhoneDaoFacade phoneDaoFacade;
    private final DataHandleHelper dataHandleHelper;

    @Autowired
    public AuthServiceImpl(DataHandleHelper dataHandleHelper, AccountDaoFacade accountDaoFacade,
                           PasswordDaoFacade passwordDaoFacade, PhoneDaoFacade phoneDaoFacade) {
        this.dataHandleHelper = dataHandleHelper;
        this.accountDaoFacade = accountDaoFacade;
        this.passwordDaoFacade = passwordDaoFacade;
        this.phoneDaoFacade = phoneDaoFacade;
    }

    @Override
    public boolean register(AccountInfoKeeper accountInfoKeeper, Password password) {
        return register(accountInfoKeeper.getAccount(), accountInfoKeeper.getPhones(), password);
    }

    @Override
    public boolean register(Account account, Collection<Phone> phones, Password password) {
        account.setRegistrationDate(Date.valueOf(LocalDate.now()));
        if (!accountDaoFacade.create(account)) {
            throw new IncorrectDataException(IncorrectData.EMAIL_DUPLICATE);
        }
        Account savedAccount = accountDaoFacade.select(account);
        for (Phone phone : phones) {
            phone.setOwner(savedAccount);
        }
        if (!phoneDaoFacade.create(phones)) {
            throw new IncorrectDataException(IncorrectData.PHONE_DUPLICATE);
        }
        password.setPassword(dataHandleHelper.encrypt(password.getPassword()));
        if (!passwordDaoFacade.create(password)) {
            throw new IllegalStateException();
        }
        return true;
    }

    @Override
    public Account login(String email, String password) {
        Password passwordObject = new PasswordImpl();
        Account account = new AccountImpl();
        account.setEmail(email);
        passwordObject.setAccount(account);
        passwordObject.setPassword(password);
        passwordObject = passwordDaoFacade.select(passwordObject);
        if (passwordObject.equals(getNullPassword())) {
            throw new IncorrectDataException(IncorrectData.WRONG_EMAIL);
        }
        if (!passwordObject.getPassword().equals(dataHandleHelper.encrypt(password))) {
            throw new IncorrectDataException(IncorrectData.WRONG_PASSWORD);
        }
        return accountDaoFacade.select(account);
    }
}
